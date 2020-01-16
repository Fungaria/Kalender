package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.PaddedDrawable;
import de.fungistudii.kalender.util.value.ValueUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public abstract class CurrentTimeLine extends Container {

    private final Value elementHeight;

    private ValueUtil.PercentValue position;

    public CurrentTimeLine(Value elementHeight) {
        top();
        fillX();

        this.elementHeight = elementHeight;
        position = ValueUtil.percentValue(1, elementHeight);
        padTop(position);
    }

    int interval = 1000;
    long nextExecution = System.currentTimeMillis();

    @Override
    public void act(float delta) {
        super.act(delta);
        if (nextExecution < System.currentTimeMillis()) {
            nextExecution = System.currentTimeMillis() + interval;
            updatePosition();
        }
    }

    protected Date date = new Date();
    protected void updatePosition() {
        date.setTime(System.currentTimeMillis());
        float minute = DateUtil.getMinuteOfDay(date);
        minute -= KalenderTable.startTime * 60;
        position.setPercent(minute / 15f);
        invalidate();
    }

    public static class Line extends CurrentTimeLine {
        public Line(Value elementHeight) {
            super(elementHeight);
            setActor(new Image(ERE.assets.createDrawable("generic/horizontal_separator", Color.RED)));
        }
    }

    
    public static class Elbel extends CurrentTimeLine {
        private Label label;
        private PaddedDrawable bg;
        private final SimpleDateFormat format = new SimpleDateFormat("HH':'mm");
        public Elbel(Value elementHeight) {
            super(elementHeight);
            Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16), Color.BLACK);
            bg = new PaddedDrawable(ERE.assets.createNinePatchDrawable("generic/rounded_red", 10));
            bg.getChild().setLeftWidth(5);
            bg.setMinHeight(0);
            bg.setMinWidth(0);
            style.background = bg;
            
            label = new Label("13:45", style){
                @Override
                public float getPrefHeight() {
                    return 30;
                }

                @Override
                public float getY() {
                    return super.getY()+15;
                }
            };
            
            label.setAlignment(Align.left);
            minWidth(0);
            setActor(label);
        }

        @Override
        protected void updatePosition() {
            super.updatePosition();
            label.setText(format.format(date));
        }
    }

}
