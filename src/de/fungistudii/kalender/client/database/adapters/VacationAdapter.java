package de.fungistudii.kalender.client.database.adapters;

import de.fungistudii.kalender.client.database.Vacation;

/**
 *
 * @author sreis
 */
public class VacationAdapter extends MapAdapter<Vacation>{
    public VacationAdapter() {
        super(Vacation[]::new);
    }
}
