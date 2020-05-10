/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.generic.datepicker;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import static de.fungistudii.kalender.Main.ERE;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class DefaultHoverBehavior implements DaysGrid.SelectBehavior {

    private final Drawable drawable = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, ERE.assets.grey3);

    @Override
    public void select(DayButton[] buttons, LocalDate date) {
        for (DayButton button : buttons) {
            if (button.getDay().isEqual(date)) {
                button.hover(drawable);
            } else {
                button.unhover();
            }
        }
    }
}
