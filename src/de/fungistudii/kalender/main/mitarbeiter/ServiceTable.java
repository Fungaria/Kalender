package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.main.generic.GenericCheckBox;

/**
 *
 * @author sreis
 */
public class ServiceTable extends Section{

    private Employee employee;
    
    private Table contentTable;
    
    public ServiceTable() {
        super("Services: ");
        
        contentTable = new Table();
        
        add(contentTable).colspan(2).expand().left().padLeft(10);
        
        super.edit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.dialogManager.showEmpolyeeServices(employee);
            }
        });
    }
    
    public void setEmployee(Employee employee){
        this.employee = employee;
        
        contentTable.clearChildren();
        
        contentTable.add(new Tableau("Herren: ", 0)).expandY().top().uniformX().padRight(20);
        contentTable.add(new Tableau("Damen: ", 1)).expandY().top().uniformX().padRight(20);
        contentTable.add(new Tableau("Sonstiges: ", 2)).expandY().top().uniformX();
    }

    private class Tableau extends Table{
        public Tableau(String title, int category) {
            defaults().expandX().left().space(10);
            add(new Label(title, ERE.assets.bodyStyle1)).row();
            ERE.data.root.services.values().stream().filter(s -> s.category==category).forEach((s) -> {
                add(new GenericCheckBox(s.name)).row();
            });
        }
    }
}
