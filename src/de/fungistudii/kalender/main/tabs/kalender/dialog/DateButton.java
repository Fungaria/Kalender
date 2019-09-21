/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import static de.fungistudii.kalender.util.Fonts.LIGHT;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class DateButton extends TextButton{
    
    public final DateNavigationWiget navigator;
    
    public final Calendar calendar = Calendar.getInstance();
    
    public DateButton() {
        super("Di, 13. September 2019", new DateButtonStyle());
        navigator = new DateNavigationWiget(this);
        super.getLabelCell().left();
        super.getLabel().setAlignment(Align.left);
        super.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(navigator.isOpen())
                    navigator.hide();
                else
                    navigator.show();
            }
        });
    }
    
    private static class DateButtonStyle extends TextButtonStyle{
        public DateButtonStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/dropdown", 13);
            super.down = ERE.assets.createNinePatchDrawable("generic/dropdown_selected", 13);
            super.checked = ERE.assets.createNinePatchDrawable("generic/dropdown_selected", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 15, LIGHT);
            super.fontColor = Color.BLACK;
        }
    }
}
