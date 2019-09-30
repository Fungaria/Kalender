package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.generic.ConfirmationDialog;
import de.fungistudii.kalender.main.generic.ContextMenu;
import static de.fungistudii.kalender.util.Fonts.LIGHT;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author sreis
 */
public class TerminElement extends Button implements GridElement{

    private Label nameLabel;
    private Label leistungLabel;
    private Label timeLabel;

    private Termin termin;

    private static final float[][] colors = new float[][]{{237, 0.09f, 1}, {208, 0.12f, 1}, {300, 0.08f, 1}};
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private static final Calendar calendar = Calendar.getInstance();

    private int row;
    private int column;
    private int span;
    
    public TerminElement(Termin termin, Actor parent) {
        super(createButtonStyle(termin.id));
        this.termin = termin;

        final ConfirmationDialog dialog = new ConfirmationDialog("Bestätigen", "Sind sie sicher das sie den ausgewählten Termin löschen möchten?");
        dialog.addConfirmCallback(() -> {
            NetworkData.StornoRequest request = new NetworkData.StornoRequest();
            request.id = termin.id;
            ERE.client.sendTCP(request);
        });
        
        HashMap<String, Runnable> actions = new HashMap<>();
        actions.put("Termin stornieren", ()->{dialog.show(getStage());});
        actions.put("Termin bearbeiten", ()->{});
        new ContextMenu(this, actions);
        
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
        super.padTop(15);
        super.padLeft(15);

        super.add(nameLabel).fillY().top().left();
        super.row();
        super.add(leistungLabel).fillY().left();
        super.row();
        super.add(timeLabel).expand().bottom().right().padRight(10).padBottom(10);
        
        super.setClip(true);
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

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public void setRow(int row){
        this.row = row;
    }
    
    @Override
    public int getRow() {
        return row;
    }
}
