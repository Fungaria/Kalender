package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author sreis
 */
public class TwoColorLabel extends HorizontalGroup{

    private Label label1;
    private Label label2;
    
    public TwoColorLabel(String text1, BitmapFont font1, Color c1, String text2, BitmapFont font2, Color c2) {
        label1 = new Label(text1, new Label.LabelStyle(font1, c1));
        label2 = new Label(text2, new Label.LabelStyle(font2, c2));
        
        addActor(label1);
        addActor(label2);
    }
    
    public void setLeftText(String text){
        label1.setText(text);
    }
    
    public void setRightText(String text){
        label2.setText(text);
    }
}
