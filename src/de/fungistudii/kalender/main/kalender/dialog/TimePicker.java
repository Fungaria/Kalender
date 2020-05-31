package de.fungistudii.kalender.main.kalender.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
import java.time.LocalTime;

/**
 *
 * @author sreis
 */
public class TimePicker extends Table{
    
    private SelectBox<String> hours;
    private SelectBox<String> minutes;
    
    public TimePicker(){
        String[] xhours = new String[]{"08","09","10","11","12","13","14","15","16","17","18","19"};
        String[] xminutes = new String[]{"00", "15", "30", "45"};
        
        hours = new SelectBox(left);
        hours.setItems(xhours);
        
        minutes = new SelectBox<>(right);
        minutes.setItems(xminutes);
        
        add(hours).grow();
        add(new Image(ERE.assets.verticalSeparator)).width(1).minHeight(0);
        add(minutes).grow();
        
        hours.setAlignment(Align.center);
        minutes.setAlignment(Align.center);
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }
    
    private final SelectBox.SelectBoxStyle left = new SelectBox.SelectBoxStyle(){{
        super.listStyle = ERE.assets.listStyle;
        super.background = ERE.assets.createRounded("outline_left");
        super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
        super.fontColor = Color.BLACK;
        super.scrollStyle = new ScrollPane.ScrollPaneStyle();
        super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 5);
    }};
    
    private final SelectBox.SelectBoxStyle right = new SelectBox.SelectBoxStyle(){{
        super.listStyle = ERE.assets.listStyle;
        super.background = ERE.assets.createRounded("outline_right");
        super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
        super.fontColor = Color.BLACK;
        super.scrollStyle = new ScrollPane.ScrollPaneStyle();
        super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 5);
    }};

    public LocalTime getSelected() {
        return LocalTime.of(Integer.parseInt(hours.getSelected()), Integer.parseInt(minutes.getSelected()));
    }
}
