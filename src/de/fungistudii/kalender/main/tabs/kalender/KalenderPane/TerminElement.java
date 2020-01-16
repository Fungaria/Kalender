package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Customer;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.Fonts;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class TerminElement extends GridElement {

    private Label nameLabel;
    private Label leistungLabel;
    private Label timeLabel;

    private Cell leistCell;
    private Cell timeCell;
    private Cell handleCell;

    private Termin termin;

    private Color upColor;
    private Color downColor;

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");
    
    private static final Color[] colors = new Color[]{
        new Color(118 / 255f, 207 / 255f, 145 / 255f, 1),
        new Color(154 / 255f, 183 / 255f, 236 / 255f, 1)
    };

    private NinePatchDrawable up;

    public TerminElement(Termin termin) {
        super();
        super.align(Align.topLeft);
        super.defaults().space(Value.percentWidth(0.02f, this));
        this.termin = termin;

        this.upColor = colors[termin.service];
        float[] hsv = new float[3];
        upColor.toHsv(hsv);
        hsv[2] *= 0.8;
        this.downColor = new Color(Color.WHITE).fromHsv(hsv);
        up = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 10, upColor);
        super.setBackground(up);

        Customer kunde = ERE.data.root.kunden.get(termin.kundenid);
        Service service = ERE.data.root.services.get(termin.service);

        Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14, Fonts.MEDIUM), Color.WHITE);
        Label.LabelStyle serviceStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13, Fonts.REGULAR), Color.WHITE);
        Label.LabelStyle timeStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 13), Color.WHITE);
        this.nameLabel = new Label(kunde.vorname + " " + kunde.name, nameStyle);
        this.leistungLabel = new Label(service.name, serviceStyle);
        this.timeLabel = new Label("", timeStyle);
        this.handle = new Image(ERE.assets.createDrawable("icons/expand"));
        handle.setScaling(Scaling.fillY);

        nameLabel.setTouchable(Touchable.disabled);
        leistungLabel.setTouchable(Touchable.disabled);
        timeLabel.setTouchable(Touchable.disabled);

        super.add(nameLabel).top().left();
        super.row();
        leistCell = super.add(leistungLabel).fillY().left().top();
        super.row();
        timeCell = super.add(timeLabel).expand().bottom().right();
        super.row();
        handleCell = super.add().growX();
        super.setClip(true);

        this.updateLabelText();
    }

    @Override
    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if (getBackground() == null) {
            return;
        }
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        getBackground().draw(batch, x, y, getWidth() - Cons.appointmentPad, getHeight());
    }

    @Override
    public void setFocused(boolean foc) {
        if (foc) {
            up.getPatch().setColor(downColor);
            handleCell.setActor(handle);
            super.padBottom(0);
            invalidate();
        } else {
            up.getPatch().setColor(upColor);
            handleCell.clearActor();
            super.padBottom(5);
            invalidate();
        }
    }

    boolean faded;

    @Override
    public void fadeOut() {
        faded = true;
        Color n = upColor.cpy();
        n.a = 0.5f;
        setFocused(false);
        up.getPatch().setColor(n);
        setTouchable(Touchable.disabled);
    }

    @Override
    public Object serialize() {
        return termin;
    }

    @Override
    public void act(float delta) {
        if (!faded) {
            super.act(delta);
        }
    }

    public void fadeIn() {
    }

    @Override
    public Date getStart() {
        return new Date(termin.start.getTime());
    }

    @Override
    public int getFriseur() {
        return termin.friseur;
    }

    @Override
    public int getSpan() {
        return termin.dauer / 15;
    }

    @Override
    public void dispose() {
        super.remove();
    }

    @Override
    public void layout() {
        super.layout();
        if (getHeight() < 80 && leistCell.getActor() != null) {
            leistungLabel.remove();
        } else if (getHeight() < 40 && timeCell.getActor() != null) {
            timeLabel.remove();
        } else if (getHeight() > 80) {
            if (leistCell.getActor() == null) {
                leistCell.setActor(leistungLabel);
            }
            if (timeCell.getActor() == null) {
                timeCell.setActor(timeLabel);
            }
        }
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public Color getBackgroundColor() {
        return upColor;
    }

    @Override
    public TerminElement copy() {
        TerminElement element = new TerminElement(termin);
        element.setWidth(getWidth());
        element.setHeight(getHeight());
        return element;
    }

    @Override
    public void setFriseur(int friseur) {
        termin.friseur = friseur;
    }

    @Override
    public void setStart(Date start) {
        termin.start.setTime(start.getTime());
        updateLabelText();
    }

    private void updateLabelText() {
        String startTime = timeFormat.format(termin.start);
        String endTime = timeFormat.format(DateUtil.add(termin.start, Calendar.MINUTE, termin.dauer));
        timeLabel.setText(startTime + "-" + endTime);
    }

    @Override
    public void setSpan(int span) {
        termin.dauer = span * 15;
        updateLabelText();
    }

    private static class BGDrawable extends NinePatchDrawable {

        private Drawable separator;

        public BGDrawable(Color color) {
            super(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 13, color));
            separator = ERE.assets.createDrawable("generic/horizontal_separator");
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            super.draw(batch, x, y, width, height); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
