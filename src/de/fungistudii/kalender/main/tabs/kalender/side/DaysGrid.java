/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.side;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import java.util.Calendar;
import static java.util.Calendar.LONG;
import java.util.Locale;

/**
 *
 * @author sreis
 */
public class DaysGrid extends Table {

    private int currentYear = 2019;
    private int currentMonth = 1;
    private final Calendar c = Calendar.getInstance(new Locale("en","UK"));

    DayButton[] dayButtons = new DayButton[42];
    
    private final HardStyle hardStyle = new HardStyle();
    private final SoftStyle softStyle = new SoftStyle();
    
    private ButtonGroup group;
    
    
    public DaysGrid(Navigation.DateSelectCallback callback) {
        group = new ButtonGroup();
        int i=0;
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                DayButton button = new DayButton(d, callback);
                dayButtons[i++] = button;
                group.add(button);
                super.add(button).minSize(0);
            }
            row().fill();
        }
        
        updateButtons();
    }

    private void updateButtons(){
        this.currentMonth = c.get(Calendar.MONTH);
        this.currentYear = c.get(Calendar.YEAR);
        int beg = c.get(Calendar.DAY_OF_WEEK);
        if(beg==1){
            c.add(Calendar.DATE, -6);
        }else{
            c.add(Calendar.DATE, 2-beg);
        }
        for (int i = 0; i < 42; i++) {
            dayButtons[i].setText(c.get(Calendar.DAY_OF_MONTH)+"");
            if(c.get(Calendar.MONTH) == currentMonth){
                dayButtons[i].setStyle(new HardStyle());
            }else{
                dayButtons[i].setStyle(new SoftStyle());
            }
            c.add(Calendar.DATE, 1);
        }
        
        c.set(currentYear, currentMonth, 1);
    }
    
    public String getHeaderName(){
        return c.getDisplayName(Calendar.MONTH, LONG, Locale.GERMANY) + "  " + c.get(Calendar.YEAR);
    }

    public void next(){
        c.add(Calendar.MONTH, 1);
        updateButtons();
    }
    
    public void previous(){
        c.add(Calendar.MONTH, -1);
        updateButtons();
    }
    
    private class DayButton extends TextButton {
        private final Navigation.DateSelectCallback callback;
        public DayButton(final int day, Navigation.DateSelectCallback callback) {
            super("" + day, new HardStyle());
            this.callback = callback;
            super.getLabel().setFontScale(0.5f);
            super.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if(callback != null){
                        c.set(Calendar.DATE, day);
                        callback.dateSelected(c.getTime());
                    }
                }
            });
        }
    }

    private static class HardStyle extends TextButton.TextButtonStyle {
        public HardStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/navigation/date_up", 10);
            super.over = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.down = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/navigation/date_check", 10);
            super.font = ERE.assets.robotoCondensedRegular18;
            super.fontColor = ERE.assets.grey5;
        }
    }
    private static class SoftStyle extends TextButton.TextButtonStyle {
        public SoftStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/navigation/date_up", 10);
            super.over = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.down = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/navigation/date_check", 10);
            super.font = ERE.assets.robotoCondensedRegular18;
            super.fontColor = ERE.assets.grey4;
        }
    }
}
