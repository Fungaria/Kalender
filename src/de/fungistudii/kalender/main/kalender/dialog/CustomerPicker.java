package de.fungistudii.kalender.main.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.generic.GenericSearchField;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class CustomerPicker extends Container{

    private final GenericSearchField<Customer> search;
    private final CustomerBoi selected;
    
    public CustomerPicker() {
        super.fill();
        
        selected = new CustomerBoi();
        
        search = new GenericSearchField<>((String s, Customer k) -> (k.name.toLowerCase().startsWith(s) || (k.vorname.toLowerCase() + " " + k.name.toLowerCase()).startsWith(s)));
        search.setMessageText("Enter name");
        search.setItems(ERE.data.root.kunden.values());
        search.setListener((k) -> {
            setActor(selected);
        });
        
        setActor(search);
    }
    
    private class CustomerBoi extends Table{

        public CustomerBoi() {
            Table boi = new Table();
            boi.setBackground(ERE.assets.createRounded("outline"));
            boi.add(new Label("Peter Reiser", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14, Fonts.MEDIUM), ERE.assets.grey7)));
            boi.add(new Label("+49 346 556", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14, Fonts.MEDIUM), ERE.assets.grey7))).expand().right();
            
            ImageButton delete = new ImageButton(ERE.assets.createDrawable("icons/cross", ERE.assets.grey3));
            
            
            add(boi).grow().padRight(10);
            add(delete).size(Cons.defaultRowHeight*0.5f);
        }

        @Override
        public float getPrefHeight() {
            return Cons.defaultRowHeight;
        }
    }
}
