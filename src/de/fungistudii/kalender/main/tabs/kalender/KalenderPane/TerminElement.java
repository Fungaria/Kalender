package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class TerminElement extends Stack{

    public TerminElement() {
        DrawableSolid solid = new DrawableSolid(Color.MAGENTA);
        add(new Image(solid));
    }
    
}
