/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import de.fungistudii.kalender.main.tabs.kalender.side.SidePanel;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.NamesHeader;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.Kalender;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.FixScrollPane;
import de.fungistudii.kalender.util.DrawableSolid;
import java.util.Calendar;

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
    
    private Calendar calendar = Calendar.getInstance();
    
    public KalenderPage() {
        kalender = new Kalender(calendar);
        panel = new SidePanel((date) -> {
            calendar.setTime(date);
            kalender = new Kalender(calendar);
            pane.setActor(kalender);
        });
        namesHeader = new NamesHeader(ERE.data.root.friseure.stream().map((f) -> f.name).toArray(String[]::new));
        
        pane = new FixScrollPane(kalender);
        pane.setScrollingDisabled(true, false);
        pane.setOverscroll(false, true);
        
        contentTable = new Table();
        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        contentTable.add(namesHeader).growX().fillY().pad(Value.zero, Value.percentWidth(1, kalender.getCells().get(0).getActor()), Value.percentHeight(0.01f, this), Value.percentWidth(1, kalender.getCells().get(0).getActor()));
        contentTable.row();
        contentTable.add(pane).grow();
        
        add(panel).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).grow().pad(Value.percentHeight(0.04f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.02f, this));
    }
    
    public void addTermin(Termin termin){
        kalender.reloadColumn(termin.friseur);
    }
}
