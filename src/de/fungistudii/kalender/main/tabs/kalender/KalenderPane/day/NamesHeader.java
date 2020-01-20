package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.day;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderGrid;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.Header;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class NamesHeader extends Header {

    private final TextButton[] labels;
    private final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

    public NamesHeader() {
        super.center();
        
        style.font = ERE.assets.fonts.createFont("roboto", 19);
        style.fontColor = ERE.assets.grey6;
        style.over = new NinePatchSolid(ERE.assets.grey1);
        style.down = new NinePatchSolid(ERE.assets.grey2);

        this.labels = new TextButton[ERE.data.root.friseure.size()];
        
        SpriteDrawable vs = ERE.assets.createDrawable("generic/vertical_separator", ERE.assets.grey2);
        add(new Image(vs)).height(Value.percentHeight(0.5f, this)).bottom();
        for (int i = 0; i < 7; i++) {
            labels[i] = new TextButton(ERE.data.root.friseure.get(i).name, style);
            labels[i].getLabel().setAlignment(Align.center);
            add(labels[i]).grow();
            add(new Image(vs)).height(Value.percentHeight(0.5f, this)).bottom();
            
            final int workerId = i;
            labels[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ERE.mainScreen.kalender.toWeekView(workerId);
                }
            });
        }
    }
    
    @Override
    public void updateLayout(KalenderGrid grid){
        int i=0;
        for (Cell cell : grid.getCells()) {
            super.getCells().get(i).width(Value.percentWidth(1, grid.getCells().get(i).getActor()));
            i++;
        }
    }

    @Override
    public void setDate(LocalDate time) {
    }
}
