package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class GenericTextField extends TextField{

    public GenericTextField(String defaultText) {
        super("", new TFStyle());
        super.setMessageText(defaultText);
        super.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Keys.ENTER){
                    getStage().setKeyboardFocus(null);
                    return true;
                }
                return false;
            }
        });
    }
 
    private static final class TFStyle extends TextField.TextFieldStyle{
        public TFStyle() {
            super.disabledBackground = ERE.assets.createNinePatchDrawable("generic/rounded", 13);
            super.background = ERE.assets.createNinePatchDrawable("generic/rounded", 13);
            super.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.cursor.setMinWidth(1);
            super.selection = ERE.assets.createDrawable("generic/textfield_selection");
            super.focusedBackground = ERE.assets.createNinePatchDrawable("generic/rounded_check", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT);
            super.fontColor = Color.BLACK;
        }
    }
}
