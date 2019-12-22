package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 *
 * @author sreis
 */
public class FitImage extends Image{

    public FitImage(Drawable drawable) {
        super(drawable);
    }
    
    float height;

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        if(this.height != height){
            this.height = height;
            invalidateHierarchy();
        }
    }

    @Override
    public float getPrefWidth() {
        return height;
    }

    @Override
    public float getMinWidth() {
        return height;
    }
}
