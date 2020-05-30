package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author sreis
 */
public class TwoColorLabel extends Table{

    public Label label1;
    public Label label2;
    
    public Cell cell1;
    public Cell cell2;
    
    public TwoColorLabel(String text1, BitmapFont font1, Color c1, String text2, BitmapFont font2, Color c2) {
        label1 = new Label(text1, new Label.LabelStyle(font1, c1));
        label2 = new Label(text2, new Label.LabelStyle(font2, c2));
        
        cell1 = add(label1);
        cell2 = add(label2);
    }

    public TwoColorLabel(String t1, String t2, TwoColorLabelStyle style) {
        this(t1, style.font1, style.fontColor1, t2, style.font2, style.fontColor2);
    }
    
    public void setLeftText(String text){
        label1.setText(text);
    }
    
    public void setRightText(String text){
        label2.setText(text);
    }
    
    public static class TwoColorLabelStyle{
        public BitmapFont font1;
        public BitmapFont font2;
        public Color fontColor1;
        public Color fontColor2;

        public TwoColorLabelStyle() {
        }

        public TwoColorLabelStyle(BitmapFont font1, BitmapFont font2, Color fontColor1, Color fontColor2) {
            this.font1 = font1;
            this.font2 = font2;
            this.fontColor1 = fontColor1;
            this.fontColor2 = fontColor2;
        }
    }
}
