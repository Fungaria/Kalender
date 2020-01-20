/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.main.generic.datepicker.DatePicker.defaultHoverBehavior;
import static de.fungistudii.kalender.main.generic.datepicker.DatePicker.defaultSelectBehavior;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.Temporal;

/**
 *
 * @author sreis
 */
public class DaysGrid extends Table {

    private LocalDate selectedDate;

    DayButton[] dayButtons = new DayButton[42];

    private Array<DatePicker.DateSelectCallback> callbacks;

    public SelectBehavior selectBehavior = defaultSelectBehavior;
    public SelectBehavior hoverBehavior = defaultHoverBehavior;

    public DaysGrid(DatePicker.DateSelectCallback... callbacks) {
        this.callbacks = new Array(callbacks);
        selectedDate = LocalDate.now();

        int i = 0;
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                DayButton button = new DayButton(this);
                dayButtons[i++] = button;
                super.add(button).minSize(0).grow();
            }
            row();
        }
    }

    public void setMonth(YearMonth month) {
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

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate date) {
        if (date.isEqual(selectedDate)) {
            return;
        }
        int direction = date.compareTo(selectedDate);
        this.selectedDate = date;
        for (DatePicker.DateSelectCallback callback : callbacks) {
            callback.dateSelected(date, direction);
        }
        selectBehavior.select(dayButtons, date);
    }

    void setHoveredDate(LocalDate day) {
        hoverBehavior.select(dayButtons, day);
    }

    public static interface SelectBehavior {
        public void select(DayButton[] buttons, LocalDate date);
    }
}
