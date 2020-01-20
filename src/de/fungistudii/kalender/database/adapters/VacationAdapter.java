package de.fungistudii.kalender.database.adapters;

import de.fungistudii.kalender.database.Vacation;

/**
 *
 * @author sreis
 */
public class VacationAdapter extends MapAdapter<Vacation>{
    public VacationAdapter() {
        super(Vacation[]::new);
    }
}
