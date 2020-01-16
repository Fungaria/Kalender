/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 *
 * @author sreis
 */
public class NinePatchSolid extends NinePatchDrawable{

    public NinePatchSolid(Color color) {
        super(new NinePatch(createTexture(color, 5), 1, 1, 1, 1));
        super.setMinSize(0, 0);
    }
    
    public NinePatchSolid(Color color, int pad) {
        super(new NinePatch(createTexture(color, pad*3), pad, pad, pad, pad));
        super.setMinSize(0, 0);
    }
    
    private static final Texture createTexture(Color color, int size){
        Pixmap bgPixmap = new Pixmap(size,size, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new Texture(bgPixmap);
    }
}
