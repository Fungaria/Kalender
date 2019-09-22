/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.side;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.MenuButton;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddTerminDialog;
import de.fungistudii.kalender.main.tabs.kalender.Navigation;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class SidePanel extends Container {

    private Navigation navigation;
    private MenuButton terminHinzufügen;
    
    private Table content;
    
    private final AddTerminDialog dialog;
    
    public SidePanel(Navigation.DateSelectCallback callback) {
        super.setBackground(new DrawableSolid(ERE.assets.grey1));
        super.top();
        super.fill();
        
        content = new Table();
        navigation = new Navigation(callback);
        terminHinzufügen = new MenuButton("Termin hinzufugen");
        dialog = new AddTerminDialog();
        
        content.add(navigation).fillX().height(Value.percentWidth(1.2f, this));
        content.row();
        content.add(terminHinzufügen).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
        content.row();
        content.add().grow();
        
        content.pad(Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this));
        setActor(content);
        
        terminHinzufügen.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                dialog.show(ERE.mainScreen.stage, navigation.getDate());
            }
        });
    }
}
