package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

/**
 * mimics the movement of a scrollpane, either in vertical or horizontal
 * direction. Boi
 *
 * @author sreis
 */
public class ScrollPaneFollower extends WidgetGroup {

    private Actor widget;

    private final Rectangle widgetAreaBounds = new Rectangle();
    private final Rectangle widgetCullingArea = new Rectangle();

    boolean scrollX, scrollY;
    float visualAmountX, visualAmountY;
    float maxX, maxY;
    float areaWidth, areaHeight;

    boolean disableX, disableY;

    private ScrollPane prt;

    /**
     * @param widget May be null.
     */
    public ScrollPaneFollower(Actor widget) {
        setActor(widget);
        setSize(150, 150);
    }

    public void act(float delta) {
        super.act(delta);

        if (prt != null) {
            if (!disableY) {
                visualScrollY(prt.getVisualScrollY());
            }
            if (!disableX) {
                visualScrollX(prt.getVisualScrollX());
            }
        }
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return null;
        }
        return super.hit(x, y, touchable);
    }

    public void layout() {
        float bgLeftWidth = 0, bgRightWidth = 0, bgTopHeight = 0, bgBottomHeight = 0;

        float width = getWidth();
        float height = getHeight();

        // Get available space size by subtracting background's padded area.
        areaWidth = width - bgLeftWidth - bgRightWidth;
        areaHeight = height - bgTopHeight - bgBottomHeight;

        if (widget == null) {
            return;
        }

        // Get widget's desired width.
        float widgetWidth, widgetHeight;
        if (widget instanceof Layout) {
            Layout layout = (Layout) widget;
            widgetWidth = layout.getPrefWidth();
            widgetHeight = layout.getPrefHeight();
        } else {
            widgetWidth = widget.getWidth();
            widgetHeight = widget.getHeight();
        }

        // Determine if horizontal/vertical scrollbars are needed.
        scrollX = (widgetWidth > areaWidth && !disableX);
        scrollY = (widgetHeight > areaHeight && !disableY);

        // The bounds of the scrollable area for the widget.
        widgetAreaBounds.set(bgLeftWidth, bgBottomHeight, areaWidth, areaHeight);

        // If the widget is smaller than the available space, make it take up the available space.
        widgetWidth = disableX ? areaWidth : Math.max(areaWidth, widgetWidth);
        widgetHeight = disableY ? areaHeight : Math.max(areaHeight, widgetHeight);

        maxX = widgetWidth - areaWidth;
        maxY = widgetHeight - areaHeight;

        updateWidgetPosition();
        if (widget instanceof Layout) {
            widget.setSize(widgetWidth, widgetHeight);
            ((Layout) widget).validate();
        }
    }

    private void updateWidgetPosition() {
        // Calculate the widget's position depending on the scroll state and available widget area.
        float y = widgetAreaBounds.y;
        if (!scrollY) {
            y -= (int) maxY;
        } else {
            y -= (int) (maxY - visualAmountY);
        }

        float x = widgetAreaBounds.x;
        if (scrollX) {
            x -= (int) visualAmountX;
        }

        widget.setPosition(x, y);

        if (widget instanceof Cullable) {
            widgetCullingArea.x = widgetAreaBounds.x - x;
            widgetCullingArea.y = widgetAreaBounds.y - y;
            widgetCullingArea.width = widgetAreaBounds.width;
            widgetCullingArea.height = widgetAreaBounds.height;
            ((Cullable) widget).setCullingArea(widgetCullingArea);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (widget == null) {
            return;
        }

        validate();

        // Setup transform for this group.
        applyTransform(batch, computeTransform());

        updateWidgetPosition();

        // Draw the background ninepatch.
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.flush();
        if (clipBegin(widgetAreaBounds.x, widgetAreaBounds.y, widgetAreaBounds.width, widgetAreaBounds.height)) {
            drawChildren(batch, parentAlpha);
            batch.flush();
            clipEnd();
        }

        // Render scrollbars and knobs on top if they will be visible
        resetTransform(batch);
    }

    public void setParent(ScrollPane parent) {
        this.prt = parent;
    }

    public float getPrefWidth() {
        float width = 0;
        if (widget instanceof Layout) {
            validate();
            width = ((Layout) widget).getPrefWidth();
        } else if (widget != null) //
        {
            width = widget.getWidth();
        }
        return width;
    }

    public float getPrefHeight() {
        float height = 0;
        if (widget instanceof Layout) {
            validate();
            height = ((Layout) widget).getPrefHeight();
        } else if (widget != null) //
        {
            height = widget.getHeight();
        }
        return height;
    }

    public float getMinWidth() {
        return 0;
    }

    public float getMinHeight() {
        return 0;
    }

    /**
     * Sets the {@link Actor} embedded in this scroll pane.
     *
     * @param actor May be null to remove any current actor.
     */
    public void setActor(Actor actor) {
        if (widget == this) {
            throw new IllegalArgumentException("widget cannot be the ScrollPane.");
        }
        if (this.widget != null) {
            super.removeActor(this.widget);
        }
        this.widget = actor;
        if (widget != null) {
            super.addActor(widget);
        }
    }

    /**
     * Returns the actor embedded in this scroll pane, or null.
     */
    public Actor getActor() {
        return widget;
    }

    public boolean removeActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor != widget) {
            return false;
        }
        setActor(null);
        return true;
    }

    public boolean removeActor(Actor actor, boolean unfocus) {
        if (actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }
        if (actor != widget) {
            return false;
        }
        this.widget = null;
        return super.removeActor(actor, unfocus);
    }

    /**
     * Called whenever the visual x scroll amount is changed.
     */
    protected void visualScrollX(float pixelsX) {
        this.visualAmountX = pixelsX;
    }

    /**
     * Called whenever the visual y scroll amount is changed.
     */
    protected void visualScrollY(float pixelsY) {
        this.visualAmountY = pixelsY;
    }

    public float getVisualScrollX() {
        return !scrollX ? 0 : visualAmountX;
    }

    public float getVisualScrollY() {
        return !scrollY ? 0 : visualAmountY;
    }

    public float getVisualScrollPercentX() {
        if (maxX == 0) {
            return 0;
        }
        return MathUtils.clamp(visualAmountX / maxX, 0, 1);
    }

    public float getVisualScrollPercentY() {
        if (maxY == 0) {
            return 0;
        }
        return MathUtils.clamp(visualAmountY / maxY, 0, 1);
    }

    /**
     * Returns the maximum scroll value in the x direction.
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * Returns the maximum scroll value in the y direction.
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     * Returns the width of the scrolled viewport.
     */
    public float getScrollWidth() {
        return areaWidth;
    }

    /**
     * Returns the height of the scrolled viewport.
     */
    public float getScrollHeight() {
        return areaHeight;
    }

    /**
     * Returns true if the widget is larger than the scroll pane horizontally.
     */
    public boolean isScrollX() {
        return scrollX;
    }

    /**
     * Returns true if the widget is larger than the scroll pane vertically.
     */
    public boolean isScrollY() {
        return scrollY;
    }

    /**
     * Disables scrolling in a direction. The widget will be sized to the
     * FlickScrollPane in the disabled direction.
     */
    public void setScrollingDisabled(boolean x, boolean y) {
        disableX = x;
        disableY = y;
        invalidate();
    }

    public boolean isScrollingDisabledX() {
        return disableX;
    }

    public boolean isScrollingDisabledY() {
        return disableY;
    }

    public void drawDebug(ShapeRenderer shapes) {
        drawDebugBounds(shapes);
        applyTransform(shapes, computeTransform());
        if (clipBegin(widgetAreaBounds.x, widgetAreaBounds.y, widgetAreaBounds.width, widgetAreaBounds.height)) {
            drawDebugChildren(shapes);
            shapes.flush();
            clipEnd();
        }
        resetTransform(shapes);
    }
}
