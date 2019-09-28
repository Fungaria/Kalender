/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class NamesHeader extends Table{
    
    private final String[] names;
    
    private Label[] labels;
    
    private final Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 17), ERE.assets.grey4);
    public NamesHeader(String[] names, Innerlander inner) {
        this.names = names;
        super.center();
        this.labels = new Label[names.length];
        for (int i = 0; i < 7; i++) {
            labels[i] = new Label(names[i], style);
            labels[i].setAlignment(Align.center);
            add(labels[i]).grow().uniform().width(Value.percentWidth(1, inner.columns[0]));
            add(new Container()).width(Value.percentWidth(1, inner.getCells().get(1).getActor()));
            
            super.setClip(true);
        }
    }
}
