/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import static de.fungistudii.kalender.util.Fonts.LIGHT;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateButton extends TextButton {

    public final DatePickerPopup navigator;

    private InputListener hideListener;
    private final Vector2 screenPosition = new Vector2();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");

    public DateButton() {
        super("", new DateButtonStyle());
        navigator = new DatePickerPopup((date, dir) ->{
            setChecked(false);
            getLabel().setText(dateFormat.format(date));
        });
        super.getLabelCell().left();
        super.getLabel().setAlignment(Align.left);
        super.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (navigator.isOpen()) {
                    navigator.hide();
                } else {
                    localToStageCoordinates(screenPosition.set(0, 0));
                    navigator.show(screenPosition.x, screenPosition.y, getWidth());
                }
            }
        });
    }
    
    public Date getDate(){
        return navigator.navigation.getDate();
    }

    private static class DateButtonStyle extends TextButtonStyle {

        public DateButtonStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded", 13);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_check", 13);
            super.checked = ERE.assets.createNinePatchDrawable("generic/rounded_check", 13);
            super.font = ERE.assets.fonts.createFont("roboto", 15, LIGHT);
            super.fontColor = Color.BLACK;
        }
    }
}
