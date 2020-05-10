/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender;

import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.TabPage;
import de.fungistudii.kalender.main.generic.datepicker.DefaultHoverBehavior;
import de.fungistudii.kalender.main.generic.datepicker.DefaultSelectBehavior;
import de.fungistudii.kalender.main.generic.datepicker.WeekHoverBehavior;
import de.fungistudii.kalender.main.kalender.KalenderPane.BlockElement;
import de.fungistudii.kalender.main.generic.datepicker.WeekSelectBehavior;
import de.fungistudii.kalender.main.kalender.KalenderPane.day.DayTable;
import de.fungistudii.kalender.main.kalender.KalenderPane.week.WeekTable;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage{

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
        
        add(sidePanel).width(280).growY();
        add(contentTable).minSize(0).grow().pad(20, 10, 5, 10);

        updateDate(LocalDate.now());
    }

    public void toWeekView(int workerId){
        weekView = true;
        sidePanel.navigation.setSelectBehavior(new WeekSelectBehavior());
        sidePanel.navigation.setHoverBehavior(new WeekHoverBehavior());
        weekTable.setFriseur(workerId);
        currentTable = weekTable;
        contentTable.setActor(currentTable); 
        currentTable.invalidateHierarchy();
        weekTable.setElementHeight(dayTable.getElementHeight().get());
        weekTable.viewWidget.setView(false);
    }
    
    public void toDayView(int dayOfWeek){
        weekView = false; 
        sidePanel.navigation.setSelectBehavior(new DefaultSelectBehavior());
        sidePanel.navigation.setHoverBehavior(new DefaultHoverBehavior());
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
