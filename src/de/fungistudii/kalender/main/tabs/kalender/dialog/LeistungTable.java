package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.main.generic.GenericDropDown;

/**
 *
 * @author sreis
 */
public class LeistungTable extends Container{

    public final GenericDropDown<Service> leistung;
    public final GenericDropDown<String> duration;
    public final ImageButton delete;

    public LeistungTable() {
        Table table = new Table();
        
        leistung = new GenericDropDown<>("kalender/dialog/leistung", "kalender/dialog/leistung_open", ERE.data.root.services.stream().toArray(Service[]::new));
        duration = new GenericDropDown<>("kalender/dialog/duration", "kalender/dialog/duration_open", new String[]{"1:00", "2:00", "3:00", "4:00"});

        NinePatchDrawable thrashBg = ERE.assets.createNinePatchDrawable("kalender/dialog/thrash_bg", 10);
        SpriteDrawable thrashIcon = ERE.assets.createDrawable("generic/thrash");

        delete = new ImageButton(new ImageButton.ImageButtonStyle(thrashBg, thrashBg, thrashBg, thrashIcon, thrashIcon, thrashIcon));

        table.add(leistung).growX().minHeight(0);
        table.add(duration).minHeight(0).fill().width(Value.percentWidth(0.2f, this));
        table.add(delete).fill().minHeight(0).width(Value.percentWidth(0.1f, this));
        
        super.setActor(table);
    }
}
