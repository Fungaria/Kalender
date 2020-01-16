package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.servies.ServiceTable;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class MitarbeiterPage extends TabPage{
    
    private Table contentTable;
    
    private VacationTable[] vacationTables = new VacationTable[ERE.data.root.friseure.size()];
    private Services[] serviceses = new Services[ERE.data.root.friseure.size()];
    private WorkTimes[] workTimeses = new WorkTimes[ERE.data.root.friseure.size()];
    
    private Cell<WorkTimes> timesCell;
    private Cell<Services> serviceCell;
    private Cell<VacationTable> vacationCell;
    
    private WorkerHeader header;
    
    int currentWorker = 0;
    
    public MitarbeiterPage() {
        contentTable = new Table();
        contentTable.defaults().space(10);
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        
        for (int i = 0; i < ERE.data.root.friseure.size(); i++) {
            vacationTables[i] = new VacationTable(i);
            workTimeses[i] = new WorkTimes(i);
            serviceses[i] = new Services(i);
        }
        
        header = new WorkerHeader((i)->{
            currentWorker = i;
            timesCell.clearActor();
            serviceCell.clearActor();
            vacationCell.clearActor();
            timesCell.setActor(workTimeses[i]);
            serviceCell.setActor(serviceses[i]);
            vacationCell.setActor(vacationTables[i]);
        });
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        
        contentTable.add(header).colspan(7).left().padBottom(40);
        contentTable.row();
        contentTable.add(new Image(separator)).width(1).growY();
        vacationCell = contentTable.add(vacationTables[0]).top().width(Value.percentWidth(0.3f, contentTable)).growY();
        contentTable.add(new Image(separator)).width(1).growY();
        serviceCell = contentTable.add(serviceses[0]).width(Value.percentWidth(0.3f, contentTable)).growY();
        contentTable.add(new Image(separator)).width(1).growY();
        timesCell = contentTable.add(workTimeses[0]).width(Value.percentWidth(0.3f, contentTable)).growY();
        contentTable.add(new Image(separator)).width(1).growY();
        
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
    }
    
    @Override
    public void show(){
        vacationTables[currentWorker].reload();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }

    public void updateContent() {
        vacationTables[currentWorker].reload();
    }
}
 