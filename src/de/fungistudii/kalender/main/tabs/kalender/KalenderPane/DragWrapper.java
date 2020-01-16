package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author sreis
 */
public class DragWrapper extends Group implements Disposable {


    public GridElement element;

    private final Vector2 targetPosition = new Vector2();
    private final Vector2 tmpPos = new Vector2();

    public DragWrapper(GridElement element) {
        super();
        this.element = element.copy();
        this.element.setFocused(true);
        super.addActor(this.element);
        setTouchable(Touchable.disabled);
    }
    
    public void jumpTo(float x, float y){
        element.setPosition(x, y);
        targetPosition.set(x, y);
        tmpPos.set(x, y);
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
        if(element == null)
            return;
        tmpPos.lerp(targetPosition, 0.5f);
        if(tmpPos.dst2(targetPosition)<10)
            tmpPos.set(targetPosition.x, targetPosition.y);
        element.setPosition((int) tmpPos.x, (int) tmpPos.y);
    }

    @Override
    public void setPosition(float x, float y) {
        this.targetPosition.set(x, y);
    }
    
    @Override
    public float getX(){
        return targetPosition.x;
    }
    
    @Override
    public float getY(){
        return targetPosition.y;
    }

    @Override
    public void dispose() {
        element.remove();
        remove();
    }

    @Override
    public float getHeight() {
        return element.getHeight();
    }
    
    public GridElement getGridElement(){
        return element;
    }
}
