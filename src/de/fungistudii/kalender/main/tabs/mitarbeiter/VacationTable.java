package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.DataHandler;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.database.Vacation;
import de.fungistudii.kalender.util.Fonts;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class VacationTable extends Table {

    private VerticalGroup group;
    
    private int workerId = 0;
    
    public VacationTable() {
        Label title = new Label("Urlaub", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20, Fonts.MEDIUM), ERE.assets.grey5));

        group = new VerticalGroup();

        super.defaults().space(10);

        super.add(title);
        super.row();
        super.add(group).grow();
        super.row();
        super.add(new AddButton()).growX();

        group.space(10);
        group.grow();
        
        reload();
    }

     void reload() {
        group.clear();
        Friseur friseur = ERE.data.root.friseure.values().stream().filter((fr) -> (fr.id == workerId)).findFirst().get();
        for (Vacation vacation : friseur.vacations.values()) {
            group.addActor(new VacationElement(vacation));
            group.invalidate();
        }
    }

    private class AddButton extends ImageButton {
        public AddButton() {
            super(new ImageButtonStyle(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 6, 6, 6, 6, ERE.assets.grey1), null, null, ERE.assets.createDrawable("employes/add"), null, null));
            super.pad(10, 0, 10, 0);

            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    NetworkData.CreateVacationRequest request = new NetworkData.CreateVacationRequest();
                    request.workerId = workerId;
                    ERE.client.sendTCP(request);
                }
            });
        }

        @Override
        public float getPrefHeight() {
            return 50;
        }
    }
}
