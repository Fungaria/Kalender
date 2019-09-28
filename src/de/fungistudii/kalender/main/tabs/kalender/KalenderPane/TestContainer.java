package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author sreis
 */
public class TestContainer extends Stack{
    
    private Table actor;
    
    public TestContainer(Table actor) {
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
    public void validate() {
        super.validate(); //To change body of generated methods, choose Tools | Templates.
        actor.validate();
    }
}