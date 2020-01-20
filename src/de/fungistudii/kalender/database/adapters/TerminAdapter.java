package de.fungistudii.kalender.database.adapters;

import de.fungistudii.kalender.database.Termin;

/**
 *
 * @author sreis
 */
public class TerminAdapter extends MapAdapter<Termin>{
    public TerminAdapter() {
        super(Termin[]::new);
    }
}
