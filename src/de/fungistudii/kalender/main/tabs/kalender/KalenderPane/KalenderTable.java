package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import de.fungistudii.kalender.util.AnimationStack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.ContextMenu;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sreis
 */
public class KalenderTable extends Table {

    private final int NUM_ROWS = 16;
    public final int startTime = 8;

    private final SpriteDrawable timeFiller = ERE.assets.createDrawable("kalender/grid/time");

    private BGContext backgroundContext;
    private ContextMenu terminContext;

    private KalenderGrid old;
    private KalenderGrid nu;
    private ScrollPane pane;

    private AnimationStack container;

    int startRow;
    int col;
    boolean dragging;

    public KalenderTable(Calendar calendar) {
        old = new KalenderGrid(calendar.getTime());
        nu = new KalenderGrid(calendar.getTime());
        container = new AnimationStack(old);
        container.add(nu);
        pane = new ScrollPane(container);
        pane.setOverscroll(false, true);

        ScrollPaneFollower p2 = new ScrollPaneFollower(new TimeColumn(Align.left));
        p2.setScrollingDisabled(true, false);
        p2.setParent(pane);

        ScrollPaneFollower p1 = new ScrollPaneFollower(new TimeColumn(Align.right));
        p1.setScrollingDisabled(true, false);
        p1.setParent(pane);

        NamesHeader namesHeader = new NamesHeader(ERE.data.root.friseure.stream().map((s) -> s.name).toArray(String[]::new), old);
        ScrollPaneFollower n = new ScrollPaneFollower(namesHeader);
        n.setScrollingDisabled(false, true);
        n.setParent(pane);

        super.add();
        super.add(n).height(40);
        super.add();
        super.row();
        super.add(p1);
        super.add(pane).grow();
        super.add(p2);

        p2.setZIndex(1000);
        p1.setZIndex(1000);

        addListener(desktopListener);
        
        backgroundContext = new BGContext();
    }

    @Override
    public void layout() {
        super.layout();
        if (pane.getWidth() < 1000) {
            pane.setScrollingDisabled(false, false);
        } else {
            pane.setScrollingDisabled(true, false);
        }
    }

    public void updateCurrentTable() {
        old.reloadTable();
    }

    public void switchDate(Date date, int direction) {
        container.removeActor(nu);
        nu = new KalenderGrid(date);
        container.add(nu);
        nu.setVisible(false);

        if (direction == 0) {
            return;
        }
        
        nu.clearActions();
        old.clearActions();

        Gdx.app.postRunnable(() -> {
            nu.setVisible(true);
            nu.setPosition(direction * old.getWidth(), 0);
            old.addAction(Actions.sequence(Actions.moveBy(-direction * old.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2), Actions.hide(), Actions.run(() -> {
                KalenderGrid g = old;
                old = nu;
                nu = g;
            })));
            old.setPosition(0, 0);
            nu.addAction(Actions.moveBy(-direction * nu.getWidth(), 0, Cons.calendarTransitionTime, Interpolation.pow2));
        });
    }

    public Button getSelectedElement() {
        return old.getSelectedElement();
    }

    public int getSelectedDuration(){
        return old.getselectedDuration();
    }
    
    private ClickListener desktopListener = new ClickListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor hit = hit(x, y, true);
            pane.cancel();
            if (hit instanceof BackgroundElement && ((BackgroundElement) hit).isChecked()) {
                return true;
            }
            dragging = true;
            old.buttons.uncheckAll();
            old.buttons.setMaxCheckCount(100);
            if (hit instanceof BackgroundElement) {
                startRow = ((BackgroundElement) hit).getRow();
                col = ((BackgroundElement) hit).getColumn();
            }
            return true;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            old.buttons.setMaxCheckCount(1);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            dragging = false;
        }
        

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if(!dragging)
                return;
            Actor hit = hit(x, y, true);
            if (hit instanceof BackgroundElement) {
                for (GridElement element : old.columns[col].elements) {
                    int currentRow = ((BackgroundElement) hit).getRow();
                    int min = Math.min(currentRow, startRow);
                    int max = Math.max(currentRow, startRow);
                    if (element.getRow() >= min && element.getRow() <= max) {
                        ((Button) element).setChecked(true);
                    } else {
                        ((Button) element).setChecked(false);
                    }
                }
            }
        }
    };

    private ClickListener mobileListener = new ClickListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor hit = hit(x, y, true);
            if ((hit instanceof Button && ((Button) hit).isChecked()) || (hit.getParent() instanceof Button && ((Button) hit.getParent()).isChecked())) {
                pane.cancelTouchFocus();
                pane.cancel();
                old.buttons.setMaxCheckCount(100);
                return true;
            }
            return false;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y); //To change body of generated methods, choose Tools | Templates.
            old.buttons.setMaxCheckCount(1);
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Actor hit = hit(x, y, true);

            if (hit instanceof Button) {
                ((Button) hit).setChecked(true);
            } else if (hit.getParent() instanceof Button) {
                ((Button) hit.getParent()).setChecked(true);
            }
        }

    };

    private class BGContext extends ContextMenu {

        public BGContext() {
            super(KalenderTable.this, new String[]{
            "Termin erstellen", "Blockierung hinzufügen"
            }, new Runnable[]{()->{
                ERE.mainScreen.kalender.addTermin();
            },()->{
                ERE.mainScreen.kalender.addBlockierung();
            }});
            setShowOnRightClick(false);

            KalenderTable.super.addListener(new ClickListener(Input.Buttons.RIGHT) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (event.getTarget() instanceof BackgroundElement) {
                        show(x, y);
                    }
                }
            });
        }
    }

    private class TimeColumn extends Table {

        private final TextButton.TextButtonStyle dateStyle = new TextButton.TextButtonStyle(timeFiller, timeFiller, timeFiller, ERE.assets.fonts.createFont("robotoCondensed", 14));

        public TimeColumn(int align) {
            dateStyle.fontColor = ERE.assets.grey4;
            for (int i = 0; i < NUM_ROWS; i++) {
                TextButton element = new TextButton(startTime + i + "", dateStyle);
                element.getLabel().setAlignment((align | Align.top) & ~Align.bottom);
                element.getLabelCell().pad(6);
                super.add(element).minSize(0).growX().minWidth(Value.percentWidth(0.03f, KalenderTable.this));
                super.row();
            }
        }
    }

    private class NamesHeader extends Table {

        private final String[] names;
        private Label[] labels;
        private final Label.LabelStyle style = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 17), ERE.assets.grey4);

        public NamesHeader(String[] names, KalenderGrid inner) {
            this.names = names;
            super.center();
            this.labels = new Label[names.length];
            for (int i = 0; i < 7; i++) {
                labels[i] = new Label(names[i], style);
                labels[i].setAlignment(Align.center);
                add(labels[i]).grow().uniform().width(Value.percentWidth(1, inner.columns[0]));
                add(new Container()).width(Value.percentWidth(1, inner.getCells().get(1).getActor()));

                super.setClip(true);
            }
        }
    }

}
