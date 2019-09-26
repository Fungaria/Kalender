package de.fungistudii.kalender.main.tabpane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import de.fungistudii.kalender.Main;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class TabButton extends TextButton{
    public TabButton(String text) {
        super(text, new TabButtonStyle());
    }
    
    private static class TabButtonStyle extends TextButtonStyle{
        public TabButtonStyle() {
            super.down = ERE.assets.createNinePatchDrawable("tabs/button_down", 4);
            super.checked = ERE.assets.createNinePatchDrawable("tabs/button_down", 4);
            super.up = ERE.assets.createNinePatchDrawable("tabs/button_up", 4);
            super.font = Main.ERE.assets.fonts.createFont("roboto", 14);
        }
    }
}
