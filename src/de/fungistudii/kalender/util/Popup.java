package de.fungistudii.kalender.util;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.ObjectMap;
import de.fungistudii.kalender.Cons;

/**
 *
 * @author sreis
 */
public class Popup extends Table {

    boolean cancelHide;
    Actor previousKeyboardFocus, previousScrollFocus;
    FocusListener focusListener;

    private float prefWidth;
    private float prefHeight;
    
    private boolean centered = true;
    
    DialogManager manager;
    
    private boolean fadeBackground= true;
    
    protected InputListener ignoreTouchDown = new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            event.cancel();
            return false;
        }
    };

    public Popup() {
        initialize();
    }

    private void initialize() {
        focusListener = new FocusListener() {
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if (!focused) {
                    focusChanged(event);
                }
            }

            public void scrollFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if (!focused) {
                    focusChanged(event);
                }
            }

            private void focusChanged(FocusListener.FocusEvent event) {
//                Stage stage = getStage();
//                if (isModal() && stage != null && stage.getRoot().getChildren().size > 0
//                        && stage.getRoot().getChildren().peek() == Popup.this) { // Dialog is top most actor.
//                    Actor newFocusedActor = event.getRelatedActor();
//                    if (newFocusedActor != null && !newFocusedActor.isDescendantOf(Popup.this)
//                            && !(newFocusedActor.equals(previousKeyboardFocus) || newFocusedActor.equals(previousScrollFocus))) {
//                        event.cancel();
//                    }
//                }
            }
        };
    }

    protected void setStage(Stage stage) {
        if (stage == null) {
            addListener(focusListener);
        } else {
            removeListener(focusListener);
        }
        super.setStage(stage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Stage stage = getStage();
        if (stage.getKeyboardFocus() == null) {
            stage.setKeyboardFocus(this);
        }
        
        super.draw(batch, parentAlpha);
    }
    
    public boolean isFadeBackground() {
        return fadeBackground;
    }

    public void fadeBackground(boolean fade) {
        this.fadeBackground = fade;
    }

    @Override
    public float getPrefWidth() {
        return prefWidth>0?prefWidth:super.getPrefWidth();
    }

    @Override
    public float getPrefHeight() {
        return prefHeight>0?prefHeight:super.getPrefHeight();
    }

    /**
     * @param prefWidth the preferredWidth of the Popup or -1 if the prefWidth should be calculated based on its Children.
     */
    public void prefWidth(float prefWidth) {
        this.prefWidth = prefWidth;
    }
    
    /**
     * @param prefHeight the preferredHeight of the Popup or -1 if the prefHeight should be calculated based on its children.
     */
    public void prefHeight(float prefHeight) {
        this.prefHeight = prefHeight;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public boolean isCentered() {
        return centered;
    }
    
    public Popup show(Stage stage, Action action) {
        clearActions();
        removeCaptureListener(ignoreTouchDown);

        previousKeyboardFocus = null;
        Actor actor = stage.getKeyboardFocus();
        if (actor != null && !actor.isDescendantOf(this)) {
            previousKeyboardFocus = actor;
        }

        previousScrollFocus = null;
        actor = stage.getScrollFocus();
        if (actor != null && !actor.isDescendantOf(this)) {
            previousScrollFocus = actor;
        }

        pack();
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);
        if (action != null) {
            addAction(action);
        }

        return this;
    }

    /**
     * {@link #pack() Packs} the dialog and adds it to the stage, centered with
     * default fadeIn action
     */
    public Popup show(Stage stage) {
        show(stage, sequence(Actions.alpha(0), Actions.fadeIn(Cons.dialogFadeTime, Interpolation.fade)));
        setPosition(0, 0);
        return this;
    }

    /**
     * Hides the dialog with the given action and then removes it from the
     * stage.
     */
    public void hide(Action action) {
        Stage stage = getStage();
        if(manager != null)
            manager.removePopup(this);
        if (stage != null) {
            removeListener(focusListener);
            if (previousKeyboardFocus != null && previousKeyboardFocus.getStage() == null) {
                previousKeyboardFocus = null;
            }
            Actor actor = stage.getKeyboardFocus();
            if (actor == null || actor.isDescendantOf(this)) {
                stage.setKeyboardFocus(previousKeyboardFocus);
            }

            if (previousScrollFocus != null && previousScrollFocus.getStage() == null) {
                previousScrollFocus = null;
            }
            actor = stage.getScrollFocus();
            if (actor == null || actor.isDescendantOf(this)) {
                stage.setScrollFocus(previousScrollFocus);
            }
        }
        if (action != null) {
            addCaptureListener(ignoreTouchDown);
            addAction(sequence(action, Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
        } else {
            remove();
        }
    }

    /**
     * Hides the dialog. Called automatically when a button is clicked. The
     * default implementation fades out the dialog over 400 milliseconds and
     * then removes it from the stage.
     */
    public void hide() {
        hide(sequence(fadeOut(Cons.dialogFadeTime, Interpolation.fade), Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
    }

    /**
     * If this key is pressed, {@link #result(Object)} is called with the
     * specified object.
     *
     * @see Keys
     */
    public Popup key(final int keycode, final Object object) {
        addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode2) {
                if (keycode == keycode2) {
                    result(object);
                    if (!cancelHide) {
                        hide();
                    }
                    cancelHide = false;
                }
                return false;
            }
        });
        return this;
    }

    /**
     * Called when a button is clicked. The dialog will be hidden after this
     * method returns unless {@link #cancel()} is called.
     *
     * @param object The object specified when the button was added.
     */
    protected void result(Object object) {
    }

    public void cancel() {
        cancelHide = true;
    }
}
