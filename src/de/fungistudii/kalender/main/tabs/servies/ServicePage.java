package de.fungistudii.kalender.main.tabs.servies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class ServicePage extends TabPage{
    
    private Table contentTable;
    
    public ServicePage() {
        contentTable = new Table();
        contentTable.defaults().space(10);
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        
        
        
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
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
}
