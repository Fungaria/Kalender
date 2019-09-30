package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sreis
 */
public class ContextMenu extends Table {

    protected HashMap<String, Runnable> content;

    private Actor parent;

    private boolean open = false;

    private ClickListener openListener = new ClickListener(Buttons.RIGHT) {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            show(x, y);
        }
    };
    
    public ContextMenu(Actor parent, String[] titles, Runnable[] runnables){
        content = new HashMap<>();
        this.parent = parent;
        for (int i = 0; i < titles.length; i++) {
            content.put(titles[i], runnables[i]);
        }
        
        init();
    }

    public ContextMenu(Actor parent, HashMap<String, Runnable> content) {
        this.parent = parent;
        this.content = content;

        init();
    }

    protected void init(){
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchSolid(Color.WHITE);
        style.over = new NinePatchSolid(ERE.assets.grey2);
        style.font = ERE.assets.fonts.createFont("roboto", 13, Fonts.LIGHT);
        style.fontColor = ERE.assets.grey5;

        for (Map.Entry<String, Runnable> entry : content.entrySet()) {
            TextButton button = new TextButton(entry.getKey(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    entry.getValue().run();
                }
            });
            add(button).height(25).width(180);
            button.getLabel().setAlignment(Align.left);
            button.getLabelCell().padLeft(15);
            row();
        }
        
        super.setBackground(ERE.assets.createNinePatchDrawable("generic/context_bg", 7));
        parent.addListener(openListener);
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
    
    private static final Vector2 tmpVec = new Vector2();

    public void setShowOnRightClick(boolean show) {
        if (!show) {
            parent.removeListener(openListener);
        }
    }

    public void hide() {
        open = false;
        super.remove();
        if (parent instanceof Button) {
            ((Button) parent).setChecked(false);
        }
        ERE.mainScreen.root.setTouchable(Touchable.enabled);
    }

    public void show(float x, float y) {
        parent.localToStageCoordinates(tmpVec.set(x, y));
        tmpVec.sub(0, getHeight());
        setPosition(tmpVec.x, tmpVec.y);

        if (parent instanceof Button) {
            ((Button) parent).setChecked(true);
        }

        open = true;
        ERE.mainScreen.root.setTouchable(Touchable.disabled);
        pack();
        parent.getStage().addActor(this);
        parent.getStage().setKeyboardFocus(this);
        parent.getStage().setScrollFocus(this);
    }
}
