package de.fungistudii.kalender.main.tabs.kalender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.GridElement;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.value.ValueUtil;
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

    private Termin termin;
    
    private Color color;

    public TerminElement(Termin termin, Color color) {
        super();
        super.align(Align.topLeft);
        super.padTop(0);
        super.defaults().space(Value.percentWidth(0.02f, this));

        super.setStyle(new DefaultStyle(new Color(color)));
        this.color = color;
        this.termin = termin;

        Kunde kunde = ERE.data.root.kunden.get(termin.kundenid);
        Service service = ERE.data.root.services.get(termin.service);

        String startTime = timeFormat.format(termin.start);
        String endTime = timeFormat.format(DateUtil.add(termin.start, Calendar.MINUTE, termin.dauer));

        Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13, Fonts.BOLD), Color.WHITE);
        Label.LabelStyle serviceStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 12), Color.WHITE);
        Label.LabelStyle timeStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 12), Color.WHITE);
        this.nameLabel = new Label(kunde.vorname + " " + kunde.name, nameStyle);
        this.leistungLabel = new Label(service.name, serviceStyle);
        this.timeLabel = new Label(startTime + " - " + endTime, timeStyle);

        nameLabel.setTouchable(Touchable.disabled);
        leistungLabel.setTouchable(Touchable.disabled);
        timeLabel.setTouchable(Touchable.disabled);

        super.add(nameLabel).fillY().top().left().padTop(ValueUtil.percentMinHeight(0.06f, this, 20)).padLeft(5);
        super.row();
        leistCell = super.add(leistungLabel).fillY().left().padLeft(5);
        super.row();
        timeCell = super.add(timeLabel).expand().bottom().right().padRight(5).padBottom(5);
        super.setClip(true);
    }

    boolean faded;
    
    public void fadeOut(){
        faded = true;
        Color n = color.cpy();
        n.a = 0.5f;
        super.setChecked(false);
        getStyle().up = (((NinePatchDrawable)getBackground()).tint(n));
        setTouchable(Touchable.disabled);
    }

    @Override
    public void act(float delta) {
        if(!faded)
            super.act(delta);
    }
    
    
    
    public void fadeIn(){
    }
    
    @Override
    public Date getStart() {
        return termin.start;
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

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    public Color getBackgroundColor() {
        return color;
    }

    private static class DefaultStyle extends ButtonStyle {
        public DefaultStyle(Color color) {
            float[] hsv = new float[3];
            color.toHsv(hsv);
            super.up = new TerminElement.BGDrawable(color);
            hsv[2] = 0.9f*hsv[2];
            super.over = new TerminElement.BGDrawable(color.fromHsv(hsv));
            hsv[2] = 0.9f*hsv[2];
            super.down = new TerminElement.BGDrawable(color.fromHsv(hsv));
            super.checked = super.down;
        }
    }
    
    private static class BGDrawable extends NinePatchDrawable {

        private Drawable separator;

        public BGDrawable(Color color) {
            super(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 5, color));
            super.getPatch().scale(2, 2);
            separator = ERE.assets.createDrawable("generic/horizontal_separator");
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            super.draw(batch, x, y, width, height); //To change body of generated methods, choose Tools | Templates.
//            separator.draw(batch, x, y+height-1, width, 1);
        }
    }
}
