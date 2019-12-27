package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class ConfirmationDialog extends GenericDialog{
    public ConfirmationDialog(String title, String msg) {
        super(title);
        super.setMainActor(new Label(msg, new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT), Color.BLACK))).padLeft(Cons.dialogOuterPadding);
    }
}
