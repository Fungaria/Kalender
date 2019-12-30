package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.utils.Pool;
import static de.fungistudii.kalender.Main.ERE;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BackgroundElement extends Button implements Pool.Poolable{

    private static TopStyle topStyle = new TopStyle();
    private static BottomStyle bottomStyle = new BottomStyle();
    
    private int row;
    private int friseur;
    private Date start;
    
    public BackgroundElement() {
        super();
    }
    
    public void init(Date date, int row, boolean top){
        setFriseur(friseur);
        setRow(row);
        setStart(date);
        setStyle(top?topStyle:bottomStyle);
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setFriseur(int friseur) {
        this.friseur = friseur;
    }

    public int getFriseur() {
        return friseur;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }
    
    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    //TODO
    public void dispose(){
        
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
