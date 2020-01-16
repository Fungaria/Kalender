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
    
    public static PercentValue percentValue(float percent, Value value){
        return new PercentValue(percent, value);
    }
    
    public static OffsetValue offsetValue(float offset, Value value){
        return new OffsetValue(offset, value);
    }
    
    public static OffsetValue offsetPercentValue(float offset, float percent, Value value){
        return new OffsetValue(offset, percentValue(percent, value));
    }
    
    public static class PercentValue extends Value{
        private float percent;
        private Value value;

        public PercentValue(float percent, Value value) {
            this.percent = percent;
            this.value = value;
        }

        @Override
        public float get(Actor context) {
            return value.get(context) * percent;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }
    }
    public static class OffsetValue extends Value{

        private Value value;
        private float offset;

        public OffsetValue( float offset, Value value) {
            this.value = value;
            this.offset = offset;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public float getOffset() {
            return offset;
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        @Override
        public float get(Actor context) {
            return value.get(context)+offset;
        }
        
    }
}
