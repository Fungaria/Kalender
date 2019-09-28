/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.LONG;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author sreis
 */
public class DaysGrid extends Table {

    private Date selectedDate;
    private final Calendar calendar = Calendar.getInstance(new Locale("en", "UK"));

    DayButton[] dayButtons = new DayButton[42];

    private final HardStyle hardStyle = new HardStyle();
    private final SoftStyle softStyle = new SoftStyle();

    private ButtonGroup group;

    Rectangle rect = new Rectangle();

    public DaysGrid(DatePicker.DateSelectCallback callback) {
        group = new ButtonGroup();
        int i = 0;
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

    void updateButtons() {
        this.selectedDate = calendar.getTime();
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DATE, 1);
        int beg = calendar.get(Calendar.DAY_OF_WEEK);
        if (beg == 1) {
            calendar.add(Calendar.DATE, -6);
        } else {
            calendar.add(Calendar.DATE, 2 - beg);
        }
        for (int i = 0; i < 42; i++) {
            dayButtons[i].setDate(calendar.getTime());
            if (calendar.get(Calendar.MONTH) == currentMonth) {
                dayButtons[i].setStyle(new HardStyle());
            } else {
                dayButtons[i].setStyle(new SoftStyle());
            }
            calendar.add(Calendar.DATE, 1);
        }

        calendar.setTime(selectedDate);
    }

    public String getHeaderName() {
        return calendar.getDisplayName(Calendar.MONTH, LONG, Locale.GERMANY) + "  " + calendar.get(Calendar.YEAR);
    }

    SimpleDateFormat comp = new SimpleDateFormat("MMdd");
    
    public Date getDate(){
        return calendar.getTime();
    }

    public void setDate(Date date) {
        calendar.setTime(date);
        updateButtons();
        for (DayButton dayButton : dayButtons) {
            if (comp.format(dayButton.getDay()).equals(comp.format(calendar.getTime()))) {
                dayButton.setChecked(true);
            }
        }
    }

    public void next(int month) {
        calendar.add(Calendar.MONTH, month);
        updateButtons();
    }

    public void previous(int month) {
        calendar.add(Calendar.MONTH, -month);
        updateButtons();
    }

    public Date getSelected() {
        return calendar.getTime();
    }

    private class DayButton extends TextButton {

        private final DatePicker.DateSelectCallback callback;
        private Date day;

        public DayButton(DatePicker.DateSelectCallback callback) {
            super("", new HardStyle());
            this.callback = callback;
            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (callback != null) {
                        callback.dateSelected(day);
                    }
                }
            });
        }

        public void setDate(Date day) {
            this.day = day;
            calendar.setTime(day);
            setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        }

        public Date getDay() {
            return day;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
        }
    }

    private static class HardStyle extends TextButton.TextButtonStyle {

        public HardStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/navigation/date_up", 10);
            super.over = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.down = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/navigation/date_check", 10);
            super.font = ERE.assets.fonts.createFont("robotoCondensed", 14);
            super.fontColor = ERE.assets.grey5;
        }
    }

    private static class SoftStyle extends TextButton.TextButtonStyle {

        public SoftStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/navigation/date_up", 10);
            super.over = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.down = ERE.assets.createNinePatchDrawable("kalender/navigation/date_hover", 10);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/navigation/date_check", 10);
            super.font = ERE.assets.fonts.createFont("robotoCondensed", 14);
            super.fontColor = ERE.assets.grey4;
        }
    }
}
