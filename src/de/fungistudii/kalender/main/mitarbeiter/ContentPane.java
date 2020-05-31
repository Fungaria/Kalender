package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;

/**
 *
 * @author sreis
 */
public class ContentPane extends Table{

    ContactTable contactTable;
    ServiceTable serviceTable;
    VacationTable vacationTable;
    
    
    public ContentPane() {
        super();
        
        contactTable = new ContactTable();
        serviceTable = new ServiceTable();
        vacationTable = new VacationTable();
        
        defaults().left().padLeft(20);
        
        add(contactTable).grow();
        row();
        add(new Image(ERE.assets.horizontal_separator)).growX().height(1).padLeft(0);
        row();
        add(serviceTable).grow();
        row();
        add(new Image(ERE.assets.horizontal_separator)).growX().height(1).padLeft(0);
        row();
        add(vacationTable).grow();
        
    }

    void setEmployee(Employee employee) {
        contactTable.setEmployee(employee);
        serviceTable.setEmployee(employee);
    }
    
}
