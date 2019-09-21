package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.side.Navigation;

/**
 *
 * @author sreis
 */
public class DateNavigationWiget extends Table{

    private Navigation navigation;
    private final Vector2 screenPosition = new Vector2();
    private DateButton parent;
    
    private boolean open;

    public DateNavigationWiget(DateButton parent) {
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/dropdown_list", 10));
        navigation = new Navigation((date) -> {
            hide();
            parent.calendar.setTime(date);
            parent.setChecked(false);
            parent.getLabel().setText(date.toString());
        });
        super.add(navigation).grow().height(600).pad(20);
        this.parent = parent;
    }
    
    public void hide(){
        this.open = false;
        super.remove();
    }
    
    public void show(){
        this.open = true;
        ERE.mainScreen.stage.addActor(this);
        parent.localToStageCoordinates(screenPosition.set(0, 0));
        setWidth(parent.getWidth());
        setHeight(parent.getWidth()*1f);
        setX(screenPosition.x);
        setY(screenPosition.y-getHeight());
    }
    
    public boolean isOpen() {
        return open;
    }
}
