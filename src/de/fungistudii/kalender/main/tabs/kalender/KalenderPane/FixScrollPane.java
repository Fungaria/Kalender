package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author sreis
 */
public class FixScrollPane extends ScrollPane {
    public FixScrollPane(Actor widget) {
        super(widget);
//        Array<EventListener> captureListeners = super.getCaptureListeners();
//        for (EventListener captureListener : captureListeners) {
//            super.removeListener(captureListener);
//        }
//        
//        Array<EventListener> listeners = super.getListeners();
//        for (EventListener listener : listeners) {
//            if(listener instanceof ActorGestureListener)
//                super.removeListener(listener);
//        }
    }
}
