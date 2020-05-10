/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 *
 * @author sreis
 */
public class DaysGrid extends Table {

    private LocalDate selectedDate;

    DayButton[] dayButtons = new DayButton[42];

    private Array<DatePicker.DateSelectCallback> callbacks;

    public SelectBehavior selectBehavior = new DefaultSelectBehavior();
    public SelectBehavior hoverBehavior = new DefaultHoverBehavior();

    public DaysGrid(DatePicker.DateSelectCallback... callbacks) {
        this.callbacks = new Array(callbacks);
        selectedDate = LocalDate.now();

        super.setRound(true);
        
        int i = 0;
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                DayButton button = new DayButton(this);
                dayButtons[i++] = button;
                super.add(button).minSize(0).grow();
            }
            row();
        }
        
        super.addListener(new InputListener(){
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                for (DayButton dayButton : dayButtons) {
                    dayButton.unhover();
                }
            }
        });
    }

    
    void setMonth(YearMonth month) {
        LocalDate firstOfMonth = month.atDay(1);
        LocalDate curr = firstOfMonth.with(DayOfWeek.MONDAY);

        for (int i = 0; i < 42; i++) {
            dayButtons[i].setDate(curr);
            if (curr.getMonthValue() == month.getMonthValue()) {
                dayButtons[i].setStyle(true);
            } else {
                dayButtons[i].setStyle(false);
            }
            curr = curr.plusDays(1);
        }
        selectBehavior.select(dayButtons, getSelectedDate());
    }

    LocalDate getSelectedDate() {
        return selectedDate;
    }

    void selectDate(LocalDate date) {
        if(date.equals(getSelectedDate()))
            return;
        int direction = date.compareTo(selectedDate);
        for (DatePicker.DateSelectCallback callback : callbacks) {
            callback.dateSelected(date, direction);
        }
        setSelectedDate(date);
    }
    
    void setSelectedDate(LocalDate date){
        this.selectedDate = date;
        selectBehavior.select(dayButtons, date);
    }
    
    void setHoveredDate(LocalDate day) {
        hoverBehavior.select(dayButtons, day);
        selectBehavior.select(dayButtons, getSelectedDate());
    }

    public static interface SelectBehavior {
        public void select(DayButton[] buttons, LocalDate date);
    }
}
