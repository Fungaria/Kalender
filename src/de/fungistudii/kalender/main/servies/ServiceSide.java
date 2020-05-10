/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.servies;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.GenericTextButton;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class ServiceSide extends Container {

    private GenericTextButton serviceBearbeiten;
    
    private Table content;
    
    public ServiceSide() {
        super.setBackground(new DrawableSolid(ERE.assets.grey1));
        super.top();
        super.fill();
        
        content = new Table();
        serviceBearbeiten = new GenericTextButton("Service Hinzufügen", new GenericTextButton.FilledStyle());
        
        content.add(serviceBearbeiten).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
        content.row();
        content.add().grow();
        
        content.pad(Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this));
        setActor(content);
        
        serviceBearbeiten.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.dialogManager.showService(null);
//                ERE.mainScreen.service.dialog.setTitle("Service Hinzufügen");
            }
        });
    }
}