/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.SearchField;

/**
 *
 * @author sreis
 */
public class GenericSearchField<T> extends SearchField<T>{

    private final Drawable icon;
    static final int iconPadSide = 8;
    static final int iconPadTop= 12;
    private boolean drawIcon = true;
    
    public GenericSearchField(ListFilter<T> filter) {
        super(new Style(), filter);
        this.icon = ERE.assets.createDrawable("icons/search", ERE.assets.grey3);
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }

    public void setDrawIcon(boolean drawIcon) {
        this.drawIcon = drawIcon;
    }

    public boolean isDrawIcon() {
        return drawIcon;
    }
    
    @Override
    protected void drawMessageText(Batch batch, BitmapFont font, float x, float y, float maxWidth) {
        if(drawIcon){
            float iconSize = getHeight()-(2*iconPadTop);
            this.icon.draw(batch, getX()+iconPadSide, getY()+iconPadTop, iconSize, iconSize);
            super.drawMessageText(batch, font, getX()+iconSize+iconPadSide*2, y, maxWidth);
        }else{
            super.drawMessageText(batch, font, x, y, maxWidth);
        }
    }
    
    private static final class Style extends SearchField.SFStyle {
        public Style() {
            super.textFieldStyle = new TextField.TextFieldStyle();
            super.textFieldStyle.background = ERE.assets.createRounded("outline");
            super.textFieldStyle.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
            super.textFieldStyle.fontColor = Color.BLACK;
            super.textFieldStyle.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.textFieldStyle.cursor.setMinWidth(1);
            super.textFieldStyle.selection = new NinePatchSolid(ERE.assets.grey2);
            super.listStyle = new List.ListStyle(textFieldStyle.font, ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey1));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 10);
            
            super.textFieldStyle.background.setLeftWidth(Cons.defaultLeftWidth);
//            super.textFieldStyle.disabledBackground.setLeftWidth(Cons.defaultLeftWidth);
//            super.textFieldStyle.focusedBackground.setLeftWidth(Cons.defaultLeftWidth);
        }
    }
}
