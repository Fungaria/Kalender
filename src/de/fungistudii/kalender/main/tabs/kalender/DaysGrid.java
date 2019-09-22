/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import java.util.Calendar;
import static java.util.Calendar.LONG;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author sreis
 */
public class DaysGrid extends Table {

    private int currentYear = 2019;
    private int currentMonth = 1;
    private final Calendar calendar = Calendar.getInstance(new Locale("en","UK"));

    DayButton[] dayButtons = new DayButton[42];
    
    private final HardStyle hardStyle = new HardStyle();
    private final SoftStyle softStyle = new SoftStyle();
    
    private ButtonGroup group;
    
    
    public DaysGrid(Navigation.DateSelectCallback callback) {
        group = new ButtonGroup();
        int i=0;
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                DayButton button = new DayButton(callback);
                dayButtons[i++] = button;
                group.add(button);
                super.add(button).minSize(0);
            }
            row().fill();
        }
        updateButtons();
        updateButtons();
    }

    void updateButtons(){
        this.currentMonth = calendar.get(Calendar.MONTH);
        this.currentYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.DATE, 1);
        int beg = calendar.get(Calendar.DAY_OF_WEEK);
        if(beg==1){
            calendar.add(Calendar.DATE, -6);
        }else{
            calendar.add(Calendar.DATE, 2-beg);
        }
        for (int i = 0; i < 42; i++) {
            dayButtons[i].setDate(calendar.get(Calendar.DAY_OF_MONTH));
            if(calendar.get(Calendar.MONTH) == currentMonth){
                dayButtons[i].setStyle(new HardStyle());
            }else{
                dayButtons[i].setStyle(new SoftStyle());
            }
            calendar.add(Calendar.DATE, 1);
        }
        
        calendar.set(currentYear, currentMonth, 1);
    }
    
    public String getHeaderName(){
        return calendar.getDisplayName(Calendar.MONTH, LONG, Locale.GERMANY) + "  " + calendar.get(Calendar.YEAR);
    }

    public void setDate(Date date){
        calendar.setTime(date);
        for (DayButton dayButton : dayButtons) {
            if(dayButton.getDay() == calendar.get(Calendar.DATE))
                dayButton.setChecked(true);
        }
        updateButtons();
    }
    
    public void next(){
        calendar.add(Calendar.MONTH, 1);
        updateButtons();
    }
    
    public void previous(){
        calendar.add(Calendar.MONTH, -1);
        updateButtons();
    }
    
    public Date getSelected(){
        return calendar.getTime();
    }
    
    private class DayButton extends TextButton {
        private final Navigation.DateSelectCallback callback;
        
        private int day;
        
        public DayButton(Navigation.DateSelectCallback callback) {
            super("", new HardStyle());
            this.callback = callback;
            super.getLabel().setFontScale(0.5f);
            super.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if(callback != null){
                        calendar.set(Calendar.DATE, day);
                        callback.dateSelected(calendar.getTime());
                    }
                }
            });
        }
        
        public void setDate(int day){
            this.day = day;
            setText(day+"");
        }

        public int getDay() {
            return day;
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
