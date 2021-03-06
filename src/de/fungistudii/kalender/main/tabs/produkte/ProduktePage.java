package de.fungistudii.kalender.main.tabs.produkte;

import de.fungistudii.kalender.main.tabs.mitarbeiter.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddAppointmentDialog;

/**
 *
 * @author sreis
 */
public class ProduktePage extends TabPage{
    public ProduktePage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/red", 5));
        add(im).grow();
    }

    AddAppointmentDialog dialog = new AddAppointmentDialog();
    
    @Override
    public void show() {
        dialog.show(getStage());
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
