package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPage;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateHeader extends Table{
    
    private ImageButton next;
    private ImageButton previous;
    private Label dateLabel;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");
    
    private KalenderPage parent;
    
    public DateHeader(KalenderPage parent) {
        super();
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/rounded", 7));
        
        this.parent = parent;
        
        ImageButton.ImageButtonStyle nextStyle = new ImageButton.ImageButtonStyle();
        nextStyle.up = ERE.assets.createDrawable("kalender/navigation/arrow_up");
        
        ImageButton.ImageButtonStyle prevStyle = new ImageButton.ImageButtonStyle();
        SpriteDrawable d = ERE.assets.createDrawable("kalender/navigation/arrow_up");
        d.getSprite().flip(true, false);
        prevStyle.up = d;
        
        Label.LabelStyle labelStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14), Color.BLACK);
        
        next = new ImageButton(nextStyle);
        previous = new ImageButton(prevStyle);
        dateLabel = new Label("", labelStyle);
        
        super.defaults().space(10);
        
        super.add(previous).size(Value.percentHeight(0.4f, this));
        super.add(dateLabel).grow();
        super.add(next).size(Value.percentHeight(0.4f, this));
        
        dateLabel.setAlignment(Align.center);
        
        next.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                parent.calendar.add(Calendar.DATE, 1);
                parent.updateDate(1);
                parent.panel.navigation.setDate(parent.calendar.getTime());
            }
        });
        previous.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                parent.calendar.add(Calendar.DATE, -1);
                parent.updateDate(-1);
                parent.panel.navigation.setDate(parent.calendar.getTime());
            }
        });
    }

    public void setDate(Date time) {
        dateLabel.setText(dateFormat.format(time));
    }
}
