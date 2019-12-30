package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.FitImage;

/**
 *
 * @author sreis
 */
public class IconTextButton extends Button{ 

    public IconTextButton(String text, String icon, TextButtonStyle style) {
        Label label = new Label(text, new Label.LabelStyle(style.font, style.fontColor));
        Image image = new FitImage(ERE.assets.createDrawable("icons/"+icon, ERE.assets.grey4));
        
        image.setScaling(Scaling.fillY);
        image.setAlign(Align.left);
        
        super.add(image).left().minSize(0).pad(2);
        super.add(label).grow().padLeft(4);
        
        setStyle(style);
    }
    
}
