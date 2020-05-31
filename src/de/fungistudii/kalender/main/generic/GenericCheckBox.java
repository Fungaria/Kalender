package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class GenericCheckBox extends CheckBox{

    public GenericCheckBox(String text) {
        super(text, ERE.assets.checkBoxStyle);
        super.getImage().setScaling(Scaling.fillY);
        super.getImageCell().size(20).padRight(10);
        super.invalidate();
    }
    
}
