package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class KalenderElement extends ImageButton{
    public final int row;
    public final int column;
    
    public KalenderElement(int row, int col, boolean top) {
        super(top?new TopStyle():new BottomStyle());
        this.row = row;
        this.column = col;
    }
    
    private static final class TopStyle extends ImageButtonStyle{
        public TopStyle() {
            super.up = ERE.assets.createDrawable("kalender/grid/element_top");
            super.down = ERE.assets.createDrawable("kalender/grid/element_top_check");
            super.over = ERE.assets.createDrawable("kalender/grid/element_top_hover");
            super.checked = ERE.assets.createDrawable("kalender/grid/element_top_check");
        }
    }
    
    private static final class BottomStyle extends ImageButtonStyle{
        public BottomStyle() {
            super.up = ERE.assets.createDrawable("kalender/grid/element_bottom");
            super.down = ERE.assets.createDrawable("kalender/grid/element_bottom_check");
            super.over = ERE.assets.createDrawable("kalender/grid/element_bottom_hover");
            super.checked = ERE.assets.createDrawable("kalender/grid/element_bottom_check");
        }
    }
}
