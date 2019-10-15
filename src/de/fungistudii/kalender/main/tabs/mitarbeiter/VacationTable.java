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

        add(title);
        row();
        add(group).grow();
        row();
        add(new AddButton()).growX();

        group.space(10);
        group.grow();
        
        reload();
    }

    public void addVacation(Vacation vacation) {
        group.addActor(new VacationElement(vacation));
        group.invalidate();
    }

    void reload() {
        group.clear();
        Friseur friseur = ERE.data.root.friseure.values().stream().filter((fr) -> (fr.id == workerId)).findFirst().get();
        for (Vacation vacation : friseur.vacations.values()) {
            addVacation(vacation);
        }
    }

    private void createVacation(Vacation vacation){
        NetworkData.VacationRequest request = new NetworkData.VacationRequest();
        request.start = vacation.start;
        request.end = vacation.end;
        request.workerId = workerId;
        ERE.client.sendTCP(request);
    }
    
    private class AddButton extends ImageButton {
        public AddButton() {
            super(new ImageButtonStyle(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 6, 6, 6, 6, ERE.assets.grey1), null, null, ERE.assets.createDrawable("employes/add"), null, null));
            super.pad(10, 0, 10, 0);

            super.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Vacation vacation = new Vacation();
                    vacation.start = new Date();
                    vacation.end = new Date();
                    vacation.id = DataHandler.nextId(ERE.data.root.friseure.get(workerId).vacations);
                    addVacation(vacation);
                    createVacation(vacation);
                }
            });
        }

        @Override
        public float getPrefHeight() {
            return 50;
        }
    }
}
