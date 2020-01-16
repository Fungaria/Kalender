package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.util.Durations;
import de.fungistudii.kalender.util.value.ValueUtil;

/**
 *
 * @author sreis
 */
public class LeistungTable extends Table{

    public final GenericDropDown<Service> leistung;
    public final GenericDropDown<Durations> duration;
    public final ImageButton delete;

    public LeistungTable(Value width, Value height) {
        leistung = new GenericDropDown<>(ERE.assets.createNinePatchDrawable("generic/rounded_left", 10), ERE.data.root.services.values().stream().toArray(Service[]::new));
        duration = new GenericDropDown<>(ERE.assets.createNinePatchDrawable("generic/middle", 10), Durations.all());

        NinePatchDrawable thrashBg = ERE.assets.createNinePatchDrawable("generic/rounded_right", 10);
        SpriteDrawable thrashIcon = ERE.assets.createDrawable("generic/thrash");

        delete = new ImageButton(thrashIcon);
        delete.getStyle().up = thrashBg;
        delete.getImageCell().pad(5).right();
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        Image separator1 = new Image(separator);
        Image separator2 = new Image(separator);
        
        add(leistung).grow().minHeight(0).prefWidth(ValueUtil.percentValue(0.5f, width)).prefHeight(height);
        add(separator1).width(1).prefHeight(ValueUtil.percentValue(0.8f, height)).padLeft(-1).padRight(-1);
        add(duration).minHeight(0).grow().prefWidth(ValueUtil.percentValue(0.3f, width)).prefHeight(height);
        add(separator2).width(1).prefHeight(ValueUtil.percentValue(0.8f, height)).padLeft(-1).padRight(-1);
        add(delete).grow().minHeight(0).prefSize(height).right();
        separator1.setZIndex(10);
        separator2.setZIndex(10);
        
        duration.setSelectedIndex(leistung.getSelected().duration/15);
        
        leistung.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                duration.setSelectedIndex(leistung.getSelected().duration/15);
            }
        });
    }
}
