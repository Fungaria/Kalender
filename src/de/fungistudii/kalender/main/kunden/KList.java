package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class KList extends ScrollPane {

    private Table list;
    private ButtonGroup group;
    private CustomerPage parent;
    
    public KList(CustomerPage parent) {
        super(new Table());
        
        this.parent = parent;
        
        list = (Table)getActor();

        group = new ButtonGroup();
        
        super.setScrollingDisabled(true, false);
        updateContent("");
    }

    public void updateContent(String prefix) {
        list.clear();
        group.clear();
        
        ERE.data.root.kunden.values().stream().
                filter(c -> c.name.toLowerCase().startsWith(prefix) | c.vorname.toLowerCase().startsWith(prefix)).
                sorted((a, b) -> a.vorname.compareTo(b.vorname)).
                forEach(c -> {
                    CustButton button = new CustButton(c);
                    group.add(button);
                    list.add(button).growX();
                    list.row();
                    list.add(new Image(ERE.assets.horizontal_separator)).height(1).growX();
                    list.row();
                });
    }

    private Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey6);
    private Label.LabelStyle phoneStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey5);
    
    private ButtonStyle bStyle = new ButtonStyle() {
        {
            super.checked = new NinePatchSolid(ERE.assets.grey3);
            super.down = new NinePatchSolid(ERE.assets.grey3);
            super.over = new NinePatchSolid(ERE.assets.grey3);
            super.up = new NinePatchSolid(ERE.assets.kalSide);
        }
    };

    private class CustButton extends Button {
        private Customer customer;

        public CustButton(Customer customer) {
            super(bStyle);
            
            Label n = new Label(customer.vorname+ " " + customer.name, nameStyle);
            Label p = new Label(customer.phone, phoneStyle);
            
            super.add(n).pad(8, 15, 8, 10).left();
            super.add(p).expand().pad(8, 15, 8, 10).right();
            this.customer = customer;
            
            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    parent.setSelectedCustomer(customer);
                }
            });
        }

    }
}
