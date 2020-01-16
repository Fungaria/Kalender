package de.fungistudii.kalender.main.tabs.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.TabPage;

/**
 *
 * @author sreis
 */
public class KundenPage extends TabPage{
    public KundenPage() {
        Image im = new Image(ERE.assets.createNinePatchDrawable("tabs/blue", 5));
        add(im).grow();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
