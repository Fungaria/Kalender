/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.database.Vacation;
import de.fungistudii.kalender.main.generic.DaysGrid;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class VacationSelectBehavior implements DaysGrid.SelectBehavior {

    private final Drawable leftRed = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 10, ERE.assets.lightRed);
    private final Drawable rightRed = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 10, ERE.assets.lightRed);
    private final NinePatchSolid solidRed = new NinePatchSolid(ERE.assets.lightRed, 10);
    private final NinePatchSolid solidGreen = new NinePatchSolid(ERE.assets.lightGreen, 10);
    private final Drawable leftGreen = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 10, ERE.assets.mediumGreen);
    private final Drawable rightGreen = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 10, ERE.assets.mediumGreen);

    private Date beginDate;
    private Date endDate;

    private boolean isBegin = true;

    private int vacationId;
    private final int workerId;

    public VacationSelectBehavior(int workerId, int vacationId) {
        this.vacationId = vacationId;
        this.workerId = workerId;
    }

    public VacationSelectBehavior(int workerId, int vacationId, Date begin, Date end) {
        this(workerId, vacationId);
        this.beginDate = begin;
        this.endDate = end;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public int getVacationId() {
        return vacationId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    
    public void selectRange(DaysGrid.DayButton[] buttons, Date begin, Date end, Drawable l, Drawable c, Drawable r) {
        for (DaysGrid.DayButton button : buttons) {
            int c1 = DateUtil.compareDay(button.getDay(), begin);
            int c2 = DateUtil.compareDay(button.getDay(), end);

            if (c1 == 0 && (begin.before(end) || !isBegin)) {
                button.check(l);
            } else if (c2 == 0 && (begin.before(end) || isBegin)) {
                button.check(r);
            } else if (c1 > 0 && c2 < 0) {
                button.check(c);
            }
        }
    }

    public void setSelectBegin(boolean begin) {
        this.isBegin = begin;
    }

    public boolean isSelectBegin() {
        return isBegin;
    }

    public void updateSelection(DaysGrid.DayButton[] buttons) {
        for (DaysGrid.DayButton button : buttons) {
            button.uncheck();
        }

        Friseur friseur = ERE.data.root.friseure.values().stream().filter((fr) -> (fr.id == workerId)).findFirst().get();
        selectRange(buttons, beginDate, endDate, leftGreen, solidGreen, rightGreen);

        for (Vacation v : friseur.vacations.values()) {
            if (v.id != vacationId) {
                selectRange(buttons, v.start, v.end, leftRed, solidRed, rightRed);
            }
        }
    }

    @Override
    public void select(DaysGrid.DayButton[] buttons, Date date) {
        if (isBegin) {
            beginDate = date;
        } else {
            endDate = date;
        }
        updateSelection(buttons);
    }
}
