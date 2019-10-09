package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class WeekGrid extends KalenderGrid{

    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");
    
    private final int workerId;
    
    public WeekGrid(Date date, int workerId) {
        super(date);
        this.workerId = workerId;
        init();
    }
    
    @Override
    protected MitarbeiterColumn createMitarbeiterColumn(int column, Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DAY_OF_MONTH, column);
        
        Termin[] termine = ERE.data.root.appointments.stream().filter(
                (termin) -> (termin.friseur == workerId && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(termin.start)))
        ).toArray(Termin[]::new);
        Blockierung[] blockierungen = ERE.data.root.blockierungen.stream().filter(
                (blockierung) -> (blockierung.friseur == workerId && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(blockierung.start)))
        ).toArray(Blockierung[]::new);
        
        return new MitarbeiterColumn(column, termine, blockierungen, calendar.getTime());
    }
    
}
