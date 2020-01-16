package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import static java.awt.SystemColor.text;

/**
 *
 * @author sreis
 */
public class ViewWidget extends Table{

    private final TextButton day;
    private final TextButton week;
    
    public ViewWidget() {
        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = ERE.assets.fonts.createFont("roboto", 14);
        style.fontColor = Color.BLACK;
        style.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 5, ERE.assets.grey1);
        style.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 5, ERE.assets.grey2);
        style.down = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 5, ERE.assets.grey3);
        style.checked = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 5, ERE.assets.grey3);
        
        TextButton.TextButtonStyle style2 = new TextButton.TextButtonStyle(style);
        style2.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 5, ERE.assets.grey1);
        style2.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 5, ERE.assets.grey2);
        style2.down = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 5, ERE.assets.grey3);
        style2.checked = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 5, ERE.assets.grey3);
        
        day = new TextButton("Tagesansicht", style);
        week = new TextButton("Wochenansicht", style2);
        
        add(day).grow().minHeight(0);
        add(week).grow().minHeight(0);
        
        day.setChecked(true);
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
