package de.fungistudii.kalender.util;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author sreis
 */
public class AnimationStack extends Stack{
    
    private Table actor;
    
    public AnimationStack(Table actor) {
        super(actor);
        this.actor = actor;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        actor.setSize(width, height);
    }

    @Override
    public float getWidth() {
        return actor.getWidth();
    }

    @Override
    public float getHeight() {
        return actor.getHeight();
    }
    
    @Override
    public void validate() {
        super.validate();
        actor.validate();
    }
}