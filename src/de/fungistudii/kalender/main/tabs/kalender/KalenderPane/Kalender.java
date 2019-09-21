package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class Kalender extends Table {

    private static final int NUM_ROWS = 16, NUM_COLS = 7;
    private static final float spacing = 0.1f;

    public KalenderElement[][] elements = new KalenderElement[NUM_COLS][NUM_ROWS*4];
    public Table[] groups = new Table[NUM_COLS * 2 + 1];

    private final SpriteDrawable timeFiller = ERE.assets.createDrawable("kalender/grid/time");
    private final SpriteDrawable topElement = ERE.assets.createDrawable("kalender/grid/element_top");
    private final SpriteDrawable bottomElement = ERE.assets.createDrawable("kalender/grid/element_bottom");
    private final SpriteDrawable topFiller = ERE.assets.createDrawable("kalender/grid/filler_top");
    private final SpriteDrawable bottomFiller = ERE.assets.createDrawable("kalender/grid/filler_bottom");

    public Kalender() {
        for (int i = 0; i < NUM_COLS * 2 + 1; i++) {
            groups[i] = new Table();
            add(groups[i]);
            groups[i].setZIndex(i% 2);
        }

        for (int i = 0; i < NUM_ROWS * 4; i++) {
            for (int j = 1; j < NUM_COLS * 2; j++) {
                if (j % 2 == 1) {
                    int index = (j - 1) / 2;
                    if (i % 4 == 0) {
                        elements[index][i] = new KalenderElement(i, index, true);
                        groups[j].add(elements[index][i]).grow().minSize(0);
                        groups[j].row();
                    } else {
                        elements[index][i] = new KalenderElement(i, index, false);
                        groups[j].add(elements[index][i]).grow().minSize(0);
                        groups[j].row();
                    }
                } else {
                    if (i % 4 == 0) {
                        Image img = new Image(topFiller);
                        img.setScaling(Scaling.fillY);
                        groups[j].add(img).grow().prefWidth(Value.percentWidth(spacing, elements[0][0]));
                        groups[j].row();
                    } else {
                        Image img = new Image(bottomFiller);
                        img.setScaling(Scaling.fillY);
                        groups[j].add(img).grow().prefWidth(Value.percentWidth(spacing, elements[0][0]));
                        groups[j].row();
                    }
                }
            }
        }
        
        initTimeColumn(groups[0], Align.right);
        initTimeColumn(groups[14], Align.left);
        addTermin(2, 5, 14);
    }

    TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.robotoCondensedRegular);
    private void initTimeColumn(Table table, int align) {
        dateStyle.fontColor = ERE.assets.grey4;
        for (int i = 0; i < NUM_ROWS; i++) {
            TextButton element = new TextButton(7+i+"", dateStyle);
            element.getLabel().setFontScale(0.65f);
            element.getLabel().setAlignment((align | Align.top) & ~Align.bottom);
            element.getLabelCell().pad(6);
            table.add(element).minSize(0).height(Value.percentHeight(4, elements[0][0]));
            table.row();
        }
    }

    public void addTermin(int col, int start, int end) {
        for (int i = start; i < end; i++) {
            elements[col][i].remove();
            getCells().removeValue(null, true);
        }
        TerminElement element = new TerminElement();
        groups[col * 2 + 1].row();
        groups[col * 2 + 1].add(element).height(Value.percentHeight(end - start, elements[1][1])).grow();
    }
    
    
}
