/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import de.fungistudii.kalender.main.tabs.kalender.side.SidePanel;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.DateHeader;
import de.fungistudii.kalender.util.AnimationStack;
import de.fungistudii.kalender.util.DrawableSolid;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage {

    private Table contentTable;
    public SidePanel panel;
    private DateHeader dateHeader;

    public Calendar calendar = Calendar.getInstance();
    private Date currentDate = new Date();

    private KalenderTable kalender;
    
    public KalenderPage() {
        contentTable = new Table();
        
        panel = new SidePanel((date) -> {
            boolean after = date.after(currentDate);
            calendar.setTime(date);
            this.currentDate = date;
            updateDate(after?1:-1);
        });
        
        kalender = new KalenderTable(calendar);
        
        dateHeader = new DateHeader(this);

        contentTable.defaults().space(Value.percentHeight(0.01f, this));

        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        contentTable.add(dateHeader).width(Value.percentWidth(0.2f, this));
        contentTable.row();
        contentTable.add(kalender).grow();

        add(panel).prefWidth(Value.percentWidth(Cons.sideBarPercentWidth, this)).minWidth(200).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));
        
        updateDate(0);
    }

    public void updateCurrentTable() {
        kalender.updateCurrentTable();
    }
    
    public KalenderTable getKalender(){
        return kalender;
    }

    public void updateDate(int direction) {
        panel.navigation.setDate(currentDate);
        dateHeader.setDate(currentDate);
        kalender.switchDate(calendar.getTime(), direction);
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
