package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class Kalender extends Table {

    public final int startTime = 8;

    private final int NUM_ROWS = 16, NUM_COLS;
    private static final float spacing = 0.1f;

    public Table[] columns;

    private final SpriteDrawable timeFiller = ERE.assets.createDrawable("kalender/grid/time");
    private final SpriteDrawable topElement = ERE.assets.createDrawable("kalender/grid/element_top");
    private final SpriteDrawable bottomElement = ERE.assets.createDrawable("kalender/grid/element_bottom");
    private final SpriteDrawable topFiller = ERE.assets.createDrawable("kalender/grid/filler_top");
    private final SpriteDrawable bottomFiller = ERE.assets.createDrawable("kalender/grid/filler_bottom");
    
    private Calendar calendar;
    private Date start;

    public Kalender(Calendar calendar) {
        this.calendar = calendar;
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, startTime);
        this.start = calendar.getTime();
        NUM_COLS = ERE.data.root.friseure.size();
        columns = new Table[NUM_COLS];

        for (int i = 0; i < NUM_COLS; i++) {
            columns[i] = createKalenderColumn(i);
        }
        
        super.align(Align.left);
        add(initTimeColumn(Align.right)).width(Value.percentWidth(0.05f, this));
        for (int i = 0; i < NUM_COLS; i++) {
            add(columns[i]).grow().width(Value.percentWidth(1/8f, this));
            columns[i].setZIndex(1);
            if(i<NUM_COLS-1){
                Table filler = createFiller();
                add(filler).growX().uniform();
                filler.setZIndex(0);
            }
        }
        add(initTimeColumn(Align.left)).width(Value.percentWidth(0.05f, this));
        
        calendar.setTime(start);
    }

    private static final SimpleDateFormat compareDayFormat = new SimpleDateFormat("yyyyMMdd");

    private Table createKalenderColumn(int column) {
        Table table = new Table();
        calendar.setTime(start);
        Termin[] termine = ERE.data.root.termine.stream().filter((termin) -> (termin.friseur == column && compareDayFormat.format(calendar.getTime()).equals(compareDayFormat.format(termin.start)))).toArray(Termin[]::new);
        int nextIndex = 0;
        
        for (int row = 0; row < NUM_ROWS * 4; row++) {
            calendar.add(Calendar.MINUTE, 15);
            if(nextIndex<termine.length && termine[nextIndex].start.before(calendar.getTime())){
                TerminElement element = new TerminElement(termine[nextIndex], table);
                int cells = termine[nextIndex].dauer/15;
                table.add(element).height(Value.percentHeight(cells, table.getCells().get(0).getActor())).grow();
                row += cells-1;
                nextIndex++;
            }else{
                BackgroundElement element = new BackgroundElement(row, column, row % 4 == 0);
                table.add(element).grow().minSize(0);
            }
            table.row();
        }
        return table;
    }

    private Table createFiller() {
        Table table = new Table();
        for (int row = 0; row < NUM_ROWS * 4; row++) {
            Image img = new Image(row % 4 == 0 ? topFiller : bottomFiller);
            img.setScaling(Scaling.fillY);
            table.add(img).growX();
            table.row();
        }
        return table;
    }

    TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 10));

    private Table initTimeColumn(int align) {
        dateStyle.fontColor = ERE.assets.grey4;
        Table table = new Table();
        for (int i = 0; i < NUM_ROWS; i++) {
            TextButton element = new TextButton(startTime + i + "", dateStyle);
            element.getLabel().setAlignment((align | Align.top) & ~Align.bottom);
            element.getLabelCell().pad(6);
            table.add(element).minSize(0).growX().height(Value.percentHeight(4, columns[0].getCells().get(0).getActor()));
            table.row();
        }
        return table;
    }

    public void reloadColumn(int col) {
        columns[col] = createKalenderColumn(col);
        super.getCells().get(col*2+1).clearActor();
        super.getCells().get(col*2+1).setActor(columns[col]);
    }
}
