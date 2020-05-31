package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.TabPage;
import de.fungistudii.kalender.main.generic.GenericSearchTF;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.actors.ColorContainer;

/**
 *
 * @author sreis
 */
public class CustomerPage extends TabPage{
    
    KList sidePanel;
    Label title;
    CustomerTable contentTable;
    
    private GenericSearchTF search;
    
    public CustomerPage() {
        sidePanel = new KList(this);
        contentTable = new CustomerTable();
        
        super.setBackground(new NinePatchSolid(ERE.assets.kalBG));
        
        title = new Label("Dummy dummy", ERE.assets.titleStyle1);
        
        search = new GenericSearchTF();
        search.setMessageText("Kunden suchen ...");
        search.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                sidePanel.updateContent(textField.getText().toLowerCase());
            }
        });

        SpriteDrawable separator = ERE.assets.createDrawable("horizontal_separator_shadow");
        Image sep = new Image(separator);
        sep.setScaling(Scaling.stretchX);
        sep.setAlign(Align.top);
        
        
        add(new ColorContainer(search, ERE.assets.kalSide, 10)).fillX();
        add(title).growX().padLeft(20).padBottom(10).bottom();
        row();
        add(sep).height(1).colspan(2).growX();
        row();
        add(new ColorContainer(sidePanel, ERE.assets.kalSide, 0, Align.top)).growY().width(500);
        add(contentTable).minSize(0).pad(20, 20, 5, 10).left().top().growX();
        
        sep.setZIndex(100);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }

    void setSelectedCustomer(Customer customer) {
        title.setText(customer.vorname+" "+customer.name);
        contentTable.setCustomer(customer);
    }
}
