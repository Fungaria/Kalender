package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 *
 * @author sreis
 */
public class NinePatchOffsetDrawable extends NinePatchDrawable{

    private float offsetX;
    private float offsetY;
    
    private boolean adjustSize;
    
    public NinePatchOffsetDrawable(NinePatch drawable) {
        super(drawable);
    }
    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setAdjustSize(boolean adjustSize) {
        this.adjustSize = adjustSize;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        super.draw(batch, x+offsetX, y+offsetY, adjustSize?width-offsetX:width, height);
    }    

    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        super.draw(batch, x+offsetX, y+offsetY, originX, originY, width, height, scaleX, scaleY, rotation); //To change body of generated methods, choose Tools | Templates.
    }
}
