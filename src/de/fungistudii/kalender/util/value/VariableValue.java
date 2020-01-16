package de.fungistudii.kalender.util.value;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

/**
 *
 * @author sreis
 */
public class VariableValue extends Value{

    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public VariableValue(float value) {
        this.value = value;
    }

    public VariableValue() {
    }
    
    @Override
    public float get(Actor context) {
        return value;
    }
    
}
