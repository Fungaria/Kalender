/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.SearchField;

/**
 *
 * @author sreis
 */
public class GenericSearchField<T> extends SearchField<T>{

    public GenericSearchField(ListFilter<T> filter) {
        super(new Style(), filter);
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }
    
    private static final class Style extends SearchField.SFStyle {
        public Style() {
            super.textFieldStyle = new TextField.TextFieldStyle();
            super.textFieldStyle.background = ERE.assets.createRounded("outline");
            super.textFieldStyle.font = ERE.assets.fonts.createFont("roboto", 15);
            super.textFieldStyle.fontColor = Color.BLACK;
            super.textFieldStyle.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.textFieldStyle.cursor.setMinWidth(1);
            super.textFieldStyle.selection = ERE.assets.createDrawable("generic/textfield_selection");
            super.listStyle = new List.ListStyle(textFieldStyle.font, ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey3));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
            
            textFieldStyle.background.setLeftWidth(Cons.defaultLeftWidth);
        }
    }
}
