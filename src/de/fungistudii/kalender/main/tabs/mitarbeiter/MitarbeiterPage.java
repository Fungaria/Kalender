package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddTerminDialog;

/**
 *
 * @author sreis
 */
public class MitarbeiterPage extends TabPage{
    public MitarbeiterPage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/green", 5));
        add(im).grow();
    }
    
    AddTerminDialog dialog = new AddTerminDialog();
    
    public void show(){
        dialog.show(ERE.mainScreen.stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
