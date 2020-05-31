package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.main.generic.GenericCheckBox;
import de.fungistudii.kalender.main.generic.GenericDialog;

/**
 *
 * @author sreis
 */
public class EmployeeServicesDialog extends GenericDialog{

    public EmployeeServicesDialog() {
        super("Services Bearbeiten");
        
        
        Table contentTable = new Table();

        contentTable.top().padBottom(10);
        
        contentTable.add(new Tableau("Herren: ", 0)).expandY().top().uniformX().padLeft(10);
        contentTable.add(new Tableau("Damen: ", 1)).expandY().top().uniformX();
        contentTable.add(new Tableau("Sonstiges: ", 2)).expandY().top().uniformX().padRight(15);
        contentTable.row();
        contentTable.add(new Image(ERE.assets.horizontal_separator)).height(1).colspan(3).growX();
        
        
        setMainActor(contentTable);
    }

    public void show(Stage stage, Employee employee) {
        super.show(stage);
    }
    
    private class Tableau extends Table{
        public Tableau(String title, int category) {
            defaults().expandX().left().space(10);
            pad(20);
            add(new Label(title, ERE.assets.bodyStyle1)).row();
            ERE.data.root.services.values().stream().filter(s -> s.category==category).forEach((s) -> {
                add(new GenericCheckBox(s.name)).padLeft(15).row();
            });
        }
    }
}
