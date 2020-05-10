package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.datepicker.DayButton;
import de.fungistudii.kalender.main.generic.datepicker.DaysGrid;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

/**
 *
 * @author arein
 */
public class WeekSelectBehavior implements DaysGrid.SelectBehavior {

    private final NinePatchSolid solid;
    private final NinePatchDrawable left;
    private final NinePatchDrawable right;
    private final NinePatchDrawable def;

    public WeekSelectBehavior() {
        this(
            new NinePatchSolid(ERE.assets.lightGreen, 10),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 12, ERE.assets.mediumGreen),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 12, ERE.assets.mediumGreen),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled", 12, Color.CLEAR)
        );
    }

    public WeekSelectBehavior(NinePatchSolid solid, NinePatchDrawable left, NinePatchDrawable right, NinePatchDrawable def) {
        this.solid = solid;
        this.left = left;
        this.right = right;
        this.def = def;
    }

    @Override
    public void select(DayButton[] buttons, LocalDate date) {
        for (DayButton button : buttons) {
            if (button.getDay().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)) {
                if (button.getDay().getDayOfWeek() == DayOfWeek.MONDAY) {
                    button.check(left);
                } else if (button.getDay().getDayOfWeek() == DayOfWeek.SUNDAY) {
                    button.check(right);
                } else {
                    button.check(solid);
                }
            } else {
                button.uncheck();
            }
        }
    }
}
