package de.fungistudii.kalender.main.kalender.KalenderPane.week;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Friseur;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.kalender.KalenderPane.KalenderTable.Navigator;
import de.fungistudii.kalender.util.YearWeek;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class WeekNavigator extends Navigator {

    private ImageButton next;
    private ImageButton previous;
    private SelectBox<Friseur> dropDown;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");

    public WeekNavigator(WeekTable weekTable) {
        super();

        ImageButton.ImageButtonStyle nextStyle = new ImageButton.ImageButtonStyle();
        nextStyle.imageUp = ERE.assets.createDrawable("icons/arrow_side");
        nextStyle.up = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12);
        nextStyle.down = ERE.assets.createNinePatchDrawable("rounded/outline_right", 12, ERE.assets.grey2);

        ImageButton.ImageButtonStyle prevStyle = new ImageButton.ImageButtonStyle();
        SpriteDrawable d = ERE.assets.createDrawable("icons/arrow_side");
        d.getSprite().flip(true, false);
        prevStyle.up = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12);
        prevStyle.down = ERE.assets.createNinePatchDrawable("rounded/outline_left", 12, ERE.assets.grey2);
        prevStyle.imageUp = d;

        next = new ImageButton(nextStyle);
        previous = new ImageButton(prevStyle);
        dropDown = new GenericDropDown<Friseur>(null, ERE.assets.createNinePatchDrawable("rounded/outline_middle", 12), ERE.assets.createNinePatchDrawable("rounded/outline_middle", 12), ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));

        super.add(previous).minSize(0).maxWidth(Value.percentHeight(1, this));
        super.add(dropDown).grow().minHeight(0);
        super.add(next).minSize(0).maxWidth(Value.percentHeight(1, this));

        dropDown.setAlignment(Align.center);

        next.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.updateDate(weekTable.getCurrentDate().plusWeeks(1));
            }
        });
        previous.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.updateDate(weekTable.getCurrentDate().minusWeeks(1));
            }
        });
        dropDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ERE.mainScreen.kalender.toWeekView(dropDown.getSelected().id);
            }
        });
        
    }
    
    public void setWorker(int id){
        dropDown.setSelectedIndex(id);
    }

    @Override
    public void setDate(LocalDate time) {
    }
}
