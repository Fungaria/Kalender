package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class GenericTextButton extends TextButton{
    
    public GenericTextButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }

    public static class OutlineStyle extends TextButtonStyle{
        public OutlineStyle() {
            super.up = ERE.assets.createRounded("outline");
            super.down = ERE.assets.createRounded("outline_check");
            super.over = ERE.assets.createRounded("outline_check");
            super.font = ERE.assets.fonts.createFont("roboto", 15);
            super.fontColor = Color.BLACK;
        }
    }
    
    public static class FilledStyle extends TextButtonStyle{
        public FilledStyle() {
            super.up = ERE.assets.createRounded("filled", ERE.assets.mediumGreen);
            super.down = ERE.assets.createRounded("filled", ERE.assets.darkGreen);
            super.over = ERE.assets.createRounded("filled", ERE.assets.darkGreen);
            super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.MEDIUM);
            super.fontColor = Color.WHITE;
        }
    }
    
    public static class CancelStyle extends TextButtonStyle{
        public CancelStyle() {
            super.up = ERE.assets.createRounded("cancel");
            super.down = ERE.assets.createRounded("cancel");
            super.over = ERE.assets.createRounded("cancel");
            super.font = ERE.assets.fonts.createFont("roboto", 15);
            super.fontColor = ERE.assets.darkRed;
        }
    }
}
