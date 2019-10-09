/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DayGrid extends KalenderGrid{
    
    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");
    
    public DayGrid(Date date) {
        super(date);
        init();
    } 

    @Override
    protected MitarbeiterColumn createMitarbeiterColumn(int column, Date date) {
        Termin[] termine = ERE.data.root.appointments.stream().filter((termin) -> (termin.friseur == column && compareDayFormat.format(date).equals(compareDayFormat.format(termin.start)))).toArray(Termin[]::new);
        Blockierung[] blockierungen = ERE.data.root.blockierungen.stream().filter((blockierung) -> (blockierung.friseur == column && compareDayFormat.format(date).equals(compareDayFormat.format(blockierung.start)))).toArray(Blockierung[]::new);
        return new MitarbeiterColumn(column, termine, blockierungen, super.start);
    }
}
