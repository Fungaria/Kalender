package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import java.util.function.Consumer;

/**
 *
 * @author sreis
 */
public class WorkerHeader extends SelectBox<Employee> {

    public WorkerHeader(Consumer<Integer> callback) {
        super(new WHStyle());
        super.setItems(ERE.data.root.friseure.values().stream().toArray(Employee[]::new));
        if (getStyle().background != null) {
            super.getStyle().background.setLeftWidth(10);
            super.getStyle().backgroundOpen.setLeftWidth(10);
        }
        super.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getStage().setKeyboardFocus(WorkerHeader.this);
                return false;
            }
        });
        super.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                callback.accept(getSelected().id);
            }
        });
    }

    private static final class WHStyle extends SelectBoxStyle {
        public WHStyle() {
            super.background = null;
            super.backgroundOpen = null;
            super.font = ERE.assets.fonts.createFont("roboto", 22, Fonts.MEDIUM);
            super.fontColor = Color.BLACK;
            super.listStyle = new List.ListStyle(ERE.assets.fonts.createFont("roboto", 15, Fonts.REGULAR), ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey3));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
        }
    }
}
