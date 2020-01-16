package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.database.Vacation;
import de.fungistudii.kalender.main.generic.GenericImageButton;

/**
 *
 * @author sreis
 */
public class VacationTable extends Table {

    private VerticalGroup group;

    private int workerId;

    public VacationTable(int mitarbeiterId) {
        this.workerId = mitarbeiterId;

        group = new VerticalGroup();

        super.defaults().space(10);

        Button addButton = new GenericImageButton("icons/add");

        super.add(group).growX().fillY().padLeft(Cons.dialogOuterPadding);
        super.add(addButton).expand().padLeft(Cons.dialogOuterPadding).padRight(100).bottom().right();
        super.row().padTop(Cons.dialogRowPadTop);
        super.add(new Image(ERE.assets.createDrawable("generic/horizontal_separator"))).grow().height(1).colspan(2);
        super.row();

        group.space(10);
        group.grow();

        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NetworkData.CreateVacationRequest request = new NetworkData.CreateVacationRequest();
                request.workerId = workerId;
                ERE.client.sendTCP(request);
            }
        });

        reload();
    }

    @Override
    public float getPrefWidth() {
        return 400;
    }

    void reload() {
        group.clear();
        Friseur friseur = ERE.data.root.friseure.values().stream().filter((fr) -> (fr.id == workerId)).findFirst().get();
        for (Vacation vacation : friseur.vacations.values()) {
            group.addActor(new VacationElement(workerId, vacation));
            group.invalidate();
        }
    }
}
