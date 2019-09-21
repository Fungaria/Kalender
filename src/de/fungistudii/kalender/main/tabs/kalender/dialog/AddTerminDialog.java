/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import de.fungistudii.kalender.main.feneric.TitledWidget;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import de.fungistudii.kalender.client.database.NetworkData.TerminRequest;
import de.fungistudii.kalender.main.feneric.GenericDropDown;
import de.fungistudii.kalender.main.feneric.GenericTextField;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Popup;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class AddTerminDialog extends Popup{
    
    VerticalGroup leistungsGroup;
    
    private GenericTextField name;
    private GenericTextField phone;
    private GenericTextField mail;
    
    private DateButton date;
    private GenericDropDown<String> timeHours;
    private GenericDropDown<String> timeMins;
    private GenericDropDown<Friseur> friseur;
    
    private ArrayList<LeistungTable> leistungen = new ArrayList<>();
    
    public AddTerminDialog() {
        super();
        popupContainer.setBackground(new DrawableSolid(new Color(0.9f, 0.9f, 0.9f, 1)));
        super.setStageBackground(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        
        name = new GenericTextField("Name");
        phone = new GenericTextField("Telefon");
        mail = new GenericTextField("Mail");
        date = new DateButton();
        timeHours = new GenericDropDown<>(null, "generic/dropdown", "generic/dropdown_selected", new String[]{"08","09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"});
        timeMins = new GenericDropDown<>(null, "generic/dropdown", "generic/dropdown_selected", new String[]{"00", "15", "30", "45"});
        friseur = new GenericDropDown<>(ERE.data.root.friseure.stream().toArray(Friseur[]::new));
        
        SpriteDrawable separator = ERE.assets.createDrawable("generic/separator");
        
        Table row1 = new Table();
        row1.defaults().space(Value.percentWidth(0.06f, row1));
        row1.add(new TitledWidget("NAME", name)).growX().fillY();
        row1.add(new TitledWidget("TELEFON", phone)).growX().fillY();
        row1.add(new TitledWidget("MAIL", mail)).growX().fillY();
        
        HorizontalGroup time = new HorizontalGroup();
        time.space(5);
        Image image = new Image(ERE.assets.createDrawable("kalender/dialog/time_dots"));
        image.setScale(0.6f);
        time.addActor(timeHours);
        time.addActor(image);
        time.addActor(timeMins);
        
        Table row2 = new Table();
        row2.defaults().space(Value.percentWidth(0.06f, row2));
        row2.add(new TitledWidget("DATE", date)).prefWidth(Value.percentWidth(1, name)).fillY();
        row2.add(new TitledWidget("TIME", time)).growX().fillY();
        row2.add(new TitledWidget("FRISEUR", friseur)).growX().fillY();
        
        leistungsGroup = new VerticalGroup();
        leistungsGroup.left();
        addLeistung();
        
        Label leistLabel = new Label("LEISTUNGEN", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 13), ERE.assets.grey4));
        SpriteDrawable plus = ERE.assets.createDrawable("kalender/dialog/plus");
        ImageButton addButton = new ImageButton(new ImageButton.ImageButtonStyle(null, null, null, plus, plus, plus));
        
        Label title = new Label("Termin erstellen", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20), ERE.assets.grey4));
        
        NinePatchDrawable button_bg = ERE.assets.createNinePatchDrawable("generic/textfield", 10);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(button_bg, button_bg, button_bg, ERE.assets.fonts.createFont("roboto", 13));
        buttonStyle.fontColor = ERE.assets.grey4;
        TextButton ok = new TextButton("Bestätigen", buttonStyle);
        TextButton cancel = new TextButton("Abbrechen", buttonStyle);
        
        HorizontalGroup buttons = new HorizontalGroup();
        buttons.space(20);
        buttons.addActor(cancel);
        buttons.addActor(ok);
        
        contentTable.left();
        contentTable.add(title).padBottom(Value.percentHeight(1.5f, title));
        contentTable.row();
        contentTable.add(row1).grow().padBottom(20);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1).padBottom(20);
        contentTable.row();
        contentTable.add(row2).grow().padBottom(20);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1).padBottom(20);
        contentTable.row();
        contentTable.add(leistLabel).left();
        contentTable.row();
        contentTable.add(leistungsGroup).growX();
        contentTable.row();
        contentTable.add(addButton).size(Value.percentHeight(0.5f, name)).padBottom(20);
        contentTable.row();
        contentTable.add(new Image(separator)).growX().height(1).padBottom(20);
        contentTable.row();
        contentTable.add(buttons).right().padBottom(20);
        contentTable.row();
        contentTable.add().grow();
        
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
                TerminRequest request = new TerminRequest();
                request.duration = 90;
                request.start = date.calendar.getTime();
                request.friseurId = friseur.getSelected().id;
                request.kundenId = 0;
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
    
    private void addLeistung(){
        LeistungTable leistung = new LeistungTable();
        leistungen.add(leistung);
        leistung.prefHeight(Value.percentHeight(1, name));
        leistung.prefWidth(Value.percentWidth(0.7f, contentTable));
        leistung.padBottom(Value.percentHeight(0.1f, name));
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
