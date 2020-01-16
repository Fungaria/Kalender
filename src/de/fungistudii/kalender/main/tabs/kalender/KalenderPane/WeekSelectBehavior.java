package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.DaysGrid;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.util.Calendar;
import java.util.Date;

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
        solid = new NinePatchSolid(ERE.assets.lightGreen, 10);
        left =ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 12, ERE.assets.mediumGreen);
        right = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 12, ERE.assets.mediumGreen);
        def = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 12, Color.CLEAR);
    }

    @Override
    public void select(DaysGrid.DayButton[] buttons, Date date) {
        for (DaysGrid.DayButton button : buttons) {
            if (DateUtil.compareWeek(date, button.getDay()) == 0) {
                if (DateUtil.getDayOfWeek(button.getDay()) == Calendar.MONDAY) {
                    button.check(left);
                } else if (DateUtil.getDayOfWeek(button.getDay()) == Calendar.SUNDAY) {
                    button.check(right);
                } else {
                    button.check(solid);
                }
            } else {
                button.uncheck();
            }
        }
    }
};
