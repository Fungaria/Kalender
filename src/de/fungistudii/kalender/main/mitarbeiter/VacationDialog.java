package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.main.generic.GenericMask;

/**
 *
 * @author arein
 */
public class VacationDialog extends GenericDialog{

    public VacationDialog() {
        super("Urlaub");
        VacationTable vacations = new VacationTable(0);
        Container container = new Container();
        container.padTop(20);
        setMainActor(container).left();
        container.setActor(vacations);
    }
    
}
