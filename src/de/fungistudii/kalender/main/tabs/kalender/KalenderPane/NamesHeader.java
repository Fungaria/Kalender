/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class NamesHeader extends Table{
    
    private String[] names = new String[]{"Meryem", "Nena", "Nicole", "Barbara", "Nardos", "Linh", "Nida"};
    
    private Label[] labels = new Label[names.length];
    
    private final Label.LabelStyle style = new Label.LabelStyle(ERE.assets.openSansSmall, ERE.assets.grey4);
    public NamesHeader() {
        for (int i = 0; i < 7; i++) {
            labels[i] = new Label(names[i], style);
            labels[i].setAlignment(Align.bottom);
            labels[i].setFontScale(0.7f);
            add(labels[i]).grow().uniform();
        }
    }
}
