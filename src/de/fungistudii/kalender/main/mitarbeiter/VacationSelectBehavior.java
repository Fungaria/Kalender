/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.database.Vacation;
import de.fungistudii.kalender.main.generic.datepicker.DayButton;
import de.fungistudii.kalender.main.generic.datepicker.DaysGrid;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.time.LocalDate;

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

    private LocalDate beginDate;
    private LocalDate endDate;

    private boolean isBegin = true;

    private int vacationId;
    private final int workerId;

    public VacationSelectBehavior(int workerId, int vacationId) {
        this.vacationId = vacationId;
        this.workerId = workerId;
    }

    public VacationSelectBehavior(int workerId, int vacationId, LocalDate begin, LocalDate end) {
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

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void selectRange(DayButton[] buttons, LocalDate begin, LocalDate end, Drawable l, Drawable c, Drawable r) {
        for (DayButton button : buttons) {
            int c1 = button.getDay().compareTo(begin);
            int c2 = button.getDay().compareTo(end);

            if (c1 == 0 && (begin.isBefore(end) || !isBegin)) {
                button.check(l);
            } else if (c2 == 0 && (begin.isBefore(end) || isBegin)) {
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

    public void updateSelection(DayButton[] buttons) {
        for (DayButton button : buttons) {
            button.uncheck();
        }

        Employee friseur = ERE.data.root.friseure.values().stream().filter((fr) -> (fr.id == workerId)).findFirst().get();
        selectRange(buttons, beginDate, endDate, leftGreen, solidGreen, rightGreen);

        for (Vacation v : friseur.vacations.values()) {
            if (v.id != vacationId) {
//                selectRange(buttons, v.start, v.end, leftRed, solidRed, rightRed);
            }
        }
    }

    @Override
    public void select(DayButton[] buttons, LocalDate date) {
        if (isBegin) {
            beginDate = date;
        } else {
            endDate = date;
        }
        updateSelection(buttons);
    }
}
