package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.TerminElement;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.value.ValueUtil;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class TerminWrapper extends Table {

    private static final Calendar calendar = Calendar.getInstance();

    public TerminElement terminElement;
    
    private static final Color[] colors = new Color[]{
        new Color(118/255f, 207/255f, 145/255f, 1),
        new Color(154/255f, 183/255f, 236/255f, 1)
    };

    public TerminWrapper(Termin termin, Value elementHeight) {
        super();
        terminElement = new TerminElement(termin, colors[termin.id%colors.length]);

        int startCell = DateUtil.getMinuteOfDay(termin.start) - KalenderTable.startTime * 60;
        super.add(terminElement).height(ValueUtil.percentValue(termin.dauer / 15, elementHeight)).grow().fillY().padRight(Cons.appointmentPad).padLeft(Cons.appointmentPad).padTop(ValueUtil.percentValue(startCell / 15, elementHeight)).top();
    }

    public Termin getTermin() {
        return terminElement.getTermin();
    }
}
