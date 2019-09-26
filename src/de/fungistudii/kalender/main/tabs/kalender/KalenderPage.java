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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.DateHeader;
import de.fungistudii.kalender.util.DrawableSolid;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage {

    private Table contentTable;
    private ScrollPane pane;
    private Kalender kalender;
    public SidePanel panel;
    private NamesHeader namesHeader;
    private DateHeader dateHeader;

    public Calendar calendar = Calendar.getInstance();

    public KalenderPage() {
        contentTable = new Table();
        kalender = new Kalender(calendar);
        panel = new SidePanel((date) -> {
            calendar.setTime(date);
            updateDate();
        });
        namesHeader = new NamesHeader(ERE.data.root.friseure.stream().map((f) -> f.name).toArray(String[]::new));

        pane = new ScrollPane(null);
        pane.setScrollingDisabled(true, false);
        pane.setOverscroll(false, true);

        dateHeader = new DateHeader(this);

        contentTable.defaults().space(Value.percentHeight(0.01f, this));

        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        contentTable.add(dateHeader).width(Value.percentWidth(0.2f, this));
        contentTable.row();
        contentTable.add(namesHeader);
        contentTable.row();
        contentTable.add(pane).grow();

        add(panel).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));

        updateDate();
    }

    public void addTermin(Termin termin) {
        kalender.reloadColumn(termin.friseur);
    }

    public void updateDate() {
        kalender = new Kalender(calendar);
        pane.setActor(kalender);
        panel.navigation.setDate(calendar.getTime());
        dateHeader.setDate(calendar.getTime());
        
        contentTable.getCell(namesHeader).growX().fillY().pad(Value.percentHeight(0.01f, this), Value.percentWidth(1, kalender.getCells().get(0).getActor()), Value.zero, Value.percentWidth(1, kalender.getCells().get(0).getActor())).center();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
