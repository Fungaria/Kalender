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
import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.NetworkData.TerminRequest;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericTextButton;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Popup;
import de.fungistudii.kalender.util.SearchField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class AddTerminDialog extends Popup{
    
    VerticalGroup leistungsGroup;
    
    private KundenRow row1;
    
    private DateButton date;
    public GenericDropDown<String> timeHours;
    public GenericDropDown<String> timeMins;
    public GenericDropDown<Friseur> friseur;
    private GenericDropDown<Friseur> urheber;
    
    private ArrayList<LeistungTable> leistungen = new ArrayList<>();
    
    public AddTerminDialog() {
        super();
        popupContainer.setBackground(new DrawableSolid(new Color(0.9f, 0.9f, 0.9f, 1)));
        super.setStageBackground(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        
        contentTable.pad(Value.percentWidth(0.023f, this));
        
        row1 = new KundenRow();
        
        date = new DateButton();
        timeHours = new GenericDropDown<>(null, "generic/rounded", "generic/rounded_check", new String[]{"08","09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"});
        timeMins = new GenericDropDown<>(null, "generic/rounded", "generic/rounded_check", new String[]{"00", "15", "30", "45"});
        friseur = new GenericDropDown<>(ERE.data.root.friseure.stream().toArray(Friseur[]::new));
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/separator");
        
        HorizontalGroup time = new HorizontalGroup();
        time.space(5);
        Image image = new Image(ERE.assets.createDrawable("kalender/dialog/time_dots"));
        image.setScale(0.6f);
        time.addActor(timeHours);
        time.addActor(image);
        time.addActor(timeMins);
        
        Table row2 = new Table();
        row2.defaults().space(Value.percentWidth(0.06f, row2));
        row2.defaults().left();
        row2.add(new TitledWidget("DATE", date)).width(Value.percentWidth(0.4f, contentTable)).fill();
        row2.add(new TitledWidget("TIME", time)).growX().fillY();
        row2.add(new TitledWidget("FRISEUR", friseur)).growX().fillY();
        
        leistungsGroup = new VerticalGroup();
        leistungsGroup.left();
        addLeistung();
        
        Label leistLabel = new Label("LEISTUNGEN", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), ERE.assets.grey4));
        Label urheberLabel = new Label("URHEBER", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), ERE.assets.grey4));
        SpriteDrawable plus = ERE.assets.createDrawable("kalender/dialog/plus");
        ImageButton addButton = new ImageButton(new ImageButton.ImageButtonStyle(null, null, null, plus, plus, plus));
        
        Label title = new Label("Termin erstellen", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20), ERE.assets.grey4));
        
        NinePatchDrawable button_bg = ERE.assets.createNinePatchDrawable("generic/rounded", 10);
        TextButton ok = new GenericTextButton("Bestätigen", new GenericTextButton.OutlineStyle());
        TextButton cancel = new TextButton("Abbrechen", new GenericTextButton.OutlineStyle());
        
        urheber = new GenericDropDown<>(ERE.data.root.friseure.stream().toArray(Friseur[]::new));
        Table buttons = new Table();
        buttons.defaults().spaceLeft(Value.percentWidth(0.01f, this));
        buttons.add(urheber).width(Value.percentWidth(0.3f, contentTable)).left();
        buttons.add(new Image()).grow();
        buttons.add(cancel);
        buttons.add(ok);
        
        contentTable.defaults().space(10);
        
        contentTable.left();
        contentTable.add(title);
        contentTable.row();
        contentTable.add(row1).grow().padTop(15);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        contentTable.add(row2).grow().padTop(15);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        contentTable.add(leistLabel).left().padTop(15);
        contentTable.row();
        contentTable.add(leistungsGroup).growX();
        contentTable.row();
        contentTable.add(addButton).size(Value.percentHeight(0.5f, date));
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1);
        contentTable.row();
        contentTable.add(urheberLabel).left().padTop(15);
        contentTable.row();
        contentTable.add(buttons).grow().right();
        contentTable.row();
        contentTable.add().grow();
        
        contentTable.pack();
        
        addButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addLeistung();
            }
        });
        
        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                date.calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeHours.getSelected()));
                date.calendar.set(Calendar.MINUTE, Integer.parseInt(timeMins.getSelected()));
                TerminRequest request = new TerminRequest();
                request.duration = parseMinutes(leistungen.get(0).duration.getSelected());
                request.start = date.calendar.getTime();
                request.friseurId = friseur.getSelected().id;
                request.kundenId = row1.getSelected().id;
                request.serviceId = leistungen.get(0).leistung.getSelected().id;
                ERE.client.sendTCP(request);
                hide();
            }
        });
        
        cancel.addListener(new ClickListener(){
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
    
    private void addLeistung(){
        LeistungTable leistung = new LeistungTable();
        leistungen.add(leistung);
        leistung.prefHeight(Value.percentHeight(1, date));
        leistung.prefWidth(Value.percentWidth(0.7f, contentTable));
        leistung.padBottom(Value.percentHeight(0.1f, date));
        leistungsGroup.addActor(leistung);
        leistung.delete.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(leistungen.size()>1){
                    leistungen.remove(leistung);
                    leistung.remove();
                }
            }
        });
    }
}
