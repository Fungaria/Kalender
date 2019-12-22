package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.IconTextButton;
import static java.awt.SystemColor.text;

/**
 *
 * @author sreis
 */
public class ViewWidget extends Table{

    private final IconTextButton day;
    private final Button week;
    
    public ViewWidget() {
        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = ERE.assets.fonts.createFont("roboto", 16);
        style.fontColor = ERE.assets.grey6;
        style.up = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12);
        style.down = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12, ERE.assets.grey2);
        style.checked = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12, ERE.assets.grey2);
        
        TextButton.TextButtonStyle style2 = new TextButton.TextButtonStyle(style);
        style2.up = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12);
        style2.down = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12, ERE.assets.grey2);
        style2.checked = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12, ERE.assets.grey2);
        
        day = new IconTextButton("Tag", "kalender_day", style);
        week = new IconTextButton("Woche", "kalender_week", style2);
        
        add(day).grow().minHeight(0).width(110);
        add(week).grow().minHeight(0).width(120);
        
        setView(true);
        
        ButtonGroup group = new ButtonGroup(day, week);
        
        day.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.toDayView(0);
            }
        });
        week.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.toWeekView(0);
            }
        });
    }
    
    public void setView(boolean day){
        this.day.setChecked(day);
        this.week.setChecked(!day);
    }
    
}
