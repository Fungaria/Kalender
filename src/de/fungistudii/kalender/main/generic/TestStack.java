/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 *
 * @author sreis
 */
public class TestStack extends WidgetGroup {

    private float prefWidth, prefHeight, minWidth, minHeight, maxWidth, maxHeight;
    private boolean sizeInvalid = true;

    public TestStack() {
        setTransform(false);
        setWidth(150);
        setHeight(150);
        setTouchable(Touchable.childrenOnly);
    }

    public TestStack(Actor... actors) {
        this();
        for (Actor actor : actors) {
            addActor(actor);
        }
    }

    private boolean layoutEnabled = true;
    
    @Override
    public void setLayoutEnabled(boolean enabled) {
        this.layoutEnabled = enabled;
    }
    
    public void invalidate() {
        if(layoutEnabled)
            super.invalidate();
        sizeInvalid = true;
    }

    private void computeSize() {
        sizeInvalid = false;
        prefWidth = 0;
        prefHeight = 0;
        minWidth = 0;
        minHeight = 0;
        maxWidth = 0;
        maxHeight = 0;
        Actor child = super.getChild(0);
        float childMaxWidth, childMaxHeight;
        if (child instanceof Layout) {
            Layout layout = (Layout) child;
            prefWidth = Math.max(prefWidth, layout.getPrefWidth());
            prefHeight = Math.max(prefHeight, layout.getPrefHeight());
            minWidth = Math.max(minWidth, layout.getMinWidth());
            minHeight = Math.max(minHeight, layout.getMinHeight());
            childMaxWidth = layout.getMaxWidth();
            childMaxHeight = layout.getMaxHeight();
        } else {
            prefWidth = Math.max(prefWidth, child.getWidth());
            prefHeight = Math.max(prefHeight, child.getHeight());
            minWidth = Math.max(minWidth, child.getWidth());
            minHeight = Math.max(minHeight, child.getHeight());
            childMaxWidth = 0;
            childMaxHeight = 0;
        }
        if (childMaxWidth > 0) {
            maxWidth = maxWidth == 0 ? childMaxWidth : Math.min(maxWidth, childMaxWidth);
        }
        if (childMaxHeight > 0) {
            maxHeight = maxHeight == 0 ? childMaxHeight : Math.min(maxHeight, childMaxHeight);
        }
    }

    public void add(Actor actor) {
        addActor(actor);
    }

    public void layout() {
        if (sizeInvalid) {
            computeSize();
        }
        float width = getWidth(), height = getHeight();
        Array<Actor> children = getChildren();
        for (int i = 0, n = children.size; i < n; i++) {
            Actor child = children.get(i);
            child.setBounds(0, 0, width, height);
            if (child instanceof Layout) {
                ((Layout) child).validate();
            }
        }
    }

    public float getPrefWidth() {
        if (sizeInvalid) {
            computeSize();
        }
        return prefWidth;
    }

    public float getPrefHeight() {
        if (sizeInvalid) {
            computeSize();
        }
        return prefHeight;
    }

    public float getMinWidth() {
        if (sizeInvalid) {
            computeSize();
        }
        return minWidth;
    }

    public float getMinHeight() {
        if (sizeInvalid) {
            computeSize();
        }
        return minHeight;
    }

    public float getMaxWidth() {
        if (sizeInvalid) {
            computeSize();
        }
        return maxWidth;
    }

    public float getMaxHeight() {
        if (sizeInvalid) {
            computeSize();
        }
        return maxHeight;
    }
}
