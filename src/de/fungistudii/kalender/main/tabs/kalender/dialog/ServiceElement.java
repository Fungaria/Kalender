package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.util.Durations;
import de.fungistudii.kalender.util.NinePatchOffsetDrawable;
import de.fungistudii.kalender.util.value.ValueUtil;

/**
 *
 * @author sreis
 */
public class ServiceElement extends Table{

    public final GenericDropDown<Service> leistung;
    public final GenericDropDown<Durations> duration;
    public final ImageButton delete;

    public ServiceElement(Value width, Value height) {
        NinePatchOffsetDrawable np = ERE.assets.createRounded("outline_left");
        np.setAdjustSize(true);
        leistung = new GenericDropDown<>(np, ERE.data.root.services.values().stream().toArray(Service[]::new));
        duration = new GenericDropDown<>(ERE.assets.createNinePatchDrawable("rounded/outline_middle", 15), Durations.all());

        NinePatchDrawable thrashBg = ERE.assets.createNinePatchDrawable("rounded/outline_right", 15);
        SpriteDrawable thrashIcon = ERE.assets.createDrawable("icons/thrash");

        delete = new ImageButton(thrashIcon);
        delete.getStyle().up = thrashBg;
        delete.getImageCell().right();
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        separator.setTopHeight(5);
                
        Image separator1 = new Image(separator);
        Image separator2 = new Image(separator);
        
        defaults().height(Cons.defaultRowHeight).minHeight(0);
        
        add(leistung).grow().maxWidth(-1);
//        add(separator1).width(1).prefHeight(ValueUtil.percentValue(0.6f, height)).padLeft(-1).padRight(-1);
        add(duration).grow();
//        add(separator2).width(1).prefHeight(ValueUtil.percentValue(0.6f, height)).padLeft(-1).padRight(-1);
        add(delete).width(height).right();
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
    
    public Service createService(){
        return ERE.data.root.services.get(leistung.getSelectedIndex());
    }
}
