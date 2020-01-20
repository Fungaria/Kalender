package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Blockierung;
import de.fungistudii.kalender.util.CompositeDrawable;
import de.fungistudii.kalender.util.Fonts;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class BlockElement extends GridElement {

    private Blockierung block;
    
    private BlockDrawable up;
    private BlockDrawable down;
    
    private Table table;
    
    private Cell handleCell;
    
    private Label title;
    
    public BlockElement(Blockierung block) {
        super();
        this.block = block;
        System.out.println(block.duration);
        
        up = new BlockDrawable(ERE.assets.createDrawable("kalender/grid/block_bg_r"));
        up.setAlpha(0.25f);
        down = new BlockDrawable(ERE.assets.createDrawable("kalender/grid/block_bg_check_r"));
        down.setAlpha(0.55f);
        
        title = new Label(block.msg, new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 15, Fonts.MEDIUM), ERE.assets.grey7));
        
        table = new Table();
        handle = new Image(ERE.assets.createDrawable("icons/expand"));
        handle.setScaling(Scaling.fillY);
        table.add(title).expandX().left().padLeft(10).padTop(5);
        table.row();
        handleCell = table.add().expand().bottom().fillX();
        table.setBackground(up);
        
        super.add(table).grow();
        super.setClip(true);
    }
    
    boolean faded;
    @Override
    public void fadeOut(){
        faded = true;
        setFocused(false);
        setTouchable(Touchable.disabled);
    }

    @Override
    public Object serialize(){
        return block;
    }
    
    @Override
    public GridElement copy() {
        BlockElement blockActor = new BlockElement(this.block);
        blockActor.setWidth(getWidth());
        blockActor.setHeight(getHeight());
        return blockActor;
    }

    @Override
    public void setFocused(boolean foc) {
        if(foc){
            table.setBackground(down);
            handleCell.setActor(handle);
            table.invalidate();
        }else{
            table.setBackground(up);
            handleCell.clearActor();
            table.invalidate();
        }
    }

    @Override
    public LocalDateTime getStart() {
        return block.start;
    }

    @Override
    public int getFriseur() {
        return block.friseur;
    }

    @Override
    public int getSpan() {
        return block.duration;
    }

    @Override
    public void setFriseur(int friseur) {
        block.friseur = friseur;
    }

    @Override
    public void setStart(LocalDateTime start) {
        block.start = start;
    }

    @Override
    public void setSpan(int span) {
        block.duration = span;
    }

    public Blockierung getBlock() {
        return block;
    }

    private static class BlockDrawable extends CompositeDrawable {
        private SpriteDrawable background;
        
        public BlockDrawable(SpriteDrawable background) {
            this.background = background;
        }

        public void setBackground(SpriteDrawable drawable) {
            this.background = drawable;
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            float bgh = background.getSprite().getRegionHeight() * width / background.getSprite().getRegionWidth();
            float n = height / (bgh);
            for (int i = 0; i < n; i++) {
                background.draw(batch, x, y + (i * bgh), width, bgh);
            }
            super.draw(batch, x, y, width, height);
        }

        private void setAlpha(float f) {
            background.getSprite().setColor(1, 1, 1, f);
        }
    }
}
