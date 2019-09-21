package de.fungistudii.kalender.main.tabs.produkte;

import de.fungistudii.kalender.main.tabs.mitarbeiter.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class ProduktePage extends Table{
    public ProduktePage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/red", 5));
        add(im).grow();
    }
}
