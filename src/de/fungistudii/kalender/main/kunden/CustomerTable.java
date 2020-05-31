/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.generic.TwoColorLabel;
import de.fungistudii.kalender.main.mitarbeiter.Section;

/**
 *
 * @author sreis
 */
public class CustomerTable extends Section{
    
    private TwoColorLabel phone;
    private TwoColorLabel mobile;
    private TwoColorLabel mail;
    private TwoColorLabel notes;
    
    public CustomerTable() {
        super("Kontaktinformationen:");
        super.row();
        
        super.left().top();
        super.defaults().spaceTop(10);
        
        phone = new TwoColorLabel("Festnetz: ", "", ERE.assets.twoColorStyle);
        mobile = new TwoColorLabel("Mobiltelefon: ", "", ERE.assets.twoColorStyle);
        mail = new TwoColorLabel("E-Mail: ", "", ERE.assets.twoColorStyle);
        notes = new TwoColorLabel("Notes: ", "", ERE.assets.twoColorStyle);
        
        phone.cell1.width(120);
        mobile.cell1.width(120);
        notes.cell1.width(120);
        mail.cell1.width(120);
        
        super.add(phone).left().padLeft(10);
        super.row();
        super.add(mobile).left().padLeft(10);
        super.row();
        super.add(mail).left().padLeft(10);
        super.row();
        super.add(notes).left().padLeft(10);
        
        edit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.dialogManager.showEditCustomer();
            }
        });
    }
    
    public void setCustomer(Customer customer){
        phone.setRightText(customer.phone);
        mail.setRightText(customer.mail);
        notes.setRightText(customer.notes);
    }
}
