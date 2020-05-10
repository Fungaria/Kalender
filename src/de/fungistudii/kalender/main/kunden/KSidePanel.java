package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.generic.GenericSearchTF;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class KSidePanel extends Table {

    private GenericSearchTF search;

    private Table list;

    public KSidePanel() {

        super.setBackground(new NinePatchSolid(ERE.assets.kalSide));

        super.top();
        
        list = new Table();
        list.pad(0, 5, 5, 5);

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setScrollingDisabled(true, false);

        search = new GenericSearchTF();
        search.setMessageText("Kunden suchen ...");
        search.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                updateContent(textField.getText().toLowerCase());
            }
        });

        add(search).growX().pad(8, 10, 8, 10);
        row();

        SpriteDrawable separator = ERE.assets.createDrawable("horizontal_separator_shadow");
        Image sep = new Image(separator);
        sep.setScaling(Scaling.stretchX);
        sep.setAlign(Align.top);
        add(sep).growX().height(1);

        row();
        add(scrollPane).growX();
        scrollPane.setZIndex(0);
        
        updateContent("");
    }

    public void updateContent(String prefix) {
        list.clear();
        ERE.data.root.kunden.values().stream().
                filter(c -> c.name.toLowerCase().startsWith(prefix) | c.vorname.toLowerCase().startsWith(prefix)).
                sorted((a, b) -> a.vorname.compareTo(b.vorname)).
                forEach(c -> {
            list.add(new CustButton(c)).growX();
            list.row();
            list.add(new Image(ERE.assets.horizontal_separator)).height(1);
            list.row();
        });
    }

    private Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey6);
    private Label.LabelStyle phoneStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey5);
    
    private ButtonStyle bStyle = new ButtonStyle() {
        {
            super.down = new NinePatchSolid(ERE.assets.grey4);
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
        }

    }
}
