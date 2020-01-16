package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import java.util.Date;

/**
 *
 * @author sreis
 */
public abstract class GridElement extends Button implements Disposable{

    public GridElement() {
    }

    public GridElement(ButtonStyle style) {
        super(style);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!isDisabled())
                    setChecked(true);
            }
        });
    }

    @Override
    public boolean isOver() {
        if(!isDisabled())
            return super.isOver(); //To change body of generated methods, choose Tools | Templates.
        else
            return false;
    }
    
    public abstract Date getStart();
    public abstract int getFriseur();
    public abstract int getSpan();
    public void dispose(){
        remove();
    }
}
