/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 *
 * @author sreis
 */
public class DrawableSolid extends TextureRegionDrawable{

    public DrawableSolid(Color color) {
        super(new TextureRegion(createTexture(color)));
    }
    
    private static final Texture createTexture(Color color){
        Pixmap bgPixmap = new Pixmap(100,100, Pixmap.Format.RGBA4444);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new Texture(bgPixmap);
    }
    
}
