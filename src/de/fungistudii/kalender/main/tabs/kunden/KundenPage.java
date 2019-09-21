package de.fungistudii.kalender.main.tabs.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class KundenPage extends Table{
    public KundenPage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/blue", 5));
        add(im).grow();
    }
}
