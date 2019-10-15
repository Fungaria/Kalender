package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.util.DateUtil;
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
        
        if(ERE.data.root.friseure.get(workerId).hasVacation(calendar.getTime())){
            return new VacationColumn(workerId);
        }
        
        Termin[] termine = ERE.data.root.appointments.values().stream().filter(
                (termin) -> (termin.friseur == workerId && DateUtil.compareDay(calendar.getTime(), termin.start)==0)
        ).sorted((termin1, termin2) -> {
            return DateUtil.compare(termin1.start, termin2.start);
        }).toArray(Termin[]::new);
        
        Blockierung[] blockierungen = ERE.data.root.blockierungen.values().stream().filter(
                (blockierung) -> (blockierung.friseur == workerId && DateUtil.compareDay(calendar.getTime(), blockierung.start)==0)
        ).sorted((block1, block2) -> {
            return DateUtil.compare(block1.start, block2.start);
        }).toArray(Blockierung[]::new);
        
        return new MitarbeiterColumn(workerId, termine, blockierungen, calendar.getTime());
    }
    
}
