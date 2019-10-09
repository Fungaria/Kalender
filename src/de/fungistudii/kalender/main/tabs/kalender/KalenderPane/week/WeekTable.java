/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class WeekTable extends KalenderTable{

    private int workerId;
    
    public WeekTable(Date date) {
        super(date, new DateHeader(), new WeekNavigator());
    }
    
    public void setFriseur(int workerId){
        this.workerId = workerId;
    }

    @Override
    public KalenderGrid createGrid(Date date) {
        ((DateHeader)super.header).setDate(date);
        return new WeekGrid(date, workerId);
    }
}
