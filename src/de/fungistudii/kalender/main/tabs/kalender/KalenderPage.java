/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.Gdx;
import de.fungistudii.kalender.main.tabs.kalender.side.SidePanel;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BlockierungElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.GridElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.StornoDialog;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day.DayTable;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week.WeekTable;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddAppointmentDialog;
import de.fungistudii.kalender.util.DrawableSolid;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage {

    private Table contentTable;
    public final SidePanel sidePanel;

    public Calendar calendar = Calendar.getInstance();
    private Date currentDate = new Date();

    public final AddAppointmentDialog addAppointment;
    public final StornoDialog stornoDialog;

    public final DayTable dayTable;
    public final WeekTable weekTable;

    public KalenderTable currentTable;
    
    private Cell<KalenderTable> cell;

    public KalenderPage() {
        contentTable = new Table();

        addAppointment = new AddAppointmentDialog();
        stornoDialog = new StornoDialog();

        sidePanel = new SidePanel((date) -> {
            boolean after = date.after(currentDate);
            calendar.setTime(date);
            this.currentDate = date;
            updateDate(after ? 1 : -1);
        });

        dayTable = new DayTable(calendar.getTime());
        weekTable = new WeekTable(calendar.getTime());
        currentTable = weekTable;
        
        contentTable.defaults().space(Value.percentHeight(0.01f, this));

        contentTable.setBackground(new DrawableSolid(Color.WHITE));
        contentTable.row();
        cell = contentTable.add(currentTable).grow();

        add(sidePanel).prefWidth(Value.percentWidth(Cons.sideBarPercentWidth, this)).minWidth(200).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.02f, this));

        updateDate(0);
    }
    
    public void toWeekView(int workerId){
        weekTable.setFriseur(workerId);
        currentTable = weekTable;
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        updateDate(0);
        cell.setActor(currentTable); 
        currentTable.invalidateHierarchy();
    }
    
    public void toDayView(int dayOfWeek){
        currentTable = dayTable;
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        updateDate(0);
        cell.setActor(currentTable);
        currentTable.invalidateHierarchy();
    }

    public void updateCurrentTable() {
        currentTable.updateCurrentTable();
    }

    public KalenderTable getKalender() {
        return currentTable;
    }

    public void updateDate(int direction) {
        currentDate = calendar.getTime();
        currentTable.switchDate(calendar.getTime(), direction);
        sidePanel.navigation.setDate(currentDate);
    }

    public void addTermin() {
        Button selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();

        addAppointment.show(ERE.mainScreen.stage, sidePanel.navigation.getDate());
        if (selectedElement instanceof BackgroundElement) {
            BackgroundElement e = (BackgroundElement) selectedElement;
            addAppointment.friseur.setSelectedIndex(e.column);
            addAppointment.timeHours.setSelectedIndex(e.row / 4);
            addAppointment.timeMins.setSelectedIndex(e.row % 4);
        }
    }

    public void addBlockierung() {
        Button selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();
        int duration = ERE.mainScreen.kalender.getKalender().getSelectedDuration();

        int mins = ((GridElement) selectedElement).getRow() % 4;
        int hours = ((GridElement) selectedElement).getRow() / 4;

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, hours + 8);
        calendar.set(Calendar.MINUTE, mins * 15);

        NetworkData.BlockRequest request = new NetworkData.BlockRequest();
        request.duration = duration;
        request.start = calendar.getTime();
        request.friseur = ((GridElement) selectedElement).getColumn();
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

    public void removeBlock(BlockierungElement a) {
        NetworkData.RemoveBlockRequest request = new NetworkData.RemoveBlockRequest();
        request.id = a.getBlockierung().id;
        
        ERE.client.sendTCP(request);
    }
}
