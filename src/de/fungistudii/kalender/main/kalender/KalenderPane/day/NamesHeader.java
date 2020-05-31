package de.fungistudii.kalender.main.kalender.KalenderPane.day;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable.Header;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class NamesHeader extends Header {

    @Override
    public TextButton[] createLabels() {
        TextButton[] labels = new TextButton[ERE.data.root.friseure.size()];
        
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new TextButton(ERE.data.root.friseure.get(i).surname, style);
            final int workerId = i;
            labels[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ERE.mainScreen.kalender.toWeekView(workerId);
                }
            });
        }
        
        return labels;
    }

    @Override
    public void setDate(LocalDate time) {
    }
}
