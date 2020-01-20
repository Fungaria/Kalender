package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.datepicker.DatePicker;
import de.fungistudii.kalender.util.Popup;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class DatePickerPopup extends Popup {

    public DatePicker navigation;

    private boolean open;

    public Runnable onHide = () -> {
    };

    public Actor openButton;

    ClickListener hideListener = new ClickListener() {
        public boolean keyDown(InputEvent event, int keycode) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    hide();
                    event.stop();
                    return true;
            }
            return false;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if(!isAscendantOf(event.getTarget())){
                hide();
                return true;
            }
            return false;
        }
    };

    public DatePickerPopup(DatePicker.DateSelectCallback callback) {
        setBackground(ERE.assets.createNinePatchDrawable("generic/square", 7, ERE.assets.grey1));
        fadeBackground(false);
        
        navigation = new DatePicker((date, dir) -> {
            hide();
            callback.dateSelected(date, dir);
        });

        add(navigation).grow().pad(20);
    }

    public void setDate(LocalDate date) {
        navigation.setDate(date);
    }

    @Override
    public void hide() {
        super.hide();
        this.open = false;
        onHide.run();
        getStage().removeListener(hideListener);
    }

    public void show(float x, float y, float width, Stage stage) {
        this.open = true;
        prefWidth(width);
        prefHeight(width * 1.1f);
        super.show(stage);
        super.setX(x);
        super.setY(y - (width * 1.1f));
        super.setCentered(false);
        invalidate();
        stage.addListener(hideListener);
    }

    public boolean isOpen() {
        return open;
    }
}
