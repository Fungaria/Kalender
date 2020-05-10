 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kalender.dialog;

import de.fungistudii.kalender.main.generic.TitledWidget;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Friseur;
import de.fungistudii.kalender.client.NetworkData.CreateTerminRequest;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericImageButton;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericSearchField;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author sreis
 */
public class AddAppointmentDialog extends GenericMask{
    
    //1
    private GenericSearchField<Customer> customerName;
//      private GenericTextField customerPhone;
    private GenericImageButton edit;
    private GenericImageButton nu;
    
    //2
    private DateButton date;
    private GenericDropDown<LocalTime> timePicker; 
    public GenericDropDown<Friseur> friseur;
    
    //3
    private ServiceWidget serviceWidget;
    private GenericImageButton addServiceButton;
    
    //4
    private GenericDropDown<Friseur> urheber;
    
    public AddAppointmentDialog() {
        super(3, "Termin Hinzufügen");
        super.setColumnWeights(new float[]{0.3f, 0.2f, 0.1f});
        initGUI();
        addListeners();
    }

    private void initGUI(){
        // ROW 1 ---------------------------------------------------------------
        customerName = new GenericSearchField<>((String s, Customer k) -> (k.name.toLowerCase().startsWith(s) || (k.vorname.toLowerCase() + " " + k.name.toLowerCase()).startsWith(s)));
        customerName.setMessageText("Enter name");
        customerName.setItems(ERE.data.root.kunden.values());
//        customerPhone = new GenericTextField("Telefon");
        edit = new GenericImageButton("icons/edit2", ERE.assets.grey3);
        nu = new GenericImageButton("icons/person", ERE.assets.grey3);
        
        Table buttons = new Table();
        buttons.left().bottom().defaults().space(Cons.dialogHorizontalSpacing);
        buttons.add(edit).left();
        buttons.add(nu).left();
        
        super.addC(new TitledWidget("Kunde:", customerName));
//        super.addC(new TitledWidget("Telefon:", customerPhone));
        super.addC(buttons).bottom().left();
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        date = new DateButton();
        timePicker = new TimePicker();
        friseur = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        super.addC(new TitledWidget("Datum:", date));
        super.addC(new TitledWidget("Uhrzeit:", timePicker));
        super.addC(new TitledWidget("Friseur:", friseur)).padRight(100);
        
        super.separator();
        
        //ROW 3 ----------------------------------------------------------------
        Label serviceLabel = new Label("Leistungen:", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16), ERE.assets.grey5));
        serviceWidget = new ServiceWidget(Value.percentHeight(1, friseur));
        addServiceButton = new GenericImageButton("icons/service");
        Container addContainer = new Container(addServiceButton);
        addContainer.bottom().left();
        super.addC(serviceLabel).left().padBottom(10);
        super.rowC();
        super.addC(serviceWidget, 2);
        super.addC(addContainer).bottom().left();
        
        super.separator();
        
        //ROW 4 ----------------------------------------------------------------
        urheber = new GenericDropDown<>(ERE.data.root.friseure.values().stream().toArray(Friseur[]::new));
        Container bottom = new Container(new TitledWidget("Urheber:", urheber));
        bottom.left();
        bottom.width(Value.percentWidth(0.25f, this));
        super.setBottomActor(bottom);
    }
    
    private void addListeners(){
        customerName.setListener((k) -> {
            customerName.setText(k.toString());
//            customerPhone.setText(k.phone);
        });
        
        addServiceButton.addListener(
            () -> serviceWidget.addService()
        );
        
        edit.addListener(() -> ERE.mainScreen.dialogManager.showCustomer());
        
        super.addConfirmCallback(() -> {
                CreateTerminRequest request = new CreateTerminRequest();
                request.duration = 90;
                request.start = LocalDateTime.of(date.getDate(), timePicker.getSelected());
                request.friseurId = friseur.getSelected().id;
                request.kundenId = customerName.getSelected().id;
                request.serviceId = serviceWidget.getService(0).id;
                ERE.client.sendTCP(request);
                hide();
        });
    }
    
    public void init(LocalDateTime def, int friseur){
        date.navigator.setDate(def.toLocalDate());
        this.friseur.setSelectedIndex(friseur);
    }
    
    
    private static int parseMinutes(String input){
        String[] s = input.split(":");
        return Integer.parseInt(s[0])*60+Integer.parseInt(s[1]);
    }
}
