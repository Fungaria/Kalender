package de.fungistudii.kalender.main.tabs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class MenuButton extends TextButton{

    public MenuButton(String text) {
        super(text, new MenuButtonStyle());
    }
    
    private static final class MenuButtonStyle extends TextButtonStyle{
        public MenuButtonStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/menu_button_up", 7);
            super.over = ERE.assets.createNinePatchDrawable("generic/menu_button_over", 7);
            super.down = ERE.assets.createNinePatchDrawable("generic/menu_button_over", 7);
            super.font = ERE.assets.fonts.createFont("roboto", 10);
            super.fontColor = Color.WHITE;
        }
    }
}
