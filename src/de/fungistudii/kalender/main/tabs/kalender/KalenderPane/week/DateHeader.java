package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.Header;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateHeader extends Header {

    private TextButton[] labels;
    private final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

    private static final SimpleDateFormat format = new SimpleDateFormat("E', 'd'.'MM");
    
    private final Calendar calendar;
    
    public DateHeader() {
        super.center();
        
        style.font = ERE.assets.fonts.createFont("roboto", 19);
        style.fontColor = ERE.assets.grey6;
        style.over = new NinePatchSolid(ERE.assets.grey1);
        style.down = new NinePatchSolid(ERE.assets.grey2);

        calendar = Calendar.getInstance();
        
        this.labels = new TextButton[7];
        SpriteDrawable vs = ERE.assets.createDrawable("generic/vertical_separator", ERE.assets.grey2);
        add(new Image(vs));
        for (int i = 0; i < 7; i++) {
            labels[i] = new TextButton("MO, 30.09", style);
            labels[i].getLabel().setAlignment(Align.center);
            add(labels[i]).grow().uniform();
            add(new Image(vs));
            
            final int dayofweek = (i+1)%7 +1;
            
            labels[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ERE.mainScreen.kalender.toDayView(dayofweek);
                }
            });
        }
    }

    
    @Override
    public void setDate(Date time) {
        calendar.setTime(time);
        for (TextButton label : labels) {
            label.setText(format.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    
    @Override
    public void updateLayout(KalenderGrid grid){
        int i=0;
        for (Cell cell : grid.getCells()) {
            super.getCells().get(i).width(Value.percentWidth(1, grid.getCells().get(i).getActor()));
            i++;
        }
    }
}
