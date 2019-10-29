 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import de.fungistudii.kalender.main.generic.TitledWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.NetworkData.TerminRequest;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericTextButton;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Popup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class AddAppointmentDialog extends Popup{
    
    private DateButton date;
    public GenericDropDown<Friseur> friseur;
    private GenericDropDown<Friseur> urheber;
    private TimePicker timePicker; 
    private ImageButton addButton;
    private TextButton okButton;
    private TextButton cancelButton;
    private ServiceWidget serviceWidget;
    private NameSearchField customerName;
    private GenericTextField customerPhone;
    
    private Calendar calendar = Calendar.getInstance();
    
    public AddAppointmentDialog() {
        super("Termin Hinzufügen");
        
        popupContainer.setBackground(new DrawableSolid(new Color(0.9f, 0.9f, 0.9f, 1)));
        popupContainer.prefWidth(600);
        super.setStageBackground(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        
        date = new DateButton();
        friseur = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        timePicker = new TimePicker();
        urheber = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        addButton = new ImageButton(ERE.assets.createDrawable("kalender/dialog/plus"));
        okButton = new GenericTextButton("Bestätigen", new GenericTextButton.FilledStyle());
        cancelButton = new GenericTextButton("Abbrechen", new GenericTextButton.CancelStyle());
        serviceWidget = new ServiceWidget(Value.percentHeight(1, friseur));
        customerName = new NameSearchField();
        customerPhone = new GenericTextField("Telefon");
        Label serviceLabel = new Label("LEISTUNGEN", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), ERE.assets.grey5));
        Label urheberLabel = new Label("URHEBER", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), ERE.assets.grey5));
        
        Table row1 = new Table();
        row1.defaults().space(Value.percentWidth(0.06f, this));
        row1.add(new TitledWidget("NAME", customerName)).grow();
        row1.add(new TitledWidget("TELEFON", customerPhone)).grow();
        
        Table row2 = new Table();
        row2.defaults().space(Value.percentWidth(0.06f, row2));
        row2.defaults().left();
        row2.add(new TitledWidget("DATE", date)).width(Value.percentWidth(0.4f, contentTable)).fill();
        row2.add(new TitledWidget("TIME", timePicker)).growX().fillY();
        row2.add(new TitledWidget("FRISEUR", friseur)).growX().fillY();
        
        Table row3 = new Table();
        row3.add(serviceLabel).left().padBottom(10);
        row3.add();
        row3.row();
        row3.add(serviceWidget).grow();
        row3.add(addButton).bottom().left().height(Value.percentHeight(0.5f, friseur)).pad(Value.percentHeight(0.25f, friseur));
        
        Table row4 = new Table();
        row4.defaults().space(10);
        row4.add(urheber).width(Value.percentWidth(0.3f, contentTable)).left();
        row4.add(new Image()).grow();
        row4.add(cancelButton).height(40).width(Value.percentWidth(0.25f, contentTable));
        row4.add(okButton).height(40).width(Value.percentWidth(0.25f, contentTable)).padRight(20);
        
        contentTable.defaults().space(10);
        contentTable.left();
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/separator");
        
        contentTable.add(row1).grow().padTop(15);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        //
        contentTable.add(row2).grow().padTop(15);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        //
        contentTable.add(row3).grow().padTop(15);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        //
        contentTable.add(urheberLabel).left().padTop(15);
        contentTable.row();
        contentTable.add(row4).grow().right();
        contentTable.row();
        contentTable.add().grow();
        
        contentTable.pack();
        
        customerName.setListener((k) -> {
            customerName.setText(k.toString());
            customerPhone.setText(k.phone);
        });
        
        addButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                serviceWidget.addService();
            }
        });
        
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                calendar.setTime(date.getDate());
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                TerminRequest request = new TerminRequest();
                request.duration = 60;
                request.start = calendar.getTime();
                request.friseurId = friseur.getSelected().id;
                request.kundenId = customerName.getId();
                request.serviceId = 0;
                ERE.client.sendTCP(request);
                hide();
            }
        });
        
        cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }

    public Popup show(Stage stage, Date def) {
        date.navigator.setDate(def);
        return super.show(stage);
    }
    
    private static int parseMinutes(String input){
        String[] s = input.split(":");
        return Integer.parseInt(s[0])*60+Integer.parseInt(s[1]);
    }
}
