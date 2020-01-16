package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class DateNavigator extends KalenderTable.Navigator{
    
    private ImageButton next;
    private ImageButton previous;
    private TextButton dateLabel;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");
    
    
    public DateNavigator() {
        super();
        
        ImageButton.ImageButtonStyle nextStyle = new ImageButton.ImageButtonStyle();
        nextStyle.imageUp = ERE.assets.createDrawable("icons/arrow_side");
        nextStyle.up = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12);
        nextStyle.down = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12, ERE.assets.grey2);

        ImageButton.ImageButtonStyle prevStyle = new ImageButton.ImageButtonStyle();
        SpriteDrawable d = ERE.assets.createDrawable("icons/arrow_side");
        d.getSprite().flip(true, false);
        prevStyle.up = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12);
        prevStyle.down = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12, ERE.assets.grey2);
        prevStyle.imageUp = d;
        
        TextButton.TextButtonStyle labelStyle = new TextButton.TextButtonStyle();
        labelStyle.up = ERE.assets.createNinePatchDrawable("rounded/outline_middle", 12);
        labelStyle.down = ERE.assets.createNinePatchDrawable("rounded/outline_middle", 12, ERE.assets.grey2);
        labelStyle.font = ERE.assets.fonts.createFont("roboto", 16);
        labelStyle.fontColor = ERE.assets.grey6;
        
        
        next = new ImageButton(nextStyle);
        previous = new ImageButton(prevStyle);
        dateLabel = new TextButton("", labelStyle);
        
        super.add(previous).minSize(0).maxWidth(Value.percentHeight(1, this));
        super.add(dateLabel).grow().minHeight(0);
        super.add(next).minSize(0).maxWidth(Value.percentHeight(1, this));
        
        dateLabel.getLabel().setAlignment(Align.center);
        
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
        dateLabel.setText(dateFormat.format(time));
    }
}
