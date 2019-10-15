package de.fungistudii.kalender.client.database.adapters;

import de.fungistudii.kalender.client.database.Termin;

/**
 *
 * @author sreis
 */
public class TerminAdapter extends MapAdapter<Termin>{
    public TerminAdapter() {
        super(Termin[]::new);
    }
}
