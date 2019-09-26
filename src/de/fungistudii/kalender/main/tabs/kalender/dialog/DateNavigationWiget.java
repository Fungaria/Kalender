package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateNavigationWiget extends Table {

    private DatePicker navigation;
    private final Vector2 screenPosition = new Vector2();
    private DateButton parent;

    private boolean open;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");

    public DateNavigationWiget(DateButton parent) {
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/dropdown_list", 10));
        navigation = new DatePicker((date) -> {
            hide();
            parent.calendar.setTime(date);
            parent.setChecked(false);
            parent.getLabel().setText(dateFormat.format(parent.calendar.getTime()));
        });
        super.add(navigation).grow().height(600).pad(20);
        this.parent = parent;
    }

    public void setDate(Date date) {
        navigation.setDate(date);
        parent.calendar.setTime(date);
        parent.getLabel().setText(dateFormat.format(parent.calendar.getTime()));
    }

    public void hide() {
        this.open = false;
        super.remove();
    }

    public void show() {
        this.open = true;
        ERE.mainScreen.stage.addActor(this);
        parent.localToStageCoordinates(screenPosition.set(0, 0));
        setWidth(parent.getWidth());
        setHeight(parent.getWidth() * 1f);
        setX(screenPosition.x);
        setY(screenPosition.y - getHeight());
    }

    public boolean isOpen() {
        return open;
    }
}
