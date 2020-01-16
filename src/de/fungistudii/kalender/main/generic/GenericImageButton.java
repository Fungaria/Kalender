package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class GenericImageButton extends ImageButton{

    public GenericImageButton(ImageButtonStyle style) {
        super(style);
    }

    public GenericImageButton(String icon) {
        super(new Style(icon));
        getImageCell().pad(3, 3, 3, 7.5f);
    }

    public void addListener(Runnable r){
        super.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                r.run();
            }
        });
    }
    
    @Override
    public float getPrefHeight() {
        return Cons.defaultRowHeight;
    }

    @Override
    public float getPrefWidth() {
        return Cons.defaultRowHeight;
    }

    @Override
    public float getMinWidth() {
        return Cons.defaultRowHeight;
    }

    @Override
    public float getMaxWidth() {
        return Cons.defaultRowHeight;
    }
    
    private static class Style extends ImageButtonStyle{
        public Style(String icon) {
            super.imageUp = ERE.assets.createDrawable(icon);
            super.up = ERE.assets.createRounded("outline");
            super.over = ERE.assets.createRounded("outline_over");
            super.down = ERE.assets.createRounded("outline_down");
        }
    }
}
