package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
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
        this("generic/rounded", "generic/rounded_check", list);
    }
    
    public GenericDropDown(String bg, String bgOpen, T[] list) {
        this("generic/arrow_down", bg, bgOpen, list);
    }
    
    public GenericDropDown(String icon, String bg, String bgOpen, T[] list) {
        super(new DDStyle(icon, bg, bgOpen));
        super.setItems(list);
    }

    private static final class DDStyle extends SelectBoxStyle {
        public DDStyle(String icon, String bg, String bgOpen) {
            if(icon == null){
                super.background = ERE.assets.createNinePatchDrawable(bg, 10, 10, 10, 10);
            }else{
                super.background = new NinePatchIconDrawable(ERE.assets.createDrawable("generic/arrow_down"), ERE.assets.createNinePatchDrawable(bg, 10, 10, 10, 10), 0.35f, 0.5f);
            }
            super.backgroundOpen = ERE.assets.createNinePatchDrawable(bgOpen, 10, 10, 5, 10);
            super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
            super.fontColor = Color.BLACK;
            super.listStyle = new List.ListStyle(font, ERE.assets.grey5, ERE.assets.grey4, new DrawableSolid(ERE.assets.grey2));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
        }
    }
}
