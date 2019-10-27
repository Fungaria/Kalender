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

    private TextButton okButton;
    private TextButton cancelButton;
    
    private Button closeButton;
    
    private Cell actorCell;
    
    public GenericDialog(String title) {
        super();
        popupContainer.setBackground(new DrawableSolid(new Color(0.9f, 0.9f, 0.9f, 1)));
//        popupContainer.prefWidth(700);
        super.setStageBackground(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        
        Label titleLabel = new Label(title, new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), Color.BLACK));
        
        okButton = new GenericTextButton("Bestätigen", new GenericTextButton.FilledStyle());
        cancelButton = new GenericTextButton("Abbrechen", new GenericTextButton.CancelStyle());
        ImageButton.ImageButtonStyle closeStyle = new ImageButton.ImageButtonStyle();
        closeStyle.imageUp = ERE.assets.createDrawable("generic/cross", ERE.assets.grey3);
        closeStyle.imageOver = ERE.assets.createDrawable("generic/cross", ERE.assets.grey5);
        closeButton = new ImageButton(closeStyle);
        Image separator = new Image(ERE.assets.createDrawable("generic/separator"));
        
        super.contentTable.defaults().space(10);
        
        Table group = new Table();
        group.add(titleLabel).grow().padLeft(Value.percentHeight(1, titleLabel));
        group.add(closeButton).size(Value.percentHeight(1, titleLabel)).padRight(20);
        
        super.contentTable.add(group).colspan(3).padTop(20).growX();
        super.contentTable.row();
        super.contentTable.add(separator).grow().colspan(3).height(1);
        super.contentTable.row();
        actorCell = super.contentTable.add().colspan(3).pad(17).left().grow();
        super.contentTable.row().padBottom(20);
        super.contentTable.add(new Image()).grow();
        super.contentTable.add(cancelButton).height(40).width(Value.percentWidth(0.25f, contentTable));
        super.contentTable.add(okButton).height(40).width(Value.percentWidth(0.25f, contentTable)).padRight(20);
        
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
        closeButton.addListener(new ClickListener(){
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
