/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabpane;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Main;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class Logo extends TextButton{
    public Logo() {
        super("Orplid Friseure", new Style());
        
        
        super.getLabel().setAlignment(Align.left);
        super.getLabel().setX(5);
        super.getLabelCell().padLeft(5);
    }
    
    private static class Style extends TextButtonStyle{
        public Style() {
            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
            param.minFilter = Texture.TextureFilter.MipMapLinearLinear;
            param.size = 16;
            param.genMipMaps = true;
            super.up = ERE.assets.createNinePatchDrawable("tabs/button_up", 4);
            super.font = Main.ERE.assets.fonts.createFont("roboto", param, Fonts.BOLD);
        }
    }
}
