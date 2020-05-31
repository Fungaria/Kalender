/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Popup;

/**
 *
 * @author sreis
 */
public class GenericDialog extends Popup {

    private final TextButton okButton;
    private final TextButton cancelButton;
    
    private final Label titleLabel;
    private final ImageButton closeButton;
    
    private final Cell actorCell;
    private final Cell bottomCell;
    
    protected final SpriteDrawable separator;
    
    private final Array<Runnable> cancelCallbacks = new Array<>();
    private final Array<Runnable> confirmCallbacks = new Array<>();
    
    public GenericDialog(String title) {
        NinePatchDrawable np = ERE.assets.createNinePatchDrawable("generic/rounded_stripped", Cons.roundedPad, new Color(0.93f, 0.93f, 0.93f, 1));
        np.setPadding(0, 0, 0, 0);
        super.setBackground(np);
        
        this.separator = ERE.assets.createDrawable("generic/horizontal_separator");
        
        this.okButton = new GenericTextButton("Bestätigen", new GenericTextButton.FilledStyle());
        this.cancelButton = new GenericTextButton("Abbrechen", new GenericTextButton.CancelStyle());
        this.titleLabel = new Label(title, new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20), Color.BLACK));
        this.closeButton = new ImageButton(ERE.assets.createDrawable("icons/cross", ERE.assets.grey4));
        this.closeButton.getStyle().imageOver = ERE.assets.createDrawable("icons/cross", ERE.assets.grey7);
        
        Table titleTable = new Table();
        titleTable.add(titleLabel).grow().pad(Cons.dialogOuterPadding/2, Cons.dialogOuterPadding, Cons.dialogOuterPadding/2, 0);
        titleTable.add(closeButton).size(Value.percentHeight(0.7f, titleLabel)).padRight(20);
        
        Table buttonTable = new Table();
        buttonTable.bottom();
        buttonTable.defaults().space(10).bottom();
        bottomCell = buttonTable.add(new Container()).grow().left().bottom();
        buttonTable.add(cancelButton).width(Value.percentWidth(0.25f, this));
        buttonTable.add(okButton).width(Value.percentWidth(0.25f, this));
        
        super.add(titleTable).left().grow();
        super.row();
        super.add(new Image(separator)).grow().height(1);
        super.row().padTop(Cons.dialogRowPadTop);
        actorCell = super.add().left().grow();
        super.row();
        super.add(buttonTable).grow().pad(Cons.dialogRowPadTop, Cons.dialogOuterPadding, Cons.dialogOuterPadding/2f, Cons.dialogOuterPadding/2);
        
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                confirmCallbacks.forEach(c -> c.run());
            }
        });
        cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                cancelCallbacks.forEach(c -> c.run());
            }
        });
        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                cancelCallbacks.forEach(c -> c.run());
            }
        });
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Keys.ENTER){
                    hide();
                    confirmCallbacks.forEach(c -> c.run());
                    return true;
                }else if(keycode == Keys.ESCAPE){
                    hide();
                    cancelCallbacks.forEach(c -> c.run());
                    return true;
                }
                return false;
            }
        });
    }
    
    public void setTitle(String title){
        this.titleLabel.setText(title);
    }
    
    public String getTitle(){
        return this.titleLabel.getName();
    }
    
    public void show(){
        super.show(ERE.mainScreen.stage);
    }
    
    public Cell setMainActor(Actor actor){
        actorCell.setActor(actor);
        invalidateHierarchy();
        return actorCell;
    }
    
    /**
     * sets the Actor left to the cancel/confirm Buttons. Empty by default.
     */
    public Cell setBottomActor(Actor actor){
        bottomCell.setActor(actor);
        return bottomCell;
    }
    
    public void addConfirmCallback(Runnable runnable){
        confirmCallbacks.add(runnable);
    }
    
    public void addCancelCallback(Runnable runnable){
        cancelCallbacks.add(runnable);
    }
}
