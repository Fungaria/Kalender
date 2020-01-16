/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.GridElement;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class AddBlockDialog extends GenericMask {

    public AddBlockDialog() {
        super(1, "Blockierung hinzufuegen");
        Label label = new Label("Information: ", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14, Fonts.LIGHT), Color.BLACK));
        GenericTextField field = new GenericTextField("Urlaub");

        addC(label);
        rowC();
        addC(field);

        super.addConfirmCallback(() -> {
            BackgroundElement selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();
            int duration = ERE.mainScreen.kalender.getKalender().getSelectedDuration();

            
            NetworkData.BlockRequest request = new NetworkData.BlockRequest();
            request.duration = duration;
            request.start = selectedElement.getStart();
            request.friseur = selectedElement.getFriseur();
            request.msg = field.getText();

            ERE.client.sendTCP(request);
        });
    }
}
