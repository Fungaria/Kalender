package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

/**
 *
 * @author sreis
 */
public class ServiceWidget extends VerticalGroup{

    private ArrayList<LeistungTable> leistungen = new ArrayList<>();

    private Value height;
    
    public ServiceWidget(Value height) {
        super.space(10);
        super.left();
        this.height = height;
        addService();
    }
    
    public void addService(){
        LeistungTable leistung = new LeistungTable(Value.percentWidth(1, this), height);
        leistungen.add(leistung);
        super.addActor(leistung);
        leistung.delete.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(leistungen.size()>1){
                    leistungen.remove(leistung);
                    leistung.remove();
                }
            }
        });
    }
}
