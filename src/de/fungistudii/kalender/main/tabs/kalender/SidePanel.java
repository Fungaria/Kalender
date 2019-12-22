/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.GenericTextButton;
import de.fungistudii.kalender.main.generic.DatePicker;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class SidePanel extends Container {

    public DatePicker navigation;
    private GenericTextButton terminHinzufügen;
    
    private Table content;
    
    public SidePanel(DatePicker.DateSelectCallback callback) {
        super.setBackground(new NinePatchSolid(ERE.assets.kalSide));
        super.top();
        super.fill();
        
        content = new Table();
        navigation = new DatePicker(callback);
        terminHinzufügen = new GenericTextButton("Termin hinzufugen", new GenericTextButton.FilledStyle());
        Drawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        
        content.defaults().space(15);
        
        navigation.row();
        navigation.add(new Image(separator)).height(1).growX();
        content.add(navigation).fill().height(Value.percentWidth(1, this)).minWidth(0);
        content.row();
        content.add(terminHinzufügen).minSize(0).growX();
        content.row();
        content.add().grow();
        
        content.pad(Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this));
        setActor(content);
        
        terminHinzufügen.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.addTermin();
            }
        });
    }
}
