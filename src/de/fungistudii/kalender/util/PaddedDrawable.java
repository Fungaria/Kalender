package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 *
 * @author sreis
 */
public class PaddedDrawable extends BaseDrawable{

    private Drawable kid;

    public PaddedDrawable(Drawable kid) {
        this.kid = kid;
    }

    public Drawable getChild() {
        return kid;
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        kid.draw(batch, x+super.getLeftWidth(), y+super.getBottomHeight(), width-super.getLeftWidth()-super.getRightWidth(), height-super.getTopHeight()-super.getBottomHeight());
    }
    
    @Override
    public float getLeftWidth() {
        return super.getLeftWidth()+kid.getLeftWidth();
    }
    
    @Override
    public float getRightWidth() {
        return super.getRightWidth()+kid.getRightWidth();
    }

    @Override
    public float getTopHeight() {
        return super.getTopHeight()+kid.getTopHeight();
    }
    
    @Override
    public float getBottomHeight() {
        return super.getBottomHeight()+kid.getBottomHeight();
    }
}
