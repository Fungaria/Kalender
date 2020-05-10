/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.KalenderPane.week;

import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class WeekTable extends KalenderTable{

    private int workerId;
    
    public WeekTable() {
        super();
        super.header = new DateHeader();
        super.navigator = new WeekNavigator(this);
        super.initGUI();
    }
    
    public void setFriseur(int workerId){
        this.workerId = workerId;
        ((WeekNavigator)super.navigator).setWorker(workerId);
    }

    @Override
    public KalenderGrid createGrid(LocalDate date) {
        ((DateHeader)super.header).setDate(date);
        return new WeekGrid(date, super.getElementHeight(), workerId);
    }
}