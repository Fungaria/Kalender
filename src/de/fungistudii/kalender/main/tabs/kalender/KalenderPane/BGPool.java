package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.utils.Pool;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BGPool extends Pool<BackgroundElement>{

    @Override
    protected BackgroundElement newObject() {
        return new BackgroundElement();
    }

    public BackgroundElement obtain(Date date, int friseur, boolean top) {
        BackgroundElement element = super.obtain();
        element.init(date, friseur, top);
        return element;
    }
    
}
