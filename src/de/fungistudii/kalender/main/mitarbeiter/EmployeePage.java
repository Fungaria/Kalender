package de.fungistudii.kalender.main.mitarbeiter;

import de.fungistudii.kalender.main.kunden.*;
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
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.main.TabPage;
import de.fungistudii.kalender.main.generic.GenericSearchTF;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.actors.ColorContainer;

/**
 *
 * @author sreis
 */
public class EmployeePage extends TabPage{
    
    MList sidePanel;
    Label title;
    ContentPane contentTable;
    
    private GenericSearchTF search;
    
    public EmployeePage() {
        sidePanel = new MList(this);
        contentTable = new ContentPane();
        
        super.setBackground(new NinePatchSolid(ERE.assets.kalBG));
        
        title = new Label("Dummy dummy", ERE.assets.titleStyle1);
        
        search = new GenericSearchTF();
        search.setMessageText("Mitarbeiter suchen ...");
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
        
        
        super.add(new ColorContainer(search, ERE.assets.kalSide, 10)).fillX();
        super.add(title).growX().padLeft(20).padBottom(10).bottom();
        super.row();
        super.add(sep).height(1).colspan(2).growX();
        super.row();
        super.add(new ColorContainer(sidePanel, ERE.assets.kalSide, 0, Align.top)).growY().width(500);
        super.add(contentTable).minSize(0).pad(10, 10, 5, 10).left().top().growX();
        
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

    void setSelectedEmployee(Employee employee) {
        title.setText(employee.toString());
        contentTable.setEmployee(employee);
    }
}
