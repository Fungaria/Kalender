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
import static de.fungistudii.kalender.Main.ERE;
import java.time.LocalDate;

public class DayButton extends Table {

    private LocalDate day;
    private final Label label;

    private final Drawable def = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, Color.CLEAR);
    private Drawable checkedDrawable;
    private Drawable hoveredDrawable;
    private boolean checked;
    private boolean hovered;

    private final Label.LabelStyle currentMonthStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey7);
    private final Label.LabelStyle outerMonthStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey5);
    
    private final ClickListener clickListener;

    public DayButton(DaysGrid parent) {
        super();

        label = new Label("", currentMonthStyle);

        super.setBackground(def);
        super.add(label).expand();
        super.setSize(getPrefWidth(), getPrefHeight());
        super.setTouchable(Touchable.enabled);

        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parent.selectDate(day);
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
        if (checked) {
            setBackground(checkedDrawable);
        } else if(hovered){
            setBackground(hoveredDrawable);
        }else{
            setBackground(def);
        }
        super.act(delta);
    }

    public void check(Drawable drawable) {
        this.checkedDrawable = drawable;
        this.checked = true;
    }
    
    public void hover(Drawable drawable){
        this.hoveredDrawable = drawable;
        this.hovered = true;
    }
    
    public void uncheck() {
        this.checked = false;
    }

    public void setStyle(boolean hard) {
        label.setStyle(hard? currentMonthStyle:outerMonthStyle);
    }

    public void setDate(LocalDate day) {
        this.day = day;
        label.setText(day.getDayOfMonth());
    }

    public LocalDate getDay() {
        return day;
    }

    void unhover() {
        hovered = false;
    }
}
