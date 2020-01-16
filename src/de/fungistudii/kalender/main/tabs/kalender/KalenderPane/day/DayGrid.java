/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day;

import com.badlogic.gdx.scenes.scene2d.ui.Value;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.util.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DayGrid extends KalenderGrid{
    
    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");
    
    public DayGrid(Date date, Value e) {
        super(date, e);
        init();
    } 

    @Override
    protected int getFriseur(int column) {
        return column;
    }

    @Override
    protected Termin[] getTermine(int column, Date date) {
        return ERE.data.root.appointments.values().stream().filter(
                (termin) -> (termin.friseur == column && DateUtil.compareDay(date, termin.start)==0)
        ).sorted((termin1, termin2) -> {
            return DateUtil.compare(termin1.start, termin2.start);
        }).toArray(Termin[]::new);
    }

    @Override
    protected Blockierung[] getBlockierungen(int column, Date date) {
        return ERE.data.root.blockierungen.values().stream().filter(
                (blockierung) -> (blockierung.friseur == column && DateUtil.compareDay(date, blockierung.start)==0)
        ).sorted((block1, block2) -> {
            return DateUtil.compare(block1.start, block2.start);
        }).toArray(Blockierung[]::new);
    }
}
