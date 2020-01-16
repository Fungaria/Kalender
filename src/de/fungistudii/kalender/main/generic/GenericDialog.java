/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Popup;

/**
 *
 * @author sreis
 */
public class GenericDialog extends Popup {

    private final TextButton okButton;
    private final TextButton cancelButton;
    
    private final Cell actorCell;
    
    public GenericDialog(String title) {
        super(title);
        popupContainer.setBackground(new DrawableSolid(new Color(0.9f, 0.9f, 0.9f, 1)));
        super.setStageBackground(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        
        okButton = new GenericTextButton("Bestätigen", new GenericTextButton.FilledStyle());
        cancelButton = new GenericTextButton("Abbrechen", new GenericTextButton.CancelStyle());
        
        super.contentTable.defaults().space(10);
        
        Table buttons = new Table();
        buttons.defaults().space(10);
        buttons.add(new Image()).grow();
        buttons.add(cancelButton).height(40).width(Value.percentWidth(0.25f, contentTable));
        buttons.add(okButton).height(40).width(Value.percentWidth(0.25f, contentTable));
        
        actorCell = super.contentTable.add().left().grow();
        super.contentTable.row();
        super.contentTable.add(buttons).grow();
        
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }
    
    public void show(){
        super.show(ERE.mainScreen.stage);
    }
    
    public void setActor(Actor actor){
        actorCell.setActor(actor);
        invalidateHierarchy();
    }
    
    public void addConfirmCallback(Runnable runnable){
        this.okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        });
    }
    public void addCancelCallback(Runnable runnable){
        this.cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        });
        this.closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        });
    }
}
