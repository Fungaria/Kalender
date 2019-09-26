package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.Termin;
import static de.fungistudii.kalender.util.Fonts.LIGHT;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class TerminElement extends Button {

    private Label nameLabel;
    private Label leistungLabel;
    private Label timeLabel;

    private Termin termin;

    private static final float[][] colors = new float[][]{{237, 0.09f, 1}, {208, 0.12f, 1}, {300, 0.08f, 1}};
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private static final Calendar calendar = Calendar.getInstance();

    public TerminElement(Termin termin, Actor parent) {
        super(createButtonStyle(termin.id));
        this.termin = termin;

        Kunde kunde = ERE.data.root.kunden.get(termin.kundenid);
        Service service = ERE.data.root.services.get(termin.service);
        String startTime = timeFormat.format(termin.start);
        calendar.add(Calendar.MINUTE, termin.dauer);
        String endTime = timeFormat.format(termin.start);
        calendar.add(Calendar.MINUTE, -termin.dauer);

        Label.LabelStyle nameStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), Color.BLACK);
        Label.LabelStyle serviceStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("robotoCondensed", 11), ERE.assets.grey5);
        Label.LabelStyle timeStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 11, LIGHT), ERE.assets.grey5);
        this.nameLabel = new Label(kunde.vorname+" "+kunde.name, nameStyle);
        this.leistungLabel = new Label(service.name, serviceStyle);
        this.timeLabel = new Label(startTime+" - "+endTime, timeStyle);

        super.align(Align.topLeft);
        super.defaults().space(Value.percentWidth(0.02f, parent));
        super.pad(Value.percentWidth(0.05f, parent));

        super.add(nameLabel).fillY().top().left();
        super.row();
        super.add(leistungLabel).fillY().left();
        super.row();
        super.add(timeLabel).expand().bottom().right();
    }

    private static ButtonStyle createButtonStyle(int id) {
        ButtonStyle result = new ButtonStyle();
        Color color = new Color();
        float[] params = colors[id % colors.length];
        params[2] = 1;
        result.up = new NinePatchSolid(color.fromHsv(params));
        params[2] = 0.96f;
        result.over = new NinePatchSolid(color.fromHsv(params));
        params[2] = 0.9f;
        result.down = new NinePatchSolid(color.fromHsv(params));
        result.checked = new NinePatchSolid(color.fromHsv(params));
        return result;
    }
}
