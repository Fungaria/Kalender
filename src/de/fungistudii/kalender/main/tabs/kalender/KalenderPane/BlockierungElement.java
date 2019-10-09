/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Blockierung;
import de.fungistudii.kalender.util.CompositeDrawable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class BlockierungElement extends GridElement {

    private Label msg;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private int row;
    private int column;
    private int span;

    private static final Calendar calendar = Calendar.getInstance();

    private Image[] img;

    private Blockierung blockierung;

    private static final float[][] colors = new float[][]{{237, 0.09f, 1}, {208, 0.12f, 1}, {300, 0.08f, 1}};

    private BlockDrawable image;

    private final Drawable top = ERE.assets.createDrawable("kalender/grid/element_block_top");
    private final Drawable bottom = ERE.assets.createDrawable("kalender/grid/element_block_bottom");
    private final SpriteDrawable cUp = ERE.assets.createDrawable("kalender/grid/block_bg");
    private final SpriteDrawable cOver = ERE.assets.createDrawable("kalender/grid/block_bg_over");
    private final SpriteDrawable cCheck = ERE.assets.createDrawable("kalender/grid/block_bg_check");
    
    public BlockierungElement(Blockierung blockierung, int row, int column) {
        super(new ButtonStyle());

        calendar.setTime(blockierung.start);
        int minutes = calendar.get(Calendar.MINUTE) / 15;


        image = new BlockDrawable();
        image.setBackground(cUp);

        for (int i = 0; i < blockierung.dauer; i++) {
            if (i % 4 == ((4 - minutes) % 4)) {
                image.addDrawable(top);
            } else {
                image.addDrawable(bottom);
            }
        }
        super.add(new Image(image)).grow();

        this.blockierung = blockierung;

        this.row = row;
        this.column = column;

        super.setClip(true);

        super.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                image.setBackground(cOver);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                image.setBackground(cUp);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                image.setBackground(cCheck);
            }
        });
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
        } else{
            drawable = cUp;
        }
        this.image.setBackground(drawable);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateImage();
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int getRow() {
        return row;
    }

    public Blockierung getBlockierung() {
        return blockierung;
    }

    private static class BlockDrawable extends CompositeDrawable {

        private SpriteDrawable background;

        public void setBackground(SpriteDrawable drawable) {
            this.background = drawable;
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            background.draw(batch, x, y, background.getSprite().getWidth() / 2, 0, background.getSprite().getWidth(), background.getSprite().getHeight(), 1, 1, 45);
            super.draw(batch, x, y, width, height);
        }
    }
}
