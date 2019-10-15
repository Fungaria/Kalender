package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import static de.fungistudii.kalender.Main.ERE;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BackgroundElement extends GridElement {

    public final Date date;
    public final int friseur;
    
    public BackgroundElement(Date date, int friseur, boolean top) {
        super(top ? new TopStyle() : new BottomStyle());
        this.date = date;
        this.friseur = friseur;
    }

    @Override
    public Date getStart() {
        return date;
    }

    @Override
    public int getFriseur() {
        return friseur;
    }

    @Override
    public int getSpan() {
        return 1;
    }

    private static final class TopStyle extends ButtonStyle {

        public TopStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
            super.down = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_hover", 2);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
        }
    }

    private static final class BottomStyle extends ButtonStyle {

        public BottomStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
            super.down = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_hover", 2);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
        }
    }
}
