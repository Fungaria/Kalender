/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
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

    private Stack stack;

    private final Vector2 tmpVec = new Vector2();

    public DatePicker(DateSelectCallback callback) {
        datesOld = new DaysGrid(callback);
        datesNext = new DaysGrid(callback);
        weekLabel = new WeekLabel();
        monthHeader = new MonthHeader();

        Drawable separator = ERE.assets.createDrawable("kalender/navigation/separator");

        stack = new Stack();
        stack.add(datesOld);

        super.add(monthHeader).growX().prefHeight(Value.percentWidth(0.8f / 8f, this));
        super.row();
        super.add(new Image(separator)).growX();
        super.row();
        super.add(weekLabel).growX().prefHeight(Value.percentWidth(0.8f / 7f, this));
        super.row();
        super.add(new Image(separator)).growX();
        super.row();
        super.add(stack).growX().prefHeight(Value.percentWidth((6f / 7f), this));
        super.row();
        super.add(new Image(separator)).growX();

        updateHeader();

        datesOld.updateButtons();
        stack.addActor(datesNext);
        datesNext.setVisible(false);

        super.setClip(true);

        Vector2 l = datesOld.localToStageCoordinates(new Vector2(0, 0));
        datesOld.rect = new Rectangle(l.x, l.y, datesOld.getWidth(), datesOld.getHeight());
    }

    public void setDate(Date date) {
        datesOld.setDate(date);
        updateHeader();
    }

    private void updateHeader() {
        monthHeader.label.setText(datesNext.getHeaderName());
    }

    public Date getDate() {
        return datesOld.getSelected();
    }

    private class MonthHeader extends Table {

        public Label label;
        public ImageButton next;
        public ImageButton previous;

        public MonthHeader() {
            label = new Label("Januar 2020", new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14, Fonts.BOLD), ERE.assets.grey5));

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
                    datesNext.next(1);
                    Gdx.app.postRunnable(()->{
                    updateHeader();
                    transition(1);
                    });
                }
            });
            previous.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    datesNext.previous(1);
                    updateHeader();
                    Gdx.app.postRunnable(()->{
                    transition(-1);
                    });
                }
            });
        }
    }

    private void transition(int direction) {
        datesNext.setVisible(true);
        datesNext.setPosition(-direction*datesOld.getWidth(), 0);
        datesNext.addAction(Actions.moveBy(direction*datesOld.getWidth(), 0, 0.2f, Interpolation.sineIn));
        datesOld.addAction(Actions.sequence(Actions.moveBy(direction*datesOld.getWidth(), 0, 0.2f, Interpolation.sineIn), Actions.hide(), Actions.run(() -> {
            datesOld.next(direction);
            DaysGrid g = datesOld;
            datesOld = datesNext;
            datesNext = g;
        })));
    }

    private static class WeekLabel extends Table {

        public WeekLabel() {
            Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 14), ERE.assets.grey5);
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

    public static interface DateSelectCallback {

        public void dateSelected(Date date);
    }
}
