package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;

/**
 *
 * @author sreis
 */
public class ServiceTable extends Section{

    public ServiceTable() {
        super("Services: ");
    }
    
    public void setEmployee(Employee employee){
        add(new Label("", ERE.assets.bodyStyle1));
        ERE.data.root.services.values().stream().filter(s -> s.category==0).forEach((s) -> {
            
        });
        ERE.data.root.services.values().stream().filter(s -> s.category==1).forEach((s) -> {
            
        });
        ERE.data.root.services.values().stream().filter(s -> s.category==2).forEach((s) -> {
            
        });
    }
}
