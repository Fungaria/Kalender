package de.fungistudii.kalender.util.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class ColorContainer<T> extends Container{
    
    public ColorContainer(Actor actor, Color color) {
        this(actor, color, 0);
    }
    
    public ColorContainer(Actor actor, Color color, float pad) {
        super(actor);
        super.setBackground(new NinePatchSolid(color));
        super.fill();
        super.pad(pad);
    }
    
    public ColorContainer(Actor actor, Color color, float pad, int align) {
        super(actor);
        super.setBackground(new NinePatchSolid(color));
        super.pad(pad);
        super.fillX();
        super.align(align);
    }
    
}
