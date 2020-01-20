package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.utils.Pool;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public class BGPool extends Pool<BackgroundElement>{

    @Override
    protected BackgroundElement newObject() {
        return new BackgroundElement();
    }

    public BackgroundElement obtain(LocalDateTime date, int row) {
        BackgroundElement element = super.obtain();
        element.init(date, row, row % 4 == 0);
        return element;
    }
    
}
