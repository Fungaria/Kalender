/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.main.generic.GenericSearchField;

/**
 *
 * @author sreis
 */
public class NameSearchField extends GenericSearchField<Kunde>{
    public NameSearchField() {
        super((String s, Kunde k) -> (k.name.startsWith(s) || (k.vorname+" "+k.name).startsWith(s)));
        super.setMessageText("Enter name");
        super.setItems(ERE.data.root.kunden.values());
    }

    public int getId(){
        return getSelected().id;
    }
}
