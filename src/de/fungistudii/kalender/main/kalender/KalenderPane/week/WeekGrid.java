package de.fungistudii.kalender.main.kalender.KalenderPane.week;

import com.badlogic.gdx.scenes.scene2d.ui.Value;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Blockierung;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.kalender.KalenderPane.MitarbeiterColumn;
import de.fungistudii.kalender.util.YearWeek;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public class WeekGrid extends KalenderGrid{

    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");
    
    private final int workerId;
    
    public WeekGrid(LocalDate date, Value elementHeight, int workerId) {
        super(date, elementHeight);
        columns = new MitarbeiterColumn[7];
        this.workerId = workerId;
        init();
    }

    @Override
    protected int getFriseur(int column) {
        return workerId;
    }

    @Override
    protected Termin[] getTermine(int column, LocalDate date) {
        return ERE.data.root.appointments.values().stream().filter(
                (termin) -> (termin.friseur == workerId && termin.start.toLocalDate().isEqual(date.plusDays(column)))
        ).toArray(Termin[]::new);
    }

    @Override
    protected Blockierung[] getBlockierungen(int column, LocalDate date) {
        return ERE.data.root.blockierungen.values().stream().filter(
                (blockierung) -> (blockierung.friseur == workerId && blockierung.start.toLocalDate().isEqual(date.plusDays(column)))
        ).toArray(Blockierung[]::new);
    }

    @Override
    protected LocalDateTime getStartTime(int column, LocalDate date) {
        return date.plusDays(column).atTime(8, 0);
    }
}
