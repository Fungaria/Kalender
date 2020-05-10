/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Stage;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.generic.ConfirmationDialog;

/**
 *
 * @author sreis
 */
public class StornoDialog extends ConfirmationDialog{
    
    private Termin termin;
    
    public StornoDialog() {
        super("Bestätigen", "Sind sie sicher das sie den ausgewählten Termin löschen möchten?");
        super.addConfirmCallback(() -> {
            NetworkData.StornoRequest request = new NetworkData.StornoRequest();
            request.id = termin.id;
            ERE.client.sendTCP(request);
        });
    }
    
    public void show(Stage stage, Termin termin){
        super.show(stage);
        this.termin = termin;
    }
}
