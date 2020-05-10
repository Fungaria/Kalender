package de.fungistudii.kalender.main.kalender.dialog;

import com.badlogic.gdx.utils.Array;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import java.time.LocalTime;

/**
 *
 * @author sreis
 */
public class TimePicker extends GenericDropDown<LocalTime>{
    
    public TimePicker(){
        Array<LocalTime> times = new Array<>();
        for (int i = Cons.startTime; i < Cons.endTime; i++) {
            for (int m = 0; m < 4; m++) {
                times.add(LocalTime.of(i, m*15));
            }
        }
        
        setItems(times);
    }
}
