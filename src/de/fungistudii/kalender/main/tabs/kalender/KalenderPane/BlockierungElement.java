/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class BlockierungElement extends Button implements GridElement{

    private Label msg;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private int row;
    private int column;
    private int span;
    
    private static final Calendar calendar = Calendar.getInstance();
    
    private Image[] img;
    
    private final SpriteDrawable top = ERE.assets.createDrawable("kalender/grid/element_top_block");
    private final SpriteDrawable bottom = ERE.assets.createDrawable("kalender/grid/element_bottom_block");
    private final SpriteDrawable topOver = ERE.assets.createDrawable("kalender/grid/element_top_block_over");
    private final SpriteDrawable bottomOver = ERE.assets.createDrawable("kalender/grid/element_bottom_block_over");
    private final SpriteDrawable topCheck = ERE.assets.createDrawable("kalender/grid/element_top_block_check");
    private final SpriteDrawable bottomCheck = ERE.assets.createDrawable("kalender/grid/element_bottom_block_check");
    public BlockierungElement(Blockierung blockierung, Actor parent) {
        super(new ButtonStyle(null, null, null));

        calendar.setTime(blockierung.start);
        int minutes = calendar.get(Calendar.MINUTE)/15;
        
        img = new Image[blockierung.dauer];
        
        ImageButton.ImageButtonStyle topStyle = new ImageButton.ImageButtonStyle();
        topStyle.up = top;
        topStyle.over = topOver;
        topStyle.checked = topCheck;
        
        ImageButton.ImageButtonStyle bottomStyle = new ImageButton.ImageButtonStyle();
        bottomStyle.up = bottom;
        bottomStyle.over = bottomOver;
        bottomStyle.checked = bottomCheck;
        
        for (int i = 0; i < blockierung.dauer; i++) {
            ImageButton img = new ImageButton((i % 4)== 4-minutes ? topStyle : bottomStyle);
            super.add(img).grow();
            super.row();
        }
        
        addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                for (Actor actor : getChildren()) {
                    ((Button) actor).getClickListener().enter(event, x, y, -1, actor);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                for (Actor actor : getChildren()) {
                    ((Button) actor).getClickListener().exit(event, x, y, -1, actor);
                }
            }
        });
    }

    @Override
    public void act(float delta) {
        for (Actor actor : getChildren()) {
            ((Button) actor).setChecked(isChecked());
        }
    }
    
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public void setRow(int row){
        this.row = row;
    }
    
    @Override
    public int getRow() {
        return row;
    }
}