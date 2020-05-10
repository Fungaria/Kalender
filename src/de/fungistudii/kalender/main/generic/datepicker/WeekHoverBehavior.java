package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.datepicker.DayButton;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

/**
 *
 * @author sreis
 */
public class WeekHoverBehavior extends WeekSelectBehavior{

    private final NinePatchSolid solid;
    private final NinePatchDrawable left;
    private final NinePatchDrawable right;

    public WeekHoverBehavior() {
        this(
            new NinePatchSolid(ERE.assets.grey3, 10),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 12, ERE.assets.grey3),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 12, ERE.assets.grey3)
        );
    }

    public WeekHoverBehavior(NinePatchSolid solid, NinePatchDrawable left, NinePatchDrawable right) {
        this.solid = solid;
        this.left = left;
        this.right = right;
    }

    @Override
    public void select(DayButton[] buttons, LocalDate date) {
        for (DayButton button : buttons) {
            if (button.getDay().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)) {
                if (button.getDay().getDayOfWeek() == DayOfWeek.MONDAY) {
                    button.hover(left);
                } else if (button.getDay().getDayOfWeek() == DayOfWeek.SUNDAY) {
                    button.hover(right);
                } else {
                    button.hover(solid);
                }
            } else {
                button.unhover();
            }
        }
    }
    
}
