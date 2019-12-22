package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.utils.Pool;
import static de.fungistudii.kalender.Main.ERE;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BackgroundElement extends GridElement implements Pool.Poolable{

    public Date date;
    public int friseur;
    
    private static TopStyle topStyle = new TopStyle();
    private static BottomStyle bottomStyle = new BottomStyle();
    
    public BackgroundElement() {
    }
    
    public void init(Date date, int friseur, boolean top){
        this.date = date;
        this.friseur = friseur;
        setStyle(top?topStyle:bottomStyle);
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

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
        super.dispose();
    }
    
    private static final class TopStyle extends ButtonStyle {

        public TopStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
            super.down = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
//            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_hover", 2);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
        }
    }

    private static final class BottomStyle extends ButtonStyle {

        public BottomStyle() {
            super.up = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
            super.down = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
//            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_hover", 2);
            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
        }
    }
}
