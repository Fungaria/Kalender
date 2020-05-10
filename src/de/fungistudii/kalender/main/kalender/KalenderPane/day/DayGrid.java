/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.KalenderPane.day;

import com.badlogic.gdx.scenes.scene2d.ui.Value;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Blockierung;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.kalender.KalenderPane.MitarbeiterColumn;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public class DayGrid extends KalenderGrid{
    
    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");
    
    public DayGrid(LocalDate date, Value e) {
        super(date, e);
        columns = new MitarbeiterColumn[ERE.data.root.friseure.size()];
        init();
    } 

    @Override
    protected int getFriseur(int column) {
        return column;
    }

    @Override
    protected Termin[] getTermine(int column, LocalDate date) {
        return ERE.data.root.appointments.values().stream().filter(
                (termin) -> (termin.friseur == column && termin.start.toLocalDate().isEqual(date))
        ).toArray(Termin[]::new);
    }

    @Override
    protected Blockierung[] getBlockierungen(int column, LocalDate date) {
        return ERE.data.root.blockierungen.values().stream().filter(
                (blockierung) -> (blockierung.friseur == column && blockierung.start.toLocalDate().isEqual(date))
        ).toArray(Blockierung[]::new);
    }

    @Override
    protected LocalDateTime getStartTime(int column, LocalDate date) {
        return date.atTime(8, 0);
    }
}
