/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.generic.datepicker.DatePicker;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BlockElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week.WeekSelectBehavior;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day.DayTable;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week.WeekTable;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.YearWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage {

    private Container contentTable;
    public final SidePanel sidePanel;

    public final DayTable dayTable;
    public final WeekTable weekTable;
    public KalenderTable currentTable;
    
    private boolean weekView = false;
    
    public KalenderPage() {
        contentTable = new Container();
        super.setBackground(new NinePatchSolid(ERE.assets.kalBG));

        sidePanel = new SidePanel((LocalDate date, int direction) -> {
            updateDate(date);
        });

        dayTable = new DayTable();
        weekTable = new WeekTable();
        
        currentTable = dayTable;
        contentTable.setActor(currentTable);
        
        add(sidePanel).minWidth(Cons.sideBarMinWidth).prefWidth(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.01f, this), Value.percentWidth(0.02f, this));

        updateDate(LocalDate.now());
    }

    public void toWeekView(int workerId){
        weekView = true;
        sidePanel.navigation.setSelectBehavior(new WeekSelectBehavior());
        weekTable.setFriseur(workerId);
        currentTable = weekTable;
        contentTable.setActor(currentTable); 
        currentTable.invalidateHierarchy();
        weekTable.setElementHeight(dayTable.getElementHeight().get());
        weekTable.viewWidget.setView(false);
    }
    
    public void toDayView(int dayOfWeek){
        weekView = false;
        sidePanel.navigation.setSelectBehavior(DatePicker.defaultSelectBehavior);
        currentTable = dayTable;
        contentTable.setActor(currentTable);
        currentTable.invalidateHierarchy();
        dayTable.setElementHeight(weekTable.getElementHeight().get());
        dayTable.viewWidget.setView(true);
    }

    public void updateCurrentTable() {
        currentTable.updateCurrentTable();
    }

    public KalenderTable getKalender() {
        return currentTable;
    }

    public void updateDate(LocalDate date) {
        currentTable.switchDate(date);
        sidePanel.navigation.setDate(date);
    }

    public void addTermin() {
        ERE.mainScreen.dialogManager.showAppointment();
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

    public void removeBlock(BlockElement a) {
        NetworkData.RemoveBlockRequest request = new NetworkData.RemoveBlockRequest();
        request.id = a.getBlock().id;
        
        ERE.client.sendTCP(request);
    }
}
