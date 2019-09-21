/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabpane;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Main;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class Logo extends TextButton{
    public Logo() {
        super("Orplid Friseure", new TextButtonStyle(
                ERE.assets.createNinePatchDrawable("tabs/button_up", 4),     //up
                ERE.assets.createNinePatchDrawable("tabs/button_up", 4),     //down 
                ERE.assets.createNinePatchDrawable("tabs/button_up", 4),     //checked
                Main.ERE.assets.openSansBoldSmall
        ));
        
        super.getLabel().setFontScale(0.5f);
        super.getLabel().setAlignment(Align.left);
        super.getLabel().setX(5);
    }
}
