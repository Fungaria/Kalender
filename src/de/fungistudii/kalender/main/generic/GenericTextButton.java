package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
import static de.fungistudii.kalender.util.Fonts.LIGHT;

/**
 *
 * @author sreis
 */
public class GenericTextButton extends TextButton{
    
    public GenericTextButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    public static class OutlineStyle extends TextButtonStyle{
        public OutlineStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded", 13);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_check", 13);
            super.over = ERE.assets.createNinePatchDrawable("generic/rounded_check", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 15, LIGHT);
            super.fontColor = Color.BLACK;
        }
    }
    
    public static class FilledStyle extends TextButtonStyle{
        public FilledStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.darkGreen);
            super.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.darkGreen);
            super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.MEDIUM);
            super.fontColor = Color.WHITE;
        }
    }
    
    public static class CancelStyle extends TextButtonStyle{
        public CancelStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded_red", 13);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_red", 13);
            super.over = ERE.assets.createNinePatchDrawable("generic/rounded_red", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 15);
            super.fontColor = ERE.assets.darkRed;
        }
    }
}
