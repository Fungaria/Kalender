/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author sreis
 */
public class CompositeDrawable extends BaseDrawable{
        
        private Array<Drawable> drawables = new Array<>();
        
        public CompositeDrawable() {
        }
        
        public CompositeDrawable(Drawable... drawables){
            this.drawables.addAll(drawables);
        }
        
        public void addDrawable(Drawable d){
            drawables.add(d);
        }
        
        @Override
        public void draw (Batch batch, float x, float y, float width, float height) {
            float elementHeight = height/drawables.size;
            for (int i = 0; i < drawables.size; i++) {
                drawables.get(i).draw(batch, x, y+elementHeight*(drawables.size-i-1), width, elementHeight);
            }
	}
    }