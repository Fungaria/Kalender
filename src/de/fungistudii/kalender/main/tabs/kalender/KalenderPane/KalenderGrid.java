/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.tabs.kalender.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class KalenderGrid extends Table{
    
    public final int startTime = 8;

    private final int NUM_ROWS = 16, NUM_COLS;
    private static final float spacing = 0.1f;

    public MitarbeiterColumn[] columns;

    private final SpriteDrawable topElement = ERE.assets.createDrawable("kalender/grid/element_top");
    private final SpriteDrawable bottomElement = ERE.assets.createDrawable("kalender/grid/element_bottom");
    private final SpriteDrawable topFiller = ERE.assets.createDrawable("kalender/grid/filler_top");
    private final SpriteDrawable bottomFiller = ERE.assets.createDrawable("kalender/grid/filler_bottom");

    private Calendar calendar;
    private Date start;

    final ButtonGroup<Button> buttons = new ButtonGroup<>();

    private DatePicker.DateSelectCallback callback;

    private ContextMenu backgroundContext;
    private ContextMenu terminContext;
    
    public KalenderGrid(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, startTime);
        this.start = calendar.getTime();
        
        buttons.setMinCheckCount(0);
        
        NUM_COLS = ERE.data.root.friseure.size();
        columns = new MitarbeiterColumn[NUM_COLS];

        for (int i = 0; i < NUM_COLS; i++) {
            columns[i] = new MitarbeiterColumn(i);
        }
        
        for (int i = 0; i < NUM_COLS; i++) {
            add(columns[i]).grow().uniform();
            columns[i].setZIndex(1);
            if (i < NUM_COLS - 1) {
                Table filler = new FillerColumn();
                add(filler).minWidth(10).maxWidth(20);
                filler.setZIndex(0);
            }
        }
    }
    
    public Button getSelectedElement(){
//        for (MitarbeiterColumn column : columns) {
//            
//        }
        return buttons.getChecked();
    }
    
    public int getselectedDuration(){
        return buttons.getAllChecked().size;
    }
    
    public void reloadTable(){
        for (int i = 0; i < NUM_COLS; i++) {
            reloadColumn(i);
        }
    }
    
    public void reloadColumn(int i){
        Cell<Table> cell = getCell(columns[i]);
        cell.clearActor();
        cell.setActor(new MitarbeiterColumn(i));
    }
    
    public class MitarbeiterColumn extends Table {
        private final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");

        public Array<GridElement> elements = new Array<>();
        
        public MitarbeiterColumn(int column) {
            calendar.setTime(start);
            Termin[] termine = ERE.data.root.termine.stream().filter((termin) -> (termin.friseur == column && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(termin.start)))).toArray(Termin[]::new);
            Blockierung[] blockierungen = ERE.data.root.blockierungen.stream().filter((blockierung) -> (blockierung.friseur == column && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(blockierung.start)))).toArray(Blockierung[]::new);
            int nxtTermin = 0;
            int nxtBlock = 0;

            for (int row = 0; row < NUM_ROWS * 4; row++) {
                calendar.add(Calendar.MINUTE, 15);
                if (nxtTermin < termine.length && termine[nxtTermin].start.before(calendar.getTime())) {
                    TerminElement element = new TerminElement(termine[nxtTermin], this);
                    int cells = termine[nxtTermin].dauer / 15;
                    super.add(element).height(Value.percentHeight(cells, super.getCells().get(0).getActor())).grow();
                    row += cells - 1;
                    buttons.add(element);
                    elements.add(element);
                    nxtTermin++; 
                } else if(nxtBlock < blockierungen.length && blockierungen[nxtBlock].start.before(calendar.getTime())){
                    BlockierungElement element = new BlockierungElement(blockierungen[nxtBlock], this);
                    int cells = blockierungen[nxtBlock].dauer;
                    super.add(element).height(Value.percentHeight(cells, super.getCells().get(0).getActor())).grow();
                    row += cells - 1;
                    buttons.add(element);
                    elements.add(element);
                    nxtBlock++;
                }else {
                    BackgroundElement element = new BackgroundElement(row, column, row % 4 == 0);
                    super.add(element).grow().minSize(0);
                    buttons.add(element);
                    elements.add(element);
                }
                super.row();
            }

            super.setClip(true);
            calendar.setTime(start);
        }
    }

    private class FillerColumn extends Table {

        public FillerColumn() {
            for (int row = 0; row < NUM_ROWS * 4; row++) {
                Image img = new Image(row % 4 == 0 ? topFiller : bottomFiller);
                img.setScaling(Scaling.fillY);
                super.add(img).growX();
                super.row();
            }
        }
    }
}
