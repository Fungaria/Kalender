/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import de.fungistudii.kalender.main.tabs.kalender.side.SidePanel;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.DateHeader;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.GridElement;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddTerminDialog;
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

    private final AddTerminDialog dialog;
    
    private KalenderTable kalender;
    
    public KalenderPage() {
        contentTable = new Table();
        
        dialog = new AddTerminDialog();
        
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

    public void addTermin(){
        Button selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();
        
        dialog.show(ERE.mainScreen.stage, panel.navigation.getDate());
        if(selectedElement instanceof BackgroundElement){
            BackgroundElement e = (BackgroundElement)selectedElement;
            dialog.friseur.setSelectedIndex(e.column);
            dialog.timeHours.setSelectedIndex(e.row/4);
            dialog.timeMins.setSelectedIndex(e.row%4);
        }
    }
    
    public void addBlockierung(){
        Button selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();
        int duration = ERE.mainScreen.kalender.getKalender().getSelectedDuration();
        
        int mins = ((GridElement)selectedElement).getRow()%4;
        int hours = ((GridElement)selectedElement).getRow()/4;
        
        calendar.set(Calendar.HOUR_OF_DAY, hours+8);
        calendar.set(Calendar.MINUTE, mins*15);
        
        System.out.println("----"+calendar.getTime());
        
        NetworkData.BlockRequest request = new NetworkData.BlockRequest();
        request.duration = duration;
        request.start = calendar.getTime();
        request.friseur = ((GridElement)selectedElement).getColumn();
        request.msg = "";
        
        ERE.client.sendTCP(request);
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
