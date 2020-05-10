/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.KalenderPane.day;

import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class DayTable extends KalenderTable {

    public DayTable() {
        super();
        super.header = new NamesHeader();
        super.navigator = new DateNavigator(this);
        super.initGUI();
    }

    @Override
    public KalenderGrid createGrid(LocalDate date) {
        return new DayGrid(date, super.getElementHeight());
    }

}
