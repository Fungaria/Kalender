/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database.adapters;

import de.fungistudii.kalender.database.Employee;

/**
 *
 * @author sreis
 */
public class WorkerAdapter extends MapAdapter<Employee>{
    public WorkerAdapter() {
        super(Employee[]::new);
    }
}