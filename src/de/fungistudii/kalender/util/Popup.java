package de.fungistudii.kalender.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.ObjectMap;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class Popup extends Container {

    protected Container popupContainer;
    protected Table contentTable;
    ObjectMap<Actor, Object> values = new ObjectMap();
    boolean cancelHide;
    Actor previousKeyboardFocus, previousScrollFocus;
    FocusListener focusListener;

    private Drawable stageBackground;

    private final Vector2 tmpPosition = new Vector2();
    private final Vector2 tmpSize = new Vector2();

    private Window w;
    
    protected Label titleLabel;
    protected ImageButton closeButton;

    protected InputListener ignoreTouchDown = new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            event.cancel();
            return false;
        }
    };

    public Popup(String title) {
        popupContainer = new Container();
        contentTable = new Table();
        closeButton = new ImageButton(ERE.assets.createDrawable("generic/cross", ERE.assets.grey4));
        closeButton.getStyle().imageOver = ERE.assets.createDrawable("generic/cross", ERE.assets.grey6);
        titleLabel = new Label(title, new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), Color.BLACK));
        initialize();
    }

    public void setTitle(String title){
        this.titleLabel.setText(title);
    }
    
    public String getTitle(){
        return this.titleLabel.getName();
    }
    
    private void initialize() {
        super.setFillParent(true);
        super.setActor(popupContainer);
//        popupContainer.prefWidth(Value.percentWidth(0.45f, this));

        popupContainer.setActor(contentTable);
        contentTable.defaults().space(6);
        contentTable.pad(20);
        Table group = new Table();
        
        
        group.add(titleLabel).grow().padLeft(Value.percentHeight(1, titleLabel));
        group.add(closeButton).size(Value.percentHeight(1, titleLabel));
        contentTable.add(group).growX();
        contentTable.row();
        contentTable.add(new Image(ERE.assets.createDrawable("generic/separator"))).grow().colspan(3).height(1);
        contentTable.row();

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        
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

        if (stageBackground != null) {
            stageToLocalCoordinates(tmpPosition.set(0, 0));
            stageToLocalCoordinates(tmpSize.set(stage.getWidth(), stage.getHeight()));
            drawStageBackground(batch, parentAlpha, getX() + tmpPosition.x, getY() + tmpPosition.y, getX() + tmpSize.x,
                    getY() + tmpSize.y);
        }

        super.draw(batch, parentAlpha);
    }

    protected void drawStageBackground(Batch batch, float parentAlpha, float x, float y, float width, float height) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        stageBackground.draw(batch, x, y, width, height);
    }

    public Drawable getStageBackground() {
        return stageBackground;
    }

    public void setStageBackground(Drawable stageBackground) {
        this.stageBackground = stageBackground;
    }

    public Popup show(Stage stage, Action action) {
        clearActions();
        removeCaptureListener(ignoreTouchDown);

        ERE.mainScreen.root.setTouchable(Touchable.disabled);

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
        stage.addActor(this);
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
        show(stage, sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
        setPosition(0, 0);
        return this;
    }

    /**
     * Hides the dialog with the given action and then removes it from the
     * stage.
     */
    public void hide(Action action) {
        Stage stage = getStage();
        ERE.mainScreen.root.setTouchable(Touchable.enabled);
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
        hide(sequence(fadeOut(0.4f, Interpolation.fade), Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
    }

    public void setObject(Actor actor, Object object) {
        values.put(actor, object);
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
