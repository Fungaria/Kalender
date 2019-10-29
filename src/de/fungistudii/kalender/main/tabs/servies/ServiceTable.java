package de.fungistudii.kalender.main.tabs.servies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.ServiceCategory;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class ServiceTable extends Table{
    
    private final ButtonGroup group;
    
    public ServiceTable() {
        group = new ButtonGroup();
        
        super.top();
        
        for (ServiceCategory category : ERE.data.root.serviceCategories.values()) {
            super.add(new ServiceCategoryElement(category)).growX().padBottom(30).top().left();
            super.row();
        }
    }

    @Override
    public float getPrefWidth() {
        return 1000;
    }
    
    @Override
    public float getPrefHeight() {
        return 1000;
    }
    
    private class ServiceCategoryElement extends Table{

        private final Label.LabelStyle lStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20, Fonts.MEDIUM), ERE.assets.grey6);
        
        public ServiceCategoryElement(ServiceCategory category) {
            Label titleLabel = new Label(category.name, lStyle);
            titleLabel.setAlignment(Align.left);
            
            super.defaults().space(15);
            
            add(titleLabel).left();
            
            Service[] services = ERE.data.root.services.values().stream().filter((v)->v.category==category.id).toArray(Service[]::new);
            for (Service s : services) {
                addElement(s);
            }
            
        }
        
        private void addElement(Service service){
            row();
            ServiceElement element = new ServiceElement(service);
            add(element).growX().padLeft(7).height(50);
            group.add(element);
        }
    }
    
}
