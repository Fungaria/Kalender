package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.Navigator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class WeekNavigator extends Navigator{
    
    private ImageButton next;
    private ImageButton previous;
    private Label dateLabel;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");
    
    
    public WeekNavigator() {
        super();
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 7, ERE.assets.grey1));
        
        ImageButton.ImageButtonStyle nextStyle = new ImageButton.ImageButtonStyle();
        nextStyle.up = ERE.assets.createDrawable("kalender/navigation/arrow_up");
        
        ImageButton.ImageButtonStyle prevStyle = new ImageButton.ImageButtonStyle();
        SpriteDrawable d = ERE.assets.createDrawable("kalender/navigation/arrow_up");
        d.getSprite().flip(true, false);
        prevStyle.up = d;
        
        Label.LabelStyle labelStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14), Color.BLACK);
        
        next = new ImageButton(nextStyle);
        previous = new ImageButton(prevStyle);
        dateLabel = new Label("Nena", labelStyle);
        
        super.defaults().space(10);
        
        super.add(previous).size(Value.percentHeight(0.4f, this));
        super.add(dateLabel).grow();
        super.add(next).size(Value.percentHeight(0.4f, this));
        
        dateLabel.setAlignment(Align.center);
        
        next.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.calendar.add(Calendar.DATE, 1);
                ERE.mainScreen.kalender.updateDate(1);
            }
        });
        previous.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.calendar.add(Calendar.DATE, -1);
                ERE.mainScreen.kalender.updateDate(-1);
            }
        });
    }

    public void setDate(Date time) {
    }
}