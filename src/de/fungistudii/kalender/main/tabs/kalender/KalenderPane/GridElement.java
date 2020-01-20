package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Disposable;
import static de.fungistudii.kalender.Main.ERE;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public abstract class GridElement extends Table implements Disposable {

    boolean focused = false;
    
    private final ClickListener listener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            boolean ascendant = isAscendantOf(event.getTarget());
            if (!focused && ascendant) {
                setFocused(true);
                focused = true;
            } else if(focused && !ascendant){
                setFocused(false);
                focused = false;
            }
        }
    };
    
    protected Image handle;

    public void addHandleListener(MitarbeiterColumn column){
        handle.addListener(new DragListener() {

            private int startY;
            private int originalSpan;
            private final Vector2 tmpVec = new Vector2();

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                originalSpan = getSpan();
                startY = column.table.getRow(handle.localToActorCoordinates(column.table, tmpVec.set(0, 0)).y);
            }

            
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                y = handle.localToActorCoordinates(column.table, tmpVec.set(0, y)).y;
                setSpan(originalSpan + column.table.getRow(y) - startY+1);
                if(getParent() instanceof Layout)
                    ((Layout)getParent()).invalidate();
            }
            
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                ERE.client.sendTCP(serialize());
            }
        });
    }

    public GridElement() {
        super();
        setTouchable(Touchable.enabled);
        ERE.mainScreen.stage.addListener(listener);
    }

    public boolean isOver() {
        return false;
    }
    public boolean isFocused() {
        return focused;
    }
    @Override
    public void dispose() {
        remove();
        ERE.mainScreen.stage.removeListener(listener);
    }

    public abstract LocalDateTime getStart();

    public abstract int getFriseur();

    public abstract int getSpan();

    public abstract void setFriseur(int friseur);

    public abstract void setStart(LocalDateTime start);

    public abstract void setSpan(int span);

    public abstract GridElement copy();

    public abstract void fadeOut();

    public abstract Object serialize();

    public abstract void setFocused(boolean foc);
}
