/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import java.time.LocalDate;

public class DayButton extends Table {

    private LocalDate day;

    private final Label label;

    private final Drawable def = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, Color.CLEAR);
    private final Drawable hovered = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.grey3);
    private Drawable drawable;

    private final Label.LabelStyle hardStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey7);
    private final Label.LabelStyle softStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey5);
    
    private boolean checked;
    private ClickListener clickListener;

    public DayButton(DaysGrid parent) {
        super();

        label = new Label("", hardStyle);
        label.setAlignment(Align.center);

        super.setBackground(def);
        super.add(label).grow();
        super.setSize(getPrefWidth(), getPrefHeight());
        super.setTouchable(Touchable.enabled);

        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.setSelectedDate(day);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                parent.setHoveredDate(day);
            }
        };
        
        super.addListener(clickListener);
    }

    @Override
    public void act(float delta) {
        if (clickListener.isOver() && !checked) {
            setBackground(hovered);
        } else {
            setBackground(drawable);
        }
        super.act(delta);
    }

    public void check(Drawable drawable) {
        this.drawable = drawable;
        this.checked = true;
    }

    public void uncheck() {
        this.drawable = def;
        this.checked = false;
    }

    public void setStyle(boolean hard) {
        label.setStyle(hard? hardStyle:softStyle);
    }

    public void setDate(LocalDate day) {
        this.day = day;
        label.setText(day.getDayOfMonth());
    }

    public LocalDate getDay() {
        return day;
    }
}
