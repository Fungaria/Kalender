package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddTerminDialog;

/**
 *
 * @author sreis
 */
public class MitarbeiterPage extends Table{
    public MitarbeiterPage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/green", 5));
        add(im).grow();
    }
    
    private AddTerminDialog dialog = new AddTerminDialog();
    
    public void show(){
        dialog.show(ERE.mainScreen.stage);
        dialog.invalidateHierarchy();
        dialog.validate();
    }
}
