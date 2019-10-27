package de.fungistudii.kalender.main.tabs.mitarbeiter;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.main.generic.GenericDropDown;

/**
 *
 * @author sreis
 */
public class WorkerHeader extends GenericDropDown<Friseur>{
    public WorkerHeader(){
        super(null, null, null, ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
    }
}
