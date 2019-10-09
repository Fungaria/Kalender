package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class BackgroundElement extends GridElement {

    public final int row;
    public final int column;
    
    public BackgroundElement(int row, int col, boolean top) {
        super(top ? new TopStyle() : new BottomStyle());
        this.row = row;
        this.column = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    private static final class TopStyle extends ButtonStyle {

        public TopStyle() {
            super.up = ERE.assets.createDrawable("kalender/grid/element_top");
            super.down = ERE.assets.createDrawable("kalender/grid/element_top_check");
            super.over = ERE.assets.createDrawable("kalender/grid/element_top_hover");
            super.checked = ERE.assets.createDrawable("kalender/grid/element_top_check");
        }
    }

    private static final class BottomStyle extends ButtonStyle {

        public BottomStyle() {
            super.up = ERE.assets.createDrawable("kalender/grid/element_bottom");
            super.down = ERE.assets.createDrawable("kalender/grid/element_bottom_check");
            super.over = ERE.assets.createDrawable("kalender/grid/element_bottom_hover");
            super.checked = ERE.assets.createDrawable("kalender/grid/element_bottom_check");
        }
    }
}
