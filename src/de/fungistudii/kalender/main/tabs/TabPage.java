/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * @author sreis
 */
public abstract class TabPage extends Table{
    public abstract void show();
    public abstract void hide();
}
