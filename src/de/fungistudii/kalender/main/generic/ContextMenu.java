package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.util.function.Consumer;

/**
 *
 * @author sreis
 */
public class ContextMenu<T extends Actor> extends Table {

    private boolean open = false;
    private Class type;
    
    private T currentContext;
    
    private ClickListener openListener = new ClickListener(Buttons.RIGHT) {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Actor hit = ERE.mainScreen.stage.hit(x, y, true);
            currentContext = getType(hit);
            if(currentContext != null){
                show(x, y);
            }
        }
    };
    
    private T getType(Actor actor){
        Actor current = actor;
        for (int i = 0; i < 4; i++) {
            if(current == null)
                return null;
            else if(current.getClass().equals(type))
                return (T)current;
            else
                current = current.getParent();
        }
        return null;
    }

    
    public ContextMenu(Class type, ContextEntry<T>... entries) {
        this.type = type;
        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchSolid(Color.WHITE);
        style.over = new NinePatchSolid(ERE.assets.grey3);
        style.font = ERE.assets.fonts.createFont("roboto", 13, Fonts.LIGHT);
        style.fontColor = ERE.assets.grey7;

        for (ContextEntry entry : entries) {
            TextButton button = new TextButton(entry.title, style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    entry.runnable.accept(currentContext);
                }
            });
            add(button).height(25).width(180);
            button.getLabel().setAlignment(Align.left);
            button.getLabelCell().padLeft(15);
            row();
        }
        
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/context_bg", 7));
        ERE.mainScreen.stage.addListener(openListener);
        ERE.mainScreen.stage.addCaptureListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (open) {
                    hide();
                }
                return false;
            }

            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        if (open) {
                            hide();
                            event.stop();
                        }
                        return true;
                }
                return false;
            }
        });
    }
    
    public void hide() {
        open = false;
        super.remove();
        ERE.mainScreen.root.setTouchable(Touchable.enabled);
    }
    
    public void show(float x, float y) {
        ERE.mainScreen.stage.addActor(this);
        ERE.mainScreen.stage.setKeyboardFocus(this);
        ERE.mainScreen.stage.setScrollFocus(this);
        pack();
        setPosition(x, y-getHeight());

        if (currentContext instanceof Button) {
            ((Button) currentContext).setChecked(true);
        }

        open = true;
        ERE.mainScreen.root.setTouchable(Touchable.disabled);
    }
    
    public static class ContextEntry<T extends Actor>{
        public String title;
        public Consumer<T> runnable;

        public ContextEntry(String title, Consumer<T> runnable) {
            this.title = title;
            this.runnable = runnable;
        }

        public ContextEntry() {
        }
    }
}
