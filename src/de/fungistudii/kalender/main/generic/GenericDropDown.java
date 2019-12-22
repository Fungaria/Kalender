package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchIconDrawable;

/**
 *
 * @author sreis
 */
public class GenericDropDown<T> extends SelectBox<T> {

    public GenericDropDown(T... list) {
        this(ERE.assets.createRounded("outline"), ERE.assets.createRounded("outline_check"), list);
    }
    
    public GenericDropDown(NinePatchDrawable bg, T[] list){
        this(bg, bg, list);
    }

    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }

    public GenericDropDown(NinePatchDrawable bg, NinePatchDrawable bgOpen, T[] list) {
        this(ERE.assets.createDrawable("icons/arrow_down"), bg, bgOpen, list);
    }

    public GenericDropDown(Drawable icon, NinePatchDrawable bg, NinePatchDrawable bgOpen, T[] list) {
        super(new DDStyle(icon, bg, bgOpen));
        super.setItems(list);
        super.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getStage().setKeyboardFocus(GenericDropDown.this);
                return false;
            }
        });
    }

    private static final class DDStyle extends SelectBoxStyle {
        public DDStyle(Drawable icon, NinePatchDrawable bg, NinePatchDrawable bgOpen) {
            if(icon == null){
                super.background = bg;
            }else{
                super.background = new NinePatchIconDrawable(ERE.assets.createDrawable("icons/arrow_down"), bg, 20, Align.right);
            }
            super.backgroundOpen = bgOpen;
            
            super.background.setLeftWidth(Cons.defaultLeftWidth);
            super.backgroundOpen.setLeftWidth(Cons.defaultLeftWidth);
            
            super.font = ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT);
            super.font.setUseIntegerPositions(false);
            super.fontColor = Color.BLACK;
            super.listStyle = new List.ListStyle(font, ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey3));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
            super.scrollStyle.background.setLeftWidth(Cons.defaultLeftWidth);
        }
    }
}
