/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.GenericDropDown;

/**
 *
 * @author sreis
 */
public class DurationPicker extends Table{

    public GenericDropDown<String> timeHours;
    public GenericDropDown<String> timeMins;
    
    private int startTime = 8;
    private int numHours = 13;
    
    public DurationPicker() {
        NinePatchDrawable rounded = ERE.assets.createRounded("rounded/outline");
        NinePatchDrawable roundedCheck = ERE.assets.createRounded("rounded/outline_check");
        
        String[] hours = new String[numHours];
        for (int i = 0; i < numHours; i++) {
            hours[i] = ""+(startTime+i);
        }
        
        timeHours = new GenericDropDown<>(null, rounded, roundedCheck, hours);
        timeMins = new GenericDropDown<>(null, rounded, roundedCheck, new String[]{"00", "15", "30", "45"});
        
        Image image = new Image(ERE.assets.createDrawable("kalender/time_dots"));
        image.setScaling(Scaling.fit);
        add(timeHours).grow();
        add(image).height(Value.percentHeight(0.15f, timeHours)).padLeft(1).left();
        add(timeMins).grow();
    }
    
    
    public int getHour(){
        return startTime+timeHours.getSelectedIndex();
    }
    
    public int getMinute(){
        return timeMins.getSelectedIndex()*15;
    }
    
    public int getMinuteOfDay(){
        return getHour()*60+getMinute();
    }
}
