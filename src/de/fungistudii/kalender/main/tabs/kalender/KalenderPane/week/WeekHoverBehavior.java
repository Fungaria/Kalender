package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import com.badlogic.gdx.graphics.Color;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.NinePatchSolid;

/**
 *
 * @author sreis
 */
public class WeekHoverBehavior extends WeekSelectBehavior{

    public WeekHoverBehavior() {
        super(
            new NinePatchSolid(ERE.assets.lightGreen, 10),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 12, ERE.assets.mediumGreen),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 12, ERE.assets.mediumGreen),
            ERE.assets.createNinePatchDrawable("generic/rounded_filled", 12, Color.CLEAR)
        );
    }
    
}
