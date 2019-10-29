package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
import static de.fungistudii.kalender.util.Fonts.LIGHT;
import de.fungistudii.kalender.util.NinePatchSolid;
import de.fungistudii.kalender.util.value.ValueUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class TerminElement extends GridElement{

    private Label nameLabel;
    private Label leistungLabel;
    private Label timeLabel;

    private Cell leistCell;
    private Cell timeCell;
    
    private Termin termin;

    private static final float[][] colors = new float[][]{{237, 0.09f, 1}, {208, 0.12f, 1}, {300, 0.08f, 1}};
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private static final Calendar calendar = Calendar.getInstance();

    public TerminElement(Termin termin) {
        super(createButtonStyle(termin.id));
        this.termin = termin;

        Kunde kunde = ERE.data.root.kunden.get(termin.kundenid);
        Service service = ERE.data.root.services.get(termin.service);
        String startTime = timeFormat.format(termin.start);
        calendar.add(Calendar.MINUTE, termin.dauer);
        String endTime = timeFormat.format(termin.start);
        calendar.add(Calendar.MINUTE, -termin.dauer);

        Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), Color.BLACK);
        Label.LabelStyle serviceStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 11), ERE.assets.grey6);
        Label.LabelStyle timeStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 11, LIGHT), ERE.assets.grey6);
        this.nameLabel = new Label(kunde.vorname+" "+kunde.name, nameStyle);
        this.leistungLabel = new Label(service.name, serviceStyle);
        this.timeLabel = new Label(startTime+" - "+endTime, timeStyle);

        super.align(Align.topLeft);
        super.padTop(0);
        super.defaults().space(Value.percentWidth(0.02f, this));
        
        nameLabel.setTouchable(Touchable.disabled);
        leistungLabel.setTouchable(Touchable.disabled);
        timeLabel.setTouchable(Touchable.disabled);

        super.add(nameLabel).fillY().top().left().padTop(ValueUtil.percentMinHeight(0.06f, this, 20)).padLeft(15);
        super.row();
        leistCell = super.add(leistungLabel).fillY().left().padLeft(15);
        super.row();
        timeCell = super.add(timeLabel).expand().bottom().right().padRight(10).padBottom(10);
        
        super.setClip(true);
    }

    @Override
    public void layout() {
        super.layout();
        if(getHeight() < 80 && leistCell.getActor()!=null){
            leistungLabel.remove();
        }else if(getHeight() < 40 && timeCell.getActor()!=null){
            timeLabel.remove();
        }else if(getHeight() > 80){
            if(leistCell.getActor() == null)
                leistCell.setActor(leistungLabel);
            if(timeCell.getActor() == null)
                timeCell.setActor(timeLabel);
        }
        super.layout();
    }
    
    private static ButtonStyle createButtonStyle(int id) {
        ButtonStyle result = new ButtonStyle();
        Color color = new Color(1, 1, 1, 1);
        float[] params = colors[id % colors.length];
        params[2] = 1;
        result.up = new BGDrawable(color.fromHsv(params));
        params[2] = 0.96f;
        result.over = new BGDrawable(color.fromHsv(params));
        params[2] = 0.9f;
        result.down = new BGDrawable(color.fromHsv(params));
        result.checked = result.down;
        return result;
    }

    public Termin getTermin(){
        return termin;
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
        return termin.dauer;
    }
    
    private static class BGDrawable extends NinePatchDrawable{

        private Drawable separator;
        
        public BGDrawable(Color color) {
            super(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 5, color));
            super.getPatch().scale(2, 2);
            separator = ERE.assets.createDrawable("generic/separator");
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            super.draw(batch, x, y, width, height); //To change body of generated methods, choose Tools | Templates.
//            separator.draw(batch, x, y+height-1, width, 1);
        }
    }
}
