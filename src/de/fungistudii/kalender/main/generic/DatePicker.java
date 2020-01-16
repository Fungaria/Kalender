/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import de.fungistudii.kalender.main.generic.DaysGrid.DayButton;
import de.fungistudii.kalender.main.generic.DaysGrid.SelectBehavior;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private DateSelectCallback callback;

    private final DateSelectCallback monthChanger = new DateSelectCallback() {
        @Override
        public void dateSelected(Date date, int direction) {
            transitionIfMonthChanged(date);
        }
    };

    public DatePicker(DateSelectCallback callback) {
        datesOld = new DaysGrid(callback, monthChanger);
        datesNext = new DaysGrid(callback, monthChanger);
        weekLabel = new WeekLabel();
        monthHeader = new MonthHeader();

        this.callback = callback;

        Drawable separator = ERE.assets.createDrawable("generic/vertical_separator");

        stack = new TestStack();
        stack.add(datesOld);

        super.add(monthHeader).growX().prefHeight(Value.percentWidth(0.8f / 8f, this));
        super.row();
        super.add(new Image(separator)).growX().height(1).padTop(4).padBottom(2);
        super.row();
        super.add(weekLabel).growX().prefHeight(Value.percentWidth(0.8f / 7f, this));
        super.row();
        super.add(new Image(separator)).growX().height(1).padTop(1).padBottom(1);
        super.row();
        super.add(stack).growX().height(Value.percentWidth((6f / 7f), this));

        monthHeader.setDate(datesOld.firstOfMonth());
        
        stack.addActor(datesNext);

        datesNext.setVisible(false);

        super.setClip(true);
    }

    public SelectBehavior getSelectBehavior() {
        return datesOld.getSelectBehavior();
    }

    public void setSelectBehavior(SelectBehavior selectBehavior) {
        this.datesOld.setSelectBehavior(selectBehavior);
        this.datesNext.setSelectBehavior(selectBehavior);
    }

    public void setHoverBehavior(SelectBehavior sh) {
        this.datesOld.setHoverBehavior(sh);
        this.datesNext.setHoverBehavior(sh);
    }

    public DayButton[] getDateButtons() {
        return datesOld.dayButtons;
    }

    public void setDate(Date date) {
        datesOld.setSelectedDate(date);
        transitionIfMonthChanged(date);
    }

    public Date getDate() {
        return datesOld.getDate();
    }

    public void transitionIfMonthChanged(Date date) {
        if (DateUtil.compareMonth(date, datesOld.firstOfMonth()) == 1) {
            next();
        } else if (DateUtil.compareMonth(date, datesOld.firstOfMonth()) == -1) {
            previous();
        }
    }

    private void transition(int direction) {
        datesNext.setWidth(datesOld.getWidth());
        datesNext.setHeight(datesOld.getHeight());
        datesNext.setVisible(true);
        datesNext.getHoverBehavior().select(datesNext.dayButtons, datesOld.getDate());
        datesNext.getSelectBehavior().select(datesNext.dayButtons, datesOld.getDate());

        datesOld.clearActions();
        datesNext.clearActions();
        datesNext.setSelectedDate(datesOld.getDate());

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

    void next() {
        stack.setLayoutEnabled(false);
        datesNext.next(datesOld.firstOfMonth());
        transition(1);
        monthHeader.setDate(datesNext.firstOfMonth());
    }

    void previous() {
        stack.setLayoutEnabled(false);
        datesNext.previous(datesOld.firstOfMonth());
        transition(-1);
        monthHeader.setDate(datesNext.firstOfMonth());
    }

    private class MonthHeader extends Table {

        public Label label;
        public ImageButton next;
        public ImageButton previous;

        private final SimpleDateFormat headerFormat = new SimpleDateFormat("MMMM' 'yyyy");
        
        public MonthHeader() {
            label = new Label("Januar 2020", new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14, Fonts.BOLD), ERE.assets.grey6));

            SpriteDrawable arrowf = ERE.assets.createDrawable("kalender/navigation/arrow_up");
            SpriteDrawable arrowb = ERE.assets.createDrawable("kalender/navigation/arrow_up");
            arrowb.getSprite().flip(true, false);
            next = new ImageButton(arrowf);
            previous = new ImageButton(arrowb);

            super.left();
            add(previous).minSize(0).prefSize(Value.percentHeight(0.7f, this));
            add(label).prefWidth(Value.percentWidth(0.9f, this));
            add(next).minSize(0).prefSize(Value.percentHeight(0.7f, this));
            label.setAlignment(Align.center);

            next.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    next();
                }
            });
            previous.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    previous();
                }
            });
        }
        
        public void setDate(Date date){
            label.setText(headerFormat.format(date));
        }
    }

    private static class WeekLabel extends Table {

        public WeekLabel() {
            Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey6);
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
        public void select(DaysGrid.DayButton[] buttons, Date date) {
            for (DayButton button : buttons) {
                if(DateUtil.compareDay(button.getDay(), date) == 0)
                    button.check(drawable);
                else
                    button.uncheck();
            }
        }
    };

    public static final SelectBehavior defaultHoverBehavior = new SelectBehavior() {
        @Override
        public void select(DaysGrid.DayButton[] buttons, Date date) {
        }
    };

    public static class WeekSelectBehavior implements SelectBehavior {
        private final NinePatchSolid solid = new NinePatchSolid(ERE.assets.lightGreen, 10);
        private final Drawable left = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 10, ERE.assets.mediumGreen);
        private final Drawable right = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 10, ERE.assets.mediumGreen);
        private final Drawable def = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, Color.CLEAR);

        @Override
        public void select(DaysGrid.DayButton[] buttons, Date date) {
            for (DayButton button : buttons) {
                if (DateUtil.compareWeek(date, button.getDay()) == 0) {
                    if(DateUtil.getDayOfWeek(button.getDay()) == Calendar.MONDAY){
                        button.check(left);
                    }else if(DateUtil.getDayOfWeek(button.getDay()) == Calendar.SUNDAY){
                        button.check(right);
                    }else{
                        button.check(solid);
                    }
                } else {
                    button.uncheck();
                }
            }
        }
    };

    public static interface DateSelectCallback {

        public void dateSelected(Date date, int direction);
    }
}
