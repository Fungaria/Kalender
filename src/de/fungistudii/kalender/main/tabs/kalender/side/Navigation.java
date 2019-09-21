/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.side;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class Navigation extends Table{

    private DaysGrid dates;
    private MonthHeader monthHeader;
    private WeekLabel weekLabel;
    
    public Navigation(DateSelectCallback callback) {
        dates = new DaysGrid(callback);
        weekLabel = new WeekLabel();
        monthHeader = new MonthHeader();
        
        Drawable separator = ERE.assets.createDrawable("kalender/navigation/separator");
        
        super.add(monthHeader).growX().prefHeight(Value.percentWidth(0.8f/8f, this));
        super.row();
        super.add(new Image(separator)).growX();
        super.row();
        super.add(weekLabel).growX().prefHeight(Value.percentWidth(0.8f/7f, this));
        super.row();
        super.add(new Image(separator)).growX();
        super.row();
        super.add(dates).growX().prefHeight(Value.percentWidth((6f/7f), this));
        super.row();
        super.add(new Image(separator)).growX();
    }
    
    private void updateHeader(){
        monthHeader.label.setText(dates.getHeaderName());
    }
    
    private class MonthHeader extends Table{
        public Label label;
        public ImageButton next;
        public ImageButton previous;
        public MonthHeader() {
            label = new Label("Januar 2020", new Label.LabelStyle(ERE.assets.robotoCondensedBold, ERE.assets.grey5));
            label.setFontScale(0.6f);
            
            SpriteDrawable arrowf = ERE.assets.createDrawable("kalender/navigation/arrow_up");
            SpriteDrawable arrowb = ERE.assets.createDrawable("kalender/navigation/arrow_up");
            arrowb.getSprite().flip(true, false);
            next = new ImageButton(arrowf);
            previous = new ImageButton(arrowb);
            
            super.left();
            add(previous).minSize(0).prefSize(Value.percentHeight(0.7f, this));
            add(label).prefWidth(Value.percentWidth(0.9f, this));
            add(next).minSize(0).prefSize(Value.percentHeight(0.7f, this));
            label.setAlignment(Align.center);
            
            next.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dates.next();
                    updateHeader();
                }
            });
            previous.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dates.previous();
                    updateHeader();
                }
            });
        }
    }
    
    private static class WeekLabel extends Table{
        public WeekLabel() {
            Label.LabelStyle style = new Label.LabelStyle(ERE.assets.robotoCondensedRegular10, ERE.assets.grey5);
            add(createLabel("Mo", style)).uniform().expand();
            add(createLabel("Di", style)).uniform().expand();
            add(createLabel("Mi", style)).uniform().expand();
            add(createLabel("Do", style)).uniform().expand();
            add(createLabel("Fr", style)).uniform().expand();
            add(createLabel("Sa", style)).uniform().expand();
            add(createLabel("So", style)).uniform().expand();
        }
        
        private Label createLabel(String s, Label.LabelStyle style){
            Label l = new Label(s, style);
            l.setFontScale(0.5f);
            super.align(Align.center);
            return l;
        }
    }
    
    public static interface DateSelectCallback{
        public void dateSelected(Date date);
    }
}
