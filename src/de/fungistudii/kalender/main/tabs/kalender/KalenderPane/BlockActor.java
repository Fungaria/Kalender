/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.util.CompositeDrawable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BlockActor extends GridElement {

    private Label msg;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");
    private static final Calendar calendar = Calendar.getInstance();

    private BlockDrawable drawable;

    private final Drawable top = ERE.assets.createNinePatchDrawable("kalender/grid/element_block_top", 2);
    private final Drawable bottom = ERE.assets.createNinePatchDrawable("kalender/grid/element_block_bottom", 2);
    private final SpriteDrawable cUp = ERE.assets.createDrawable("kalender/grid/block_bg_r");
    private final SpriteDrawable cOver = ERE.assets.createDrawable("kalender/grid/block_bg_over_r");
    private final SpriteDrawable cCheck = ERE.assets.createDrawable("kalender/grid/block_bg_check_r");

    private final int friseur;
    private final int duration;
    private final Date start;
    
    public BlockActor(Date start, int duration, int friseur) {
        super(new ButtonStyle());

        this.start = start;
        this.duration = duration;
        this.friseur = friseur;
        
        calendar.setTime(start);
        int minutes = calendar.get(Calendar.MINUTE) / 15;

        drawable = new BlockDrawable();
        drawable.setBackground(cUp);

        for (int i = 0; i < duration; i++) {
            if (i % 4 == ((4 - minutes) % 4)) {
                drawable.addDrawable(top);
            } else {
                drawable.addDrawable(bottom);
            }
        }
        Image image = new Image(drawable);
        Label label = new Label("Urlaub", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), Color.BLACK));
        label.setAlignment(Align.topLeft);
        Container container = new Container(label);
        container.padTop(10);
        container.padLeft(15);
        container.top().left();
        super.add(new Stack(image, container)).grow();

        image.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                drawable.setBackground(cOver);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                drawable.setBackground(cUp);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                drawable.setBackground(cCheck);
            }
        });

        super.setClip(true);
    }

    /**
     * Updates the Image with the appropriate Drawable from the style before it
     * is drawn.
     */
    protected void updateImage() {
        SpriteDrawable drawable = null;
        if (isPressed()) {
            drawable = cCheck;
        } else if (isChecked()) {
            drawable = cCheck;
        } else if (isOver()) {
            drawable = cOver;
        } else {
            drawable = cUp;
        }
        this.drawable.setBackground(drawable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateImage();
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSpan() {
        return duration;
    }

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public int getFriseur() {
        return friseur;
    }

    private static class BlockDrawable extends CompositeDrawable {

        private SpriteDrawable background;

        public void setBackground(SpriteDrawable drawable) {
            this.background = drawable;
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            float bgh = background.getSprite().getRegionHeight() * width / background.getSprite().getRegionWidth();
            float n = height / (bgh);
            for (int i = 0; i < n; i++) {
                background.draw(batch, x, y + (i * bgh), width, bgh);
            }
            super.draw(batch, x, y, width, height);
        }
    }
}