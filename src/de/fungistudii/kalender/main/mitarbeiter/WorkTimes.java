package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class WorkTimes extends Table{

    private int workerId = 0;
    
    public WorkTimes(int workerId) {
        this.workerId = workerId;
        Label title = new Label("Arbeitszeiten", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20, Fonts.REGULAR), ERE.assets.grey7));
        add(title);
        super.row();
        super.add(new Container()).grow();
    }
    
}