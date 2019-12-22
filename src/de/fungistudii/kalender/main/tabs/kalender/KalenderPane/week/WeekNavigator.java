package de.fungistudii.kalender.main.tabs.kalender.KalenderPane.week;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.KalenderTable.Navigator;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class WeekNavigator extends Navigator {

    private ImageButton next;
    private ImageButton previous;
    private SelectBox<Friseur> dropDown;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMMMM' 'yyyy");

    public WeekNavigator() {
        super();

        ImageButton.ImageButtonStyle nextStyle = new ImageButton.ImageButtonStyle();
        nextStyle.imageUp = ERE.assets.createDrawable("icons/arrow_side");
        nextStyle.up = ERE.assets.createNinePatchDrawable("generic/rounded_right", 12);
        nextStyle.down = ERE.assets.createNinePatchDrawable("generic/rounded_right", 12, ERE.assets.grey2);

        ImageButton.ImageButtonStyle prevStyle = new ImageButton.ImageButtonStyle();
        SpriteDrawable d = ERE.assets.createDrawable("icons/arrow_side");
        d.getSprite().flip(true, false);
        prevStyle.up = ERE.assets.createNinePatchDrawable("generic/rounded_left", 12);
        prevStyle.down = ERE.assets.createNinePatchDrawable("generic/rounded_left", 12, ERE.assets.grey2);
        prevStyle.imageUp = d;

        SelectBox.SelectBoxStyle sbs = new SelectBox.SelectBoxStyle();
        sbs.background = ERE.assets.createNinePatchDrawable("generic/middle", 12);
        sbs.backgroundOpen = ERE.assets.createNinePatchDrawable("generic/middle", 12, ERE.assets.grey2);
        sbs.font = ERE.assets.fonts.createFont("roboto", 16);
        sbs.fontColor = ERE.assets.grey6;
        sbs.listStyle = new List.ListStyle(sbs.font, ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey3));
        sbs.scrollStyle = new ScrollPane.ScrollPaneStyle();
        sbs.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);

        next = new ImageButton(nextStyle);
        previous = new ImageButton(prevStyle);
        dropDown = new SelectBox<Friseur>(sbs);
        dropDown.setItems(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));

        super.add(previous).minSize(0).maxWidth(Value.percentHeight(1, this));
        super.add(dropDown).grow().minHeight(0);
        super.add(next).minSize(0).maxWidth(Value.percentHeight(1, this));

        dropDown.setAlignment(Align.center);

        next.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.calendar.add(Calendar.DATE, 7);
                ERE.mainScreen.kalender.updateDate(1);
            }
        });
        previous.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.kalender.calendar.add(Calendar.DATE, -7);
                ERE.mainScreen.kalender.updateDate(-1);
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

    public void setDate(Date time) {
    }
}
