/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender;

import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.main.generic.DatePicker;
import de.fungistudii.kalender.main.tabs.TabPage;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.AddBlockDialog;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BlockierungElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.StornoDialog;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day.DayTable;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week.WeekTable;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddAppointmentDialog;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class KalenderPage extends TabPage {

    private Container contentTable;
    public final SidePanel sidePanel;

    public Calendar calendar = Calendar.getInstance();
    private Date currentDate = new Date();

    public final AddAppointmentDialog addAppointment = new AddAppointmentDialog();
    public final StornoDialog stornoDialog = new StornoDialog();
    public final AddBlockDialog blockDialog = new AddBlockDialog();

    public final DayTable dayTable;
    public final WeekTable weekTable;
    public KalenderTable currentTable;
    
    private boolean weekView = false;
    
    public KalenderPage() {
        contentTable = new Container();
        super.setBackground(new NinePatchSolid(ERE.assets.kalBG));

        sidePanel = new SidePanel((Date date, int direction) -> {
            if(weekView){
                int dir = DateUtil.compareWeek(date, currentDate);
                //if date in the same week is selected, do nothing
                if(dir != 0){
                    calendar.setTime(date);
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    this.currentDate = calendar.getTime();
                    updateDate(dir);
                }
            }else{
                calendar.setTime(date);
                updateDate(direction);
                this.currentDate = date;
            }
        });

        dayTable = new DayTable(calendar.getTime());
        weekTable = new WeekTable(calendar.getTime());
        
        currentTable = dayTable;
        contentTable.setActor(currentTable);
        
        add(sidePanel).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY();
        add(contentTable).minSize(0).grow().pad(Value.percentHeight(0.03f, this), Value.percentWidth(0.02f, this), Value.percentWidth(0.01f, this), Value.percentWidth(0.02f, this));

        updateDate(0);
    }

    public void toWeekView(int workerId){
        weekView = true;
        sidePanel.navigation.setSelectBehavior(new DatePicker.WeekSelectBehavior());
        weekTable.setFriseur(workerId);
        currentTable = weekTable;
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        updateDate(0);
        contentTable.setActor(currentTable); 
        currentTable.invalidateHierarchy();
        weekTable.setElementHeight(dayTable.getElementHeight());
        weekTable.viewWidget.setView(false);
    }
    
    public void toDayView(int dayOfWeek){
        weekView = false;
        sidePanel.navigation.setSelectBehavior(DatePicker.defaultSelectBehavior);
        currentTable = dayTable;
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        updateDate(0);
        contentTable.setActor(currentTable);
        currentTable.invalidateHierarchy();
        dayTable.setElementHeight(weekTable.getElementHeight());
        dayTable.viewWidget.setView(true);
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
        if(DateUtil.compareDay(currentDate, sidePanel.navigation.getDate())!=0)
            sidePanel.navigation.setDate(currentDate);
    }

    public void addTermin() {
        Button selectedElement = ERE.mainScreen.kalender.getKalender().getSelectedElement();

        addAppointment.show(ERE.mainScreen.stage, sidePanel.navigation.getDate());
        if (selectedElement instanceof BackgroundElement) {
            BackgroundElement e = (BackgroundElement) selectedElement;
            addAppointment.friseur.setSelectedIndex(e.getFriseur());
            //TODO correct default Values
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        System.out.println("");
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
