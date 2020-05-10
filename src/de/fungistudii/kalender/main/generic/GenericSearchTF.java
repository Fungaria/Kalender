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
public class GenericSearchTF extends TextField{
    
    private final Drawable icon;
    
    public GenericSearchTF() {
        super("", new Style());
        this.icon = ERE.assets.createDrawable("icons/search", ERE.assets.grey3);
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }

    @Override
    protected void drawMessageText(Batch batch, BitmapFont font, float x, float y, float maxWidth) {
        float iconSize = getHeight()-(2*GenericSearchField.iconPadTop);
        this.icon.draw(batch, getX()+GenericSearchField.iconPadSide, getY()+GenericSearchField.iconPadTop, iconSize, iconSize);
        super.drawMessageText(batch, font, getX()+iconSize+GenericSearchField.iconPadSide*2, y, maxWidth);
    }
    
    private static final class Style extends TextFieldStyle {
        public Style() {
            super.background = ERE.assets.createRounded("outline");
            super.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
            super.fontColor = Color.BLACK;
            super.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.cursor.setMinWidth(1);
            super.selection = new NinePatchSolid(ERE.assets.grey2);
        }
    }
}
