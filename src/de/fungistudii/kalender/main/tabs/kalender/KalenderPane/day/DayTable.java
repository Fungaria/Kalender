/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day;

import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.GridElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DayTable extends KalenderTable {

    public DayTable(Date date) {
        super(date, new NamesHeader(), new DateNavigator());
    }

    @Override
    public KalenderGrid createGrid(Date date) {
        return new DayGrid(date);
    }

}
