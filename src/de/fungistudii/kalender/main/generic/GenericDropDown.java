package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
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
        this(ERE.assets.createNinePatchDrawable("generic/rounded", 6), ERE.assets.createNinePatchDrawable("generic/rounded_check",6), list);
    }
    
    public GenericDropDown(NinePatchDrawable bg, NinePatchDrawable bgOpen, T[] list) {
        this(ERE.assets.createDrawable("generic/arrow_down"), bg, bgOpen, list);
    }
    
    public GenericDropDown(Drawable icon, NinePatchDrawable bg, NinePatchDrawable bgOpen, T[] list) {
        super(new DDStyle(icon, bg, bgOpen));
        super.setItems(list);
        if(getStyle().background != null){
            super.getStyle().background.setLeftWidth(10);
            super.getStyle().backgroundOpen.setLeftWidth(10);
        }
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
                super.background = new NinePatchIconDrawable(ERE.assets.createDrawable("generic/arrow_down"), bg, 0.35f, 0.5f);
            }
            super.backgroundOpen = bgOpen;
            super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
            super.fontColor = Color.BLACK;
            super.listStyle = new List.ListStyle(font, ERE.assets.grey5, ERE.assets.grey4, new DrawableSolid(ERE.assets.grey2));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
        }
    }
}
