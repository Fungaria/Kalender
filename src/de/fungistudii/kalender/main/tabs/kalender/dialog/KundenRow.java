package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.generic.TitledWidget;

/**
 *
 * @author sreis
 */
public class KundenRow extends Table {

    private NameSearchField name;
    private GenericTextField phone;

    private Kunde kunde;

    public KundenRow() {
        name = new NameSearchField();
        phone = new GenericTextField("Telefon");

        defaults().spaceLeft(Value.percentWidth(0.06f, this));
        defaults().spaceRight(Value.percentWidth(0.06f, this));
        add(new TitledWidget("NAME", name)).growX().fillY();
        add(new TitledWidget("TELEFON", phone)).growX().fillY();

        name.setListener((k) -> {
            name.setText(k.toString());
            phone.setText(k.phone);
            kunde = k;
        });
    }

    public Kunde getSelected() {
        return kunde;
    }
}
