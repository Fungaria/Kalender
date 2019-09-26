package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.generic.TitledWidget;

/**
 *
 * @author sreis
 */
public class KundenRow extends Table{
    
    private NameSearchField name;
    private GenericTextField phone;
    
    public KundenRow() {
        name = new NameSearchField();
        phone = new GenericTextField("Telefon");
        
        defaults().spaceLeft(Value.percentWidth(0.06f, this));
        defaults().spaceRight(Value.percentWidth(0.06f, this));
        add(new TitledWidget("NAME", name)).growX().fillY();
        add(new TitledWidget("TELEFON", phone)).growX().fillY();
    }
}
