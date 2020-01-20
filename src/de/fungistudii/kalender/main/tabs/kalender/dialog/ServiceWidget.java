package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.fungistudii.kalender.database.Service;
import java.util.ArrayList;

/**
 *
 * @author sreis
 */
public class ServiceWidget extends Table{

    private ArrayList<ServiceElement> services = new ArrayList<>();

    private Value height;
    
    public ServiceWidget(Value height) {
        super.left();
        this.height = height;
        this.addService();
    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public float getMaxWidth() {
        return -1;
    }
    
    public void addService(){
        ServiceElement leistung = new ServiceElement(Value.percentWidth(1, this), height);
        services.add(leistung);
        super.add(leistung).grow().row();
        leistung.delete.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(services.size()>1){
                    services.remove(leistung);
                    leistung.remove();
                }
            }
        });
    }
    
   public Service getService(int i){
       return services.get(i).createService();
   }
   
   public int getServiceLenght(){
       return services.size();
   }
}
