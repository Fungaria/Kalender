package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Align;

/**
 *
 * @author sreis
 */
public class NinePatchIconDrawable extends BaseDrawable implements TransformDrawable{

    private Drawable icon;
    private NinePatchDrawable patch;

    private float iconPadding;
    private int align = Align.center;

    public NinePatchIconDrawable(Drawable icon, NinePatch patch) {
        this(icon, new NinePatchDrawable(patch));
    }

    public NinePatchIconDrawable(Drawable icon, NinePatchDrawable patch) {
        this(icon, patch, 0, Align.center);
    }

    public NinePatchIconDrawable(Drawable icon, NinePatchDrawable patch, float iconPaddig, int align) {
        this.patch = patch;
        this.icon = icon;
        this.align = align;
        this.iconPadding = iconPaddig;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public int getAlign() {
        return align;
    }

    public float getIconPadding() {
        return iconPadding;
    }

    public void setIconPadding(float iconPadding) {
        this.iconPadding = iconPadding;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        patch.draw(batch, x, y, width, height);
        height -= 2 * iconPadding;

        float iconWidth = icon.getMinWidth() * (height / icon.getMinHeight());
        float iconHeight = height;
        if ((align & Align.left) != 0) {
            x += iconPadding;
        } else if ((align & Align.right) != 0) {
            x += width - iconWidth - iconPadding;
        }else{
            x += (width - iconWidth)/2;
        }
        
        if ((align & Align.bottom) != 0) {
            y += iconPadding;
        } else if ((align & Align.top) != 0) {
            y += height - iconHeight - iconPadding;
        }else{
            y += (height - iconHeight)/2;
        }
        icon.draw(batch, x, y + (height - iconHeight) / 2 + iconPadding, iconWidth, iconHeight);
    }

    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        throw new UnsupportedOperationException("not supported yet");
    }
}
