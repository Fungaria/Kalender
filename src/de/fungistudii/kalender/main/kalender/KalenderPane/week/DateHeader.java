package de.fungistudii.kalender.main.kalender.KalenderPane.week;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable.Header;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 *
 * @author sreis
 */
public class DateHeader extends Header {

    @Override
    public TextButton[] createLabels() {
        TextButton[] labels = new TextButton[7];
        
        for (int i = 0; i < 7; i++) {
            labels[i] = new TextButton("MO, 30.09", style);
            final int dayofweek = (i+1)%7 +1;
            
            labels[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ERE.mainScreen.kalender.toDayView(dayofweek);
                }
            });
        }
        
        return labels;
    }

    final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.GERMANY).getFirstDayOfWeek();
    @Override
    public void setDate(LocalDate time) {
        LocalDate firstDateOfWeek = time.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(firstDateOfWeek.plusDays(i).format(DateTimeFormatter.ofPattern("E', 'd'.'MM")));
            
        }
    }
}
