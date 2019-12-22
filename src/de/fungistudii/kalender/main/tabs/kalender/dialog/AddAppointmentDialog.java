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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Friseur;
import de.fungistudii.kalender.client.NetworkData.CreateTerminRequest;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericImageButton;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericTextButton;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Popup;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class AddAppointmentDialog extends GenericMask{
    
    //1
    private NameSearchField customerName;
    private GenericTextField customerPhone;
    private Button edit;
    private Button nu;
    
    //2
    private DateButton date;
    private GenericDropDown timePicker; 
    public GenericDropDown<Friseur> friseur;
    
    //3
    private ServiceWidget serviceWidget;
    private ImageButton addServiceButton;
    
    //4
    private GenericDropDown<Friseur> urheber;
    
    private Calendar calendar = Calendar.getInstance();
    
    public AddAppointmentDialog() {
        super(3, "Termin Hinzufügen");
        super.setColumnWeights(new float[]{0.3f, 0.2f, 0.1f});
        initGUI();
        addListeners();
    }

    private void initGUI(){
        // ROW 1 ---------------------------------------------------------------
        customerName = new NameSearchField();
        customerPhone = new GenericTextField("Telefon");
        edit = new GenericImageButton("icons/settings");
        nu = new GenericImageButton("icons/customer");
        
        Table buttons = new Table();
        buttons.left().bottom();
        buttons.add(edit).left();
        buttons.add(nu).left();
        
        super.add(new TitledWidget("Kunde:", customerName));
        super.add(new TitledWidget("Telefon:", customerPhone));
        super.add(buttons).bottom().left();
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        date = new DateButton();
        timePicker = new GenericDropDown<>("13:30");
        friseur = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        super.add(new TitledWidget("Datum:", date));
        super.add(new TitledWidget("Uhrzeit:", timePicker));
        super.add(new TitledWidget("Friseur:", friseur)).padRight(100);
        
        super.separator();
        
        //ROW 3 ----------------------------------------------------------------
        Label serviceLabel = new Label("Leistungen:", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16), ERE.assets.grey5));
        serviceWidget = new ServiceWidget(Value.percentHeight(1, friseur));
        addServiceButton = new GenericImageButton("icons/service");
        Container addContainer = new Container(addServiceButton);
        addContainer.bottom().left();
        super.add(serviceLabel).left().padBottom(10);
        super.row();
        super.add(serviceWidget, 2);
        super.add(addContainer).bottom().left();
        
        //ROW 4 ----------------------------------------------------------------
        urheber = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        Container bottom = new Container(new TitledWidget("Urheber:", urheber));
        bottom.left();
        bottom.width(Value.percentWidth(0.25f, contentTable));
        super.setBottomActor(bottom);
    }
    
    private void addListeners(){
        customerName.setListener((k) -> {
            customerName.setText(k.toString());
            customerPhone.setText(k.phone);
        });
        
        addServiceButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                serviceWidget.addService();
            }
        });
        
        super.addConfirmCallback(() -> {
                calendar.setTime(date.getDate());
                calendar.set(Calendar.HOUR_OF_DAY, 5);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                CreateTerminRequest request = new CreateTerminRequest();
                request.duration = 60;
                request.start = calendar.getTime();
                request.friseurId = friseur.getSelected().id;
                request.kundenId = customerName.getId();
                request.serviceId = 0;
                ERE.client.sendTCP(request);
                hide();
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
