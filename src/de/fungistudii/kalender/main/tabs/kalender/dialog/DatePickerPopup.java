package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.DatePicker;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DatePickerPopup extends Table {

    public DatePicker navigation;

    private boolean open;

    private DatePicker.DateSelectCallback callback;
    
    public DatePickerPopup(DatePicker.DateSelectCallback callback) {
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/square", 7, ERE.assets.grey1));
        
        navigation = new DatePicker((date, dir) -> {
            hide();
            callback.dateSelected(date, dir);
        });
        super.add(navigation).grow().pad(20).height(Value.percentWidth(1, this));
        
        ERE.mainScreen.stage.addCaptureListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();
                if (isAscendantOf(target)) {
                    return false;
                }
                if(open){
                    hide();
                    event.cancel();
                }
                return true;
            }

            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        hide();
                        event.stop();
                        return true;
                }
                return false;
            }
        });
    }

    public void setDate(Date date) {
        navigation.setDate(date);
    }

    public void hide() {
        this.open = false;
        super.remove();
    }

    public void show(float x, float y, float width) {
        this.open = true;
        ERE.mainScreen.stage.addActor(this);
        setWidth(width);
        setHeight(width);
        setX(x);
        setY(y - getHeight());
    }

    public boolean isOpen() {
        return open;
    }
}
