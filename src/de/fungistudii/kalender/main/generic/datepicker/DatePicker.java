/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.datepicker.DaysGrid.SelectBehavior;
import de.fungistudii.kalender.main.generic.TestStack;
import de.fungistudii.kalender.util.Fonts;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 *
 * @author sreis
 */
public class DatePicker extends Table {

    private DaysGrid datesOld;
    private DaysGrid datesNext;
    private MonthHeader monthHeader;
    private WeekLabel weekLabel;

    private TestStack stack;

    private YearMonth currentMonth;
    
    private DateSelectCallback callback;
    private final DateSelectCallback monthChanger = new DateSelectCallback() {
        @Override
        public void dateSelected(LocalDate date, int direction) {
            transitionIfMonthChanged(date);
        }
    };
    

    public DatePicker(DateSelectCallback callback) {
        currentMonth = YearMonth.now();
        
        datesOld = new DaysGrid(callback, monthChanger);
        datesOld.setMonth(currentMonth);
        datesNext = new DaysGrid(callback, monthChanger);
        datesNext.setMonth(currentMonth);
        weekLabel = new WeekLabel();
        monthHeader = new MonthHeader();

        this.callback = callback;

        stack = new TestStack();
        stack.add(datesOld);
        stack.addActor(datesNext);
        datesNext.setVisible(false);

        Drawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        super.add(monthHeader).growX().prefHeight(Value.percentWidth(0.8f / 8f, this));
        super.row();
        super.add(new Image(separator)).growX().height(1).padTop(4).padBottom(2);
        super.row();
        super.add(weekLabel).growX().prefHeight(Value.percentWidth(0.8f / 7f, this));
        super.row();
        super.add(new Image(separator)).growX().height(1).padTop(1).padBottom(1);
        super.row();
        super.add(stack).growX().height(Value.percentWidth((6f / 7f), this));

        monthHeader.updateHeader(currentMonth);

        super.setClip(true);
    }

    public void setSelectBehavior(SelectBehavior selectBehavior) {
        this.datesOld.selectBehavior = selectBehavior;
        this.datesNext.selectBehavior = selectBehavior;
        selectBehavior.select(datesOld.dayButtons, getDate());
    }

    public void setHoverBehavior(SelectBehavior sh) {
        this.datesOld.hoverBehavior = sh;
        this.datesNext.hoverBehavior = sh;
    }

    public DayButton[] getDateButtons() {
        return datesOld.dayButtons;
    }

    public void setDate(LocalDate date) {
        datesOld.setSelectedDate(date);
        transitionIfMonthChanged(date);
    }

    public LocalDate getDate() {
        return datesOld.getSelectedDate();
    }

    public void transitionIfMonthChanged(LocalDate date) {
        YearMonth nu = YearMonth.from(date);
        if(!nu.equals(currentMonth)){
            setMonth(nu);
        }
    }

    private void transition(int direction) {
        datesNext.setWidth(datesOld.getWidth());
        datesNext.setHeight(datesOld.getHeight());
        datesNext.setVisible(true);
        datesNext.hoverBehavior.select(datesNext.dayButtons, datesOld.getSelectedDate());
        datesNext.hoverBehavior.select(datesNext.dayButtons, datesOld.getSelectedDate());

        datesOld.clearActions();
        datesNext.clearActions();
        datesNext.setSelectedDate(datesOld.getSelectedDate());

        datesOld.setPosition(0, 0);
        datesNext.setPosition(direction * datesOld.getWidth(), 0);
        Gdx.app.postRunnable(() -> {
            datesNext.addAction(Actions.moveTo(0, 0, Cons.datePickerTransitionTime, Interpolation.sineIn));
            datesOld.addAction(Actions.sequence(Actions.moveBy(-direction * datesOld.getWidth(), 0, Cons.datePickerTransitionTime, Interpolation.sineIn), Actions.run(() -> {
                stack.setLayoutEnabled(true);
                DaysGrid g = datesOld;
                datesOld = datesNext;
                datesNext = g;
                datesNext.setVisible(false);
            })));
        });
    }

    void setMonth(YearMonth month){
        int direction = month.compareTo(currentMonth);
        currentMonth = month;
        stack.setLayoutEnabled(false);
        datesNext.setMonth(currentMonth);
        transition(direction);
        monthHeader.updateHeader(currentMonth);
    }

    private class MonthHeader extends Table {

        public Label label;
        public ImageButton next;
        public ImageButton previous;

        public MonthHeader() {
            label = new Label("Januar 2020", new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14, Fonts.BOLD), ERE.assets.grey7));

            SpriteDrawable arrowf = ERE.assets.createDrawable("icons/arrow_side");
            SpriteDrawable arrowb = ERE.assets.createDrawable("icons/arrow_side");
            arrowb.getSprite().flip(true, false);
            next = new ImageButton(arrowf);
            previous = new ImageButton(arrowb);
            
            next.getImageCell().right().maxWidth(Value.percentHeight(1, this));
            previous.getImageCell().left().maxWidth(Value.percentHeight(1, this));
            
            previous.left();
            next.right();
            
            add(previous).minSize(0).grow().pad(1);
            add(label).expand();
            add(next).minSize(0).grow().pad(1);
            label.setAlignment(Align.center);

            next.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setMonth(currentMonth.plusMonths(1));
                }
            });
            previous.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setMonth(currentMonth.minusMonths(1));
                }
            });
        }
        
        public void updateHeader(YearMonth month){
            label.setText(formatter.format(month));
        }
    }
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM' 'yyyy");

    private static class WeekLabel extends Table {

        public WeekLabel() {
            Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey7);
            add(createLabel("Mo", style)).uniform().expand();
            add(createLabel("Di", style)).uniform().expand();
            add(createLabel("Mi", style)).uniform().expand();
            add(createLabel("Do", style)).uniform().expand();
            add(createLabel("Fr", style)).uniform().expand();
            add(createLabel("Sa", style)).uniform().expand();
            add(createLabel("So", style)).uniform().expand();
        }

        private Label createLabel(String s, Label.LabelStyle style) {
            Label l = new Label(s, style);
            super.align(Align.center);
            return l;
        }
    }

    public static final SelectBehavior defaultSelectBehavior = new SelectBehavior() {
        
        private final Drawable drawable = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.mediumGreen);
        
        @Override
        public void select(DayButton[] buttons, LocalDate date) {
            for (DayButton button : buttons) {
                if(button.getDay().isEqual(date))
                    button.check(drawable);
                else
                    button.uncheck();
            }
        }
    };

    public static final SelectBehavior defaultHoverBehavior = new SelectBehavior() {
        @Override
        public void select(DayButton[] buttons, LocalDate date) {
        }
    };

    public static interface DateSelectCallback {

        public void dateSelected(LocalDate date, int direction);
    }
}
