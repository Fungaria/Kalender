/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.Main.ERE;
import static de.fungistudii.kalender.main.generic.DatePicker.defaultHoverBehavior;
import static de.fungistudii.kalender.main.generic.DatePicker.defaultSelectBehavior;
import de.fungistudii.kalender.util.DateUtil;
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
    private Date firstOfMonth;
    private final Calendar calendar = Calendar.getInstance();

    private int currentMonth;

    DayButton[] dayButtons = new DayButton[42];

    private final HardStyle hardStyle = new HardStyle();
    private final SoftStyle softStyle = new SoftStyle();

    private Array<DatePicker.DateSelectCallback> callbacks;
    private SelectBehavior selectBehavior = defaultSelectBehavior;
    private SelectBehavior hoverBehavior = defaultHoverBehavior;

    
    public DaysGrid(DatePicker.DateSelectCallback... callbacks) {
        this.callbacks = new Array(callbacks);
        calendar.set(Calendar.SECOND, 0);
        selectedDate = calendar.getTime();
        int i = 0;
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                DayButton button = new DayButton(i, d);
                dayButtons[i++] = button;
                super.add(button).minSize(0).grow();
            }
            row();
        }
        updateButtons();
    }

    public SelectBehavior getSelectBehavior() {
        return selectBehavior;
    }

    public void setSelectBehavior(SelectBehavior selectBehavior) {
        this.selectBehavior = selectBehavior;
    }

    public SelectBehavior getHoverBehavior() {
        return hoverBehavior;
    }

    void setHoverBehavior(SelectBehavior sh) {
        this.hoverBehavior = selectBehavior;
    }
    
    Date firstOfMonth(){
        return firstOfMonth;
    }

    private void updateButtons() {
        currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DATE, 1);
        firstOfMonth = calendar.getTime();
        int beg = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, beg == 1 ? -6 : 2 - beg);

        for (int i = 0; i < 42; i++) {
            dayButtons[i].setDate(calendar.getTime());
            if (calendar.get(Calendar.MONTH) == currentMonth) {
                dayButtons[i].setStyle(hardStyle);
            } else {
                dayButtons[i].setStyle(softStyle);
            }
            calendar.add(Calendar.DATE, 1);
        }
        selectBehavior.select(dayButtons, getDate());
    }

    public String getHeaderName() {
        calendar.setTime(firstOfMonth);
        return calendar.getDisplayName(Calendar.MONTH, LONG, Locale.GERMANY) + "  " + calendar.get(Calendar.YEAR);
    }

    public Date getDate() {
        return selectedDate;
    }

    public int getMonth() {
        return currentMonth;
    }

    public void setSelectedDate(Date date) {
        if (selectBehavior != null) {
            selectBehavior.select(dayButtons, date);
        }
        this.selectedDate = date;
    }

    public void next(Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        updateButtons();
    }

    public void previous(Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        updateButtons();
    }

    public class DayButton extends TextButton {

        private Date day;
        public final int index;
        public final int dayofweek;

        public DayButton(int index, int dayofweek) {
            super("", new HardStyle());
            this.index = index;
            this.dayofweek = dayofweek;
            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(DateUtil.compareDay(day, selectedDate)==0)
                        return;
                    int direction = DateUtil.compareDay(day, selectedDate);
                    setSelectedDate(day);
                    for (DatePicker.DateSelectCallback callback : callbacks) {
                        callback.dateSelected(day, direction);
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    hoverBehavior.select(dayButtons, day);
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
    }

    public static interface SelectBehavior {

        public void select(DayButton[] buttons, Date date);
    }

    private static class HardStyle extends TextButton.TextButtonStyle {

        public HardStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, Color.CLEAR);
            super.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.grey2);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
            super.checked = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
            super.font = ERE.assets.fonts.createFont("robotoCondensed", 14);
            super.fontColor = ERE.assets.grey5;
        }
    }

    private static class SoftStyle extends TextButton.TextButtonStyle {

        public SoftStyle() {
            super.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, Color.CLEAR);
            super.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.grey2);
            super.down = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
            super.checked = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
            super.font = ERE.assets.fonts.createFont("robotoCondensed", 14);
            super.fontColor = ERE.assets.grey4;
        }
    }
}
