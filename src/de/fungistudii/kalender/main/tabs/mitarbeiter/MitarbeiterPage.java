package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class MitarbeiterPage extends TabPage{
    
    private Table contentTable;
    
    public MitarbeiterPage() {
        contentTable = new Table();

        contentTable.defaults().space(10);
        contentTable.setBackground(new DrawableSolid(Color.WHITE));

        contentTable.add(new Vacation()).width(300).top();
        contentTable.add(new Image()).grow().uniform();
        contentTable.add(new Services()).width(300);
        contentTable.add(new Image()).grow().uniform();
        contentTable.add(new WorkTimes()).width(300);
        
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
    }
    
    @Override
    public void show(){
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
