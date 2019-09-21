/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import de.fungistudii.kalender.main.tabs.kalender.side.SidePanel;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.NamesHeader;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.Kalender;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.FixScrollPane;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class KalenderPage extends Table {
    private Table contentTable;
    private FixScrollPane pane;
    private Kalender kalender;
    private SidePanel panel;
    private NamesHeader namesHeader;
    
    public KalenderPage() {
        kalender = new Kalender();
        panel = new SidePanel();
        namesHeader = new NamesHeader();
        
        pane = new FixScrollPane(kalender);
        pane.setScrollingDisabled(true, false);
        pane.setOverscroll(false, true);
        
        contentTable = new Table();
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        contentTable.add(namesHeader).growX().fillY().pad(Value.zero, Value.percentWidth(1, kalender.groups[0]), Value.percentHeight(0.01f, this), Value.percentWidth(1, kalender.groups[0]));
        contentTable.row();
        contentTable.add(pane).grow();
        
        
        add(panel).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).grow().pad(Value.percentHeight(0.04f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.02f, this));
    }

    public void updateData() {
    }
}
