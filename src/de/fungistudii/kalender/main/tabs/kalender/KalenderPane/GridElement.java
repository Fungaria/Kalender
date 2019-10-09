package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author sreis
 */
public abstract class GridElement extends Button{

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

    public abstract int getRow();
    public abstract int getColumn();
    public abstract int getSpan();
}
