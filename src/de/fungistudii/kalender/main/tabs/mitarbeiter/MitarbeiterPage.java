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
    
    private VacationTable vacations;
    
    private WorkerHeader header;
    
    public MitarbeiterPage() {
        contentTable = new Table();
        contentTable.defaults().space(10);
        contentTable.setBackground(new DrawableSolid(Color.WHITE));

        vacations =new VacationTable();
        
        header = new WorkerHeader();
        
        contentTable.add(header).colspan(5);
        contentTable.row();
        contentTable.add(vacations).top().width(Value.percentWidth(0.3f, contentTable));
        contentTable.add(new Image()).grow().uniform();
        contentTable.add(new Services());
        contentTable.add(new Image()).grow().uniform();
        contentTable.add(new WorkTimes());
        
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
    }
    
    @Override
    public void show(){
        vacations.reload();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }

    public void updateContent() {
        vacations.reload();
    }
}
