/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database.adapters;

import de.fungistudii.kalender.client.database.Friseur;

/**
 *
 * @author sreis
 */
public class WorkerAdapter extends MapAdapter<Friseur>{
    public WorkerAdapter() {
        super(Friseur[]::new);
    }
}