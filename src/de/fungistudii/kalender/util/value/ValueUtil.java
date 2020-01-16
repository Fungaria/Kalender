package de.fungistudii.kalender.util.value;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

/**
 *
 * @author sreis
 */
public abstract class ValueUtil{

    public static Value percentMaxHeight(float percent, Actor actor, float maxValue){
        return new Value() {
            @Override
            public float get(Actor context) {
                return Math.max(maxValue, actor.getHeight()*percent);
            }
        };
    }
    
    public static Value percentMinHeight(float percent, Actor actor, float minValue){
        return new Value() {
            @Override
            public float get(Actor context) {
                return Math.min(minValue, actor.getHeight()*percent);
            }
        };
    }
    
    public static Value percentMinWidth(float percent, Actor actor, float minValue){
        return new Value() {
            @Override
            public float get(Actor context) {
                return Math.min(minValue, actor.getWidth()*percent);
            }
        };
    }
    
    public static Value percentMaxWidth(float percent, Actor actor, float maxValue){
        return new Value() {
            @Override
            public float get(Actor context) {
                return Math.max(maxValue, actor.getWidth()*percent);
            }
        };
    }
    
    public static Value percentValue(float percent, Value value){
        return new Value() {
            @Override
            public float get(Actor context) {
                return value.get(context)*percent;
            }
        };
    }
    
}
