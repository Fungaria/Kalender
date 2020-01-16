package de.fungistudii.kalender.main.tabs.servies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class ServicePage extends TabPage{
    
    private Container<ServiceTable> contentTable;
    
    private ServiceTable serviceTable = new ServiceTable();
    private final ServiceSide sidePanel = new ServiceSide();
    
    public final AddServiceDialog dialog = new AddServiceDialog();
    
    public ServicePage() {
        contentTable = new Container<>();
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        
        contentTable.setActor(serviceTable);
        
        add(sidePanel).prefWidth(Value.percentWidth(Cons.sideBarPercentWidth, this)).minWidth(200).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.15f, this));
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

    public void updateContent() { 
        serviceTable = new ServiceTable();
        contentTable.setActor(serviceTable);
    }
}
