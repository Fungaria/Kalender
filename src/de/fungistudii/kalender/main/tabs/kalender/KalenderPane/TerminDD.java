package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Disposable;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.TerminElement;

/**
 *
 * @author sreis
 */
public class TerminDD extends Group implements Disposable {

    public TerminDD() {

    }

    public TerminElement terminElement;

    private final Vector2 targetPosition = new Vector2();
    private final Vector2 tmpPos = new Vector2();

    public TerminDD(TerminElement termin, Value elementHeight) {
        super();
        
        terminElement = new TerminElement(termin.getTermin(), termin.getBackgroundColor());
        terminElement.setWidth(termin.getWidth());
        terminElement.setHeight(termin.getHeight());

        setTouchable(Touchable.disabled);
        
        super.addActor(terminElement);
    }

    public Termin getTermin() {
        return terminElement.getTermin();
    }

    public void jumpTo(float x, float y){
        terminElement.setPosition(x, y);
        targetPosition.set(x, y);
        tmpPos.set(x, y);
    }
    
    @Override
    public void act(float delta) {
        tmpPos.lerp(targetPosition, 0.5f);
        if(tmpPos.dst2(targetPosition)<10)
            tmpPos.set(targetPosition.x, targetPosition.y);
        terminElement.setPosition((int) tmpPos.x, (int) tmpPos.y);
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        this.targetPosition.set(x+Cons.appointmentPad, y);
    }
    
    public float getX(){
        return targetPosition.x;
    }
    
    public float getY(){
        return targetPosition.y;
    }

    @Override
    public void dispose() {
        terminElement.remove();
    }

    public float getHeight() {
        return terminElement.getHeight();
    }
}
