package de.fungistudii.kalender.main.tabpane;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.fungistudii.kalender.Main;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.DrawableSolid;

/**
 *
 * @author sreis
 */
public class TabButton extends TextButton{
    public TabButton(String text) {
        super(text, new TabButtonStyle());
    }
    
    private static class TabButtonStyle extends TextButtonStyle{
        public TabButtonStyle() {
            super.down = new DrawableSolid(ERE.assets.tabGreyer, 10);
            super.checked = new DrawableSolid(ERE.assets.tabGreyer, 10);
            super.up = new DrawableSolid(ERE.assets.tabGrey, 10);
            super.font = Main.ERE.assets.fonts.createFont("roboto", 14);
        }
    }
}
