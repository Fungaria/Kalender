package de.fungistudii.kalender.main.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;
import static de.fungistudii.kalender.Main.ERE;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public class BackgroundElement extends Image implements Pool.Poolable{

//    private static TopStyle topStyle = new TopStyle();
//    private static BottomStyle bottomStyle = new BottomStyle();
    
    private int row;
    private int friseur;
    private LocalDateTime start;
    
    private boolean checked;
    private boolean top;
    
    public BackgroundElement() {
        super();
    }
    
    public void init(LocalDateTime date, int row, boolean top){
        setFriseur(friseur);
        setRow(row);
        setStart(date);
        this.top = top;
        setChecked(false);
        //setStyle(top?topStyle:bottomStyle);
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public void setChecked(boolean checked){
        this.checked = checked;
        if(checked)
            setDrawable(top?topDown:bottomDown);
        else
            setDrawable(top?topUp:bottomUp);
    }
    
    public boolean isChecked(){
        return checked;
    }

    public LocalDateTime getStart() {
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
        //super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    //TODO
    public void dispose(){
        
    }
    
    Drawable topUp = ERE.assets.createNinePatchDrawable("kalender/grid/element_top", 2);
    Drawable topDown = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
    
    Drawable bottomUp = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
    Drawable bottomDown = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
    
//    private static final class TopStyle extends ButtonStyle {
//
//        public TopStyle() {
//            
////            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_hover", 2);
//            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_top_check", 2);
//        }
//    }
//
//    private static final class BottomStyle extends ButtonStyle {
//
//        public BottomStyle() {
//            super.up = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom", 2);
//            super.down = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
////            super.over = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_hover", 2);
//            super.checked = ERE.assets.createNinePatchDrawable("kalender/grid/element_bottom_check", 2);
//        }
//    }
}
