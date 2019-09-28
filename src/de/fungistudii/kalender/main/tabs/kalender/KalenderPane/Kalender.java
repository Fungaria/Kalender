package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.ContextMenu;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sreis
 */
public class Kalender extends Table {

    private final int NUM_ROWS = 16;
    public final int startTime = 8;

    private final SpriteDrawable timeFiller = ERE.assets.createDrawable("kalender/grid/time");

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;
    
    private Innerlander old;
    private Innerlander nu;
    private ScrollPane pane;
    
    private TestContainer container;
    
    public Kalender(Calendar calendar) {
        old = new Innerlander(calendar.getTime());
        nu = new Innerlander(calendar.getTime());
        container = new TestContainer(old);
        container.add(nu);
        pane = new ScrollPane(container);
        pane.setOverscroll(false, true);
        
        VerticalScrollPane p2 = new VerticalScrollPane(new TimeColumn(Align.left));
        p2.setScrollingDisabled(true, false);
        p2.setParent(pane);
        
        VerticalScrollPane p1 = new VerticalScrollPane(new TimeColumn(Align.right));
        p1.setScrollingDisabled(true, false);
        p1.setParent(pane);
        
        NamesHeader namesHeader = new NamesHeader(ERE.data.root.friseure.stream().map((s)->s.name).toArray(String[]::new), old);
        VerticalScrollPane n = new VerticalScrollPane(namesHeader);
        n.setScrollingDisabled(false, true);
        n.setParent(pane);
        
        super.add();
        super.add(n).height(40);
        super.add();
        super.row();
        super.add(p1);
        super.add(pane).grow();
        super.add(p2);

        p2.setZIndex(1000);
        p1.setZIndex(1000);
        
        HashMap<String, Runnable> actions = new HashMap<>();
        actions.put("Termin erstellen", () -> {
            ERE.mainScreen.kalender.panel.openDialog();
        });
        actions.put("Blockierung hinzufügen", () -> {});
        backgroundContext = new ContextMenu(this, actions);
        backgroundContext.setShowOnRightClick(false);

        super.addListener(new ClickListener(Input.Buttons.RIGHT){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof BackgroundElement){
                    backgroundContext.show(x, y);
                }
            }
        });
    }

    @Override
    public void layout() {
        super.layout();
        if(pane.getWidth()<1000){
            pane.setScrollingDisabled(false, false);
        }else{
            pane.setScrollingDisabled(true, false);
        }
    }
    
    public void updateCurrentTable(){
        old.reloadTable();
    }
    
    public void switchDate(Date date, int direction){
        container.removeActor(nu);
        nu = new Innerlander(date);
        container.add(nu);
        nu.setVisible(false);
        
        if(direction == 0)
            return;
        
        nu.clearActions();
        old.clearActions();
      
        Gdx.app.postRunnable(() -> {
            nu.setVisible(true);
            nu.setPosition(direction*old.getWidth(), 0);
                old.addAction(Actions.sequence(Actions.moveBy(-direction*old.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2), Actions.hide(), Actions.run(() -> {
                    Innerlander g = old;
                    old = nu;
                    nu = g;
                })));
            old.setPosition(0, 0);
            nu.addAction(Actions.moveBy(-direction*nu.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2));
        });
    }

    public Button getSelectedElement() {
        return old.getSelectedElement();
    }
    
    private class TimeColumn extends Table {
        private final TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 14));
        public TimeColumn(int align) {
            dateStyle.fontColor = ERE.assets.grey4;
            for (int i = 0; i < NUM_ROWS; i++) {
                TextButton element = new TextButton(startTime + i + "", dateStyle);
                element.getLabel().setAlignment((align | Align.top) & ~Align.bottom);
                element.getLabelCell().pad(6);
                super.add(element).minSize(0).growX().minWidth(Value.percentWidth(0.03f, Kalender.this));
                super.row();
            }
        }
    }
}
