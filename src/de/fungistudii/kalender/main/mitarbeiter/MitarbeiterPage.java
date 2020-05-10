package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.TabPage;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class MitarbeiterPage extends TabPage{
    
    private Table contentTable;
    private Container container;
    
    private Table[][] tables = new Table[3][ERE.data.root.friseure.size()];
    
    private int mode;
    
    private WorkerHeader header;
    
    int currentWorker = 0;
    
    private SidePanel sidePanel;
    
    public MitarbeiterPage() {
        contentTable = new Table();
        contentTable.defaults().space(10);
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        
        sidePanel = new SidePanel(this);
        
        for (int i = 0; i < ERE.data.root.friseure.size(); i++) {
            tables[0][i] = new VacationTable(i);
            tables[1][i] = new Services(i);
            tables[2][i] = new WorkTimes(i);
        }
        
        container = new Container();
        contentTable.add(container).top().left().expand();
        
        container.setActor(tables[0][0]);
        container.align(Align.topLeft);
        
        add(sidePanel).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
    }
    
    public void setMitarbeiter(int id){
        this.currentWorker = id;
        updateContent();
    }
    
    @Override
    public void show(){
        updateContent();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }

    public void updateContent() {
        container.setActor(tables[mode][currentWorker]);
        if(tables[mode][currentWorker] instanceof VacationTable)
            ((VacationTable)tables[mode][currentWorker]).reload();
    }

    void setMode(int i) {
        this.mode = i;
        updateContent();
    }
}
 