/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class AddBlockDialog extends GenericDialog {

    public AddBlockDialog() {
        super("Blockierung hinzufuegen");
        Label label = new Label("Information: ", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14, Fonts.LIGHT), Color.BLACK));
        GenericTextField field = new GenericTextField("Urlaub");

        Table group = new Table();
        group.add(label).grow().padBottom(10);
        group.row();
        group.add(field).grow().padRight(30);

        setActor(group);

        super.addConfirmCallback(() -> {
            GridElement selectedElement = (GridElement) ERE.mainScreen.kalender.getKalender().getSelectedElement();
            int duration = ERE.mainScreen.kalender.getKalender().getSelectedDuration();

            NetworkData.BlockRequest request = new NetworkData.BlockRequest();
            request.duration = duration;
            request.start = selectedElement.getStart();
            request.friseur = ((GridElement) selectedElement).getFriseur();
            request.msg = field.getText();

            ERE.client.sendTCP(request);
        });
    }
}
