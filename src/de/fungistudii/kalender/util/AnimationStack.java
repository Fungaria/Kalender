package de.fungistudii.kalender.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 *
 * @author sreis
 */
public class AnimationStack extends Stack{
    
    private WidgetGroup actor;
    
    public boolean useWidth = false;
    
    public AnimationStack(WidgetGroup actor) {
        super(actor);
        this.actor = actor;
    }

    public void setMainActor(WidgetGroup actor){
        this.actor = actor;
        add(actor);
    }
    
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        actor.setSize(width, height);
    }

    @Override
    public float getWidth() {
        if(useWidth)
            return super.getWidth();
        return actor.getWidth();
    }

    @Override
    public float getPrefHeight() {
        return getHeight();
    }
    @Override
    public float getHeight() {
        if(useWidth)
            return super.getHeight();
        return actor.getHeight();
    }
    
    @Override
    public void validate() {
        super.validate();
        actor.validate();
    }
}