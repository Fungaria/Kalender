package de.fungistudii.kalender.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author sreis
 */
public class SearchField<T> extends TextField implements Disableable {

    private SFStyle style;
    static final Vector2 temp = new Vector2();
    private final ArrayList<T> itemsAll = new ArrayList();
    final ArrayList<T> items = new ArrayList();
    
    private SelectBoxList list;
    
    private ListFilter<T> filter;

    public SearchField(SFStyle style, ListFilter<T> filter) {
        super("", style.textFieldStyle);
        this.filter = filter;
        this.style = style;
        
        this.list = new SelectBoxList(this);
        
        super.setTextFieldListener(new TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if(getText().length() == 0){
                    list.hide();
                }else{
                    updateItems();
                    if(!list.open && items.size() >0)
                        list.show(getStage());
                    list.setItems(items.toArray());
                }
            }
        });
    }
    
    public void setItems(ArrayList<T> items){
        this.itemsAll.clear();
        this.itemsAll.addAll(items);
    }
    
    private void updateItems(){
        T[] toArray = (T[])itemsAll.stream().filter((a) -> filter.test(getText(), a)).toArray();
        items.clear();
        for (T t : toArray) {
            items.add(t);
        }
    }

    protected void onShow(Actor selectBoxList, boolean below) {
        selectBoxList.getColor().a = 0;
        selectBoxList.addAction(fadeIn(0.3f, Interpolation.fade));
    }

    protected void onHide(Actor selectBoxList) {
        selectBoxList.getColor().a = 1;
        selectBoxList.addAction(sequence(fadeOut(0.15f, Interpolation.fade), removeActor()));
    }

    public int getSelectedIndex() {
        return 0;
    }
    
    static class SelectBoxList<T> extends ScrollPane {
        private final SearchField selectBox;
        int maxListCount;
        private final Vector2 screenPosition = new Vector2();
        final List<T> list;
        private InputListener hideListener;
        private Actor previousScrollFocus;
        
        public boolean open;

        public SelectBoxList(final SearchField selectBox) {
            super(null, selectBox.style.scrollStyle);
            this.selectBox = selectBox;

            setOverscroll(false, false);
            setFadeScrollBars(false);
            setScrollingDisabled(true, false);

            list = new List<T>(selectBox.style.listStyle) {
                @Override
                public String toString(T obj) {
                    return obj.toString();
                }
            };
            list.setTouchable(Touchable.disabled);
            list.setTypeToSelect(true);
            setActor(list);

            list.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                }

                public boolean mouseMoved(InputEvent event, float x, float y) {
                    int index = list.getItemIndexAt(y);
                    if (index != -1) {
                        list.setSelectedIndex(index);
                    }
                    return true;
                }
            });

            addListener(new InputListener() {
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (toActor == null || !isAscendantOf(toActor)) {
                        list.setSelectedIndex(-1);
                    }
                }
            });

            hideListener = new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Actor target = event.getTarget();
                    System.out.println(list.getSelected());
                    if (isAscendantOf(target)) {
                        return false;
                    }
                    hide();
                    return false;
                }

                public boolean keyDown(InputEvent event, int keycode) {
                    switch (keycode) {
                        case Input.Keys.ESCAPE:
                            hide();
                            event.stop();
                            return true;
                    }
                    return false;
                }
            };
        }

        public void show(Stage stage) {
            open = true;
            if (list.isTouchable()) {
                return;
            }

            stage.addActor(this);
            stage.addCaptureListener(hideListener);
            stage.addListener(list.getKeyListener());

            selectBox.localToStageCoordinates(screenPosition.set(0, 0));

            // Show the list above or below the select box, limited to a number of items and the available height in the stage.
            float itemHeight = list.getItemHeight();
            float height = itemHeight * (maxListCount <= 0 ? selectBox.items.size() : Math.min(maxListCount, selectBox.items.size()));
            Drawable scrollPaneBackground = getStyle().background;
            if (scrollPaneBackground != null) {
                height += scrollPaneBackground.getTopHeight() + scrollPaneBackground.getBottomHeight();
            }
            Drawable listBackground = list.getStyle().background;
            if (listBackground != null) {
                height += listBackground.getTopHeight() + listBackground.getBottomHeight();
            }

            float heightBelow = screenPosition.y;
            float heightAbove = stage.getCamera().viewportHeight - screenPosition.y - selectBox.getHeight();
            boolean below = true;
            if (height > heightBelow) {
                if (heightAbove > heightBelow) {
                    below = false;
                    height = Math.min(height, heightAbove);
                } else {
                    height = heightBelow;
                }
            }

            if (below) {
                setY(screenPosition.y - height);
            } else {
                setY(screenPosition.y + selectBox.getHeight());
            }
            setX(screenPosition.x);
            setHeight(height);
            validate();
            float width = Math.max(getPrefWidth(), selectBox.getWidth());
            if (getPrefHeight() > height && !super.isScrollingDisabledY()) {
                width += getScrollBarWidth();
            }
            setWidth(width);

            validate();
            scrollTo(0, list.getHeight() - selectBox.getSelectedIndex() * itemHeight - itemHeight / 2, 0, 0, true, true);
            updateVisualScroll();

            previousScrollFocus = null;
            Actor actor = stage.getScrollFocus();
            if (actor != null && !actor.isDescendantOf(this)) {
                previousScrollFocus = actor;
            }
            stage.setScrollFocus(this);

            list.setSelectedIndex(-1);
            list.setTouchable(Touchable.enabled);
            clearActions();
            selectBox.onShow(this, below);
        }

        private void updateHeight(Stage stage){
            float itemHeight = list.getItemHeight();
            float height = itemHeight * (maxListCount <= 0 ? selectBox.items.size() : Math.min(maxListCount, selectBox.items.size()));
            Drawable scrollPaneBackground = getStyle().background;
            if (scrollPaneBackground != null) {
                height += scrollPaneBackground.getTopHeight() + scrollPaneBackground.getBottomHeight();
            }
            Drawable listBackground = list.getStyle().background;
            if (listBackground != null) {
                height += listBackground.getTopHeight() + listBackground.getBottomHeight();
            }

            float heightBelow = screenPosition.y;
            float heightAbove = stage.getCamera().viewportHeight - screenPosition.y - selectBox.getHeight();
            boolean below = true;
            if (height > heightBelow) {
                if (heightAbove > heightBelow) {
                    below = false;
                    height = Math.min(height, heightAbove);
                } else {
                    height = heightBelow;
                }
            }

            if (below) {
                setY(screenPosition.y - height);
            } else {
                setY(screenPosition.y + selectBox.getHeight());
            }
            setX(screenPosition.x);
            setHeight(height);
        }
        
        public void setItems(T[] items){
            if(items.length == 0){
                hide();
            }else{
                list.setItems(items);
                updateHeight(getStage());
            }
        }
        
        public void hide() {
            open = false;
            if (!list.isTouchable() || !hasParent()) {
                return;
            }
            list.setTouchable(Touchable.disabled);

            Stage stage = getStage();
            if (stage != null) {
                stage.removeCaptureListener(hideListener);
                stage.removeListener(list.getKeyListener());
                if (previousScrollFocus != null && previousScrollFocus.getStage() == null) {
                    previousScrollFocus = null;
                }
                Actor actor = stage.getScrollFocus();
                if (actor == null || isAscendantOf(actor)) {
                    stage.setScrollFocus(previousScrollFocus);
                }
            }

            clearActions();
            selectBox.onHide(this);
        }

        public void draw(Batch batch, float parentAlpha) {
            selectBox.localToStageCoordinates(temp.set(0, 0));
            if (!temp.equals(screenPosition)) {
                hide();
            }
            super.draw(batch, parentAlpha);
        }

        public void act(float delta) {
            super.act(delta);
            toFront();
        }

        protected void setStage(Stage stage) {
            Stage oldStage = getStage();
            if (oldStage != null) {
                oldStage.removeCaptureListener(hideListener);
                oldStage.removeListener(list.getKeyListener());
            }
            super.setStage(stage);
        }
    }

    static public class SFStyle {

        public TextField.TextFieldStyle textFieldStyle;
        public ScrollPaneStyle scrollStyle;
        public ListStyle listStyle;

        public SFStyle() {
        }
    }
    
    @FunctionalInterface
    static public interface ListFilter<T>{
        public boolean test(String input, T test);
    }
}
