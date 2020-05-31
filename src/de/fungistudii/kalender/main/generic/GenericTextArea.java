package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class GenericTextArea extends TextArea{
    public GenericTextArea(String defaultText) {
        super("", new TFStyle());
        super.setMessageText(defaultText);
        super.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER){
                    getStage().setKeyboardFocus(null);
                    return true;
                }
                return false;
            }
        });
        
    }
    
    private int rows = 1;

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight*rows;
    }
 
    private static final class TFStyle extends TextField.TextFieldStyle{
        public TFStyle() {
            super.disabledBackground = ERE.assets.createRounded("outline");
            super.disabledBackground.setTopHeight(15);
            super.background = super.disabledBackground;
            super.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.cursor.setMinWidth(1);
            super.selection = new NinePatchSolid(ERE.assets.grey2);
            super.focusedBackground = ERE.assets.createRounded("outline_check");
            super.focusedBackground.setTopHeight(15);
            super.font = ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT);
            super.fontColor = Color.BLACK;
            
            super.background.setLeftWidth(Cons.defaultLeftWidth);
            super.disabledBackground.setLeftWidth(Cons.defaultLeftWidth);
            super.focusedBackground.setLeftWidth(Cons.defaultLeftWidth);
        }
    }
}
