package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import static de.fungistudii.kalender.util.Fonts.LIGHT;

/**
 *
 * @author sreis
 */
public class GenericTextButton extends TextButton{
    public GenericTextButton(String text) {
        super(text, new DateButtonStyle());
        super.getLabelCell().left();
        super.getLabel().setAlignment(Align.left);
    }
    
    private static class DateButtonStyle extends TextButtonStyle{
        public DateButtonStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/dropdown", 13);
            super.down = ERE.assets.createNinePatchDrawable("generic/dropdown", 13);
            super.checked = ERE.assets.createNinePatchDrawable("generic/dropdown", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 15, LIGHT);
            super.fontColor = Color.BLACK;
        }
    }
}
