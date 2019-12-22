package de.fungistudii.kalender.main.tabs.servies;

import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericTextField;
import static de.fungistudii.kalender.util.Durations.*;

/**
 *
 * @author sreis
 */
public class AddServiceDialog extends GenericMask{
    
    private GenericTextField name;
    private PriceField price;
    private GenericDropDown duration;
    private GenericDropDown category;
    private GenericDropDown einwirken;
    
    private Service context;
    
    public AddServiceDialog() {
        super(3, "Service Hinzufügen");
        
        name = new GenericTextField("Bezeichnung");
        price = new PriceField("10€");
        duration = new GenericDropDown(d15, d30, d45, d60, d90, d105, d120, d135, d150, d165, d180);
        category = new GenericDropDown("Herren", "Damen", "Andere");
        einwirken = new GenericDropDown("keine", "15 Minuten", "30 Minuten", "45 Minuten", "60 Minuten");
        
        super.add("Bezeichnung: ", name, 2);
        super.add("Kategorie: ", category);
        
        super.separator();
        
        super.add("Dauer: ", duration);
        super.add("Einwirkzeit: ", einwirken);
        super.add("Preis: ", price);
        
        super.addConfirmCallback(() -> {
            if(context == null){
                NetworkData.CreateServiceRequest request = new NetworkData.CreateServiceRequest();
                request.category = category.getSelectedIndex();
                request.duration = 90;
                request.einwirkZeit = 30;
                request.name = name.getText();
                request.price = price.getPrice();
                ERE.client.sendTCP(request);
            }else{
                Service service = new Service(context.id, name.getText(), category.getSelectedIndex(), duration.getSelectedIndex()*15, einwirken.getSelectedIndex()*15, price.getPrice());
                ERE.client.sendTCP(service);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        this.context = null;
    }
    
    public void show(Service service) {
        name.setText(service.name);
        price.setText(service.price+"");
        category.setSelectedIndex(service.category);
        this.context = service;
        super.show();
    }
}
