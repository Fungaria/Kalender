package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 *
 * @author sreis
 */
public class NinePatchIconDrawable extends NinePatchDrawable {

    private Drawable icon;
    
    private float iconScale = 1;
    private float iconPadding;

    public NinePatchIconDrawable(Drawable icon) {
        this.icon = icon;
    }

    public NinePatchIconDrawable(Drawable icon, NinePatch patch) {
        super(patch);
        this.icon = icon;
    }

    public NinePatchIconDrawable(Drawable icon, NinePatchDrawable drawable) {
        super(drawable);
        this.icon = icon;
    }
    
    public NinePatchIconDrawable(Drawable icon, NinePatchDrawable drawable, float iconScale, float iconPaddig) {
        super(drawable);
        this.icon = icon;
        this.iconScale = iconScale;
        this.iconPadding = iconPaddig;
    }

    public float getIconScale() {
        return iconScale;
    }

    public void setIconScale(float iconScale) {
        this.iconScale = iconScale;
    }

    public float getIconPadding() {
        return iconPadding;
    }

    public void setIconPadding(float iconPadding) {
        this.iconPadding = iconPadding;
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        super.draw(batch, x, y, width, height);
        float iconWidth = icon.getMinWidth()*(height/icon.getMinHeight())*iconScale;
        float iconHeight = height*iconScale;
        icon.draw(batch, x+width-iconWidth-iconPadding*iconWidth, y+(height-iconHeight)/2, iconWidth, iconHeight);
    }
//
//    @Override
//    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
//        super.draw(batch, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
//    }
}
