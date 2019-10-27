package de.fungistudii.kalender.main.tabs.servies;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.generic.TitledWidget;
import static de.fungistudii.kalender.util.Durations.*;

/**
 *
 * @author sreis
 */
public class AddServiceDialog extends GenericDialog{
    
    private GenericTextField name;
    private PriceField price;
    private GenericDropDown duration;
    private GenericDropDown category;
    private GenericDropDown einwirken;
    
    private Service context;
    
    public AddServiceDialog() {
        super("Service Hinzufügen");
        
        
        name = new GenericTextField("Bezeichnung");
        price = new PriceField("10€");
        duration = new GenericDropDown(d15, d30, d45, d60, d90, d105, d120, d135, d150, d165, d180);
        category = new GenericDropDown("Herren", "Damen", "Andere");
        einwirken = new GenericDropDown("keine", "15 Minuten", "30 Minuten", "45 Minuten", "60 Minuten");
        
        Table row1 = new Table();
        row1.defaults().space(20);
        row1.add(new TitledWidget("Bezeichnung", name)).grow().width(Value.percentWidth(0.4f, row1)).left();
        row1.add(new TitledWidget("Kategorie", category)).grow().width(Value.percentWidth(0.3f, row1));
        row1.add(new TitledWidget("Preis", price)).grow().width(Value.percentWidth(0.15f, row1));
        
        Table row2 = new Table();
        row2.left();
        row2.defaults().space(20);
        row2.add(new TitledWidget("Dauer", duration)).width(Value.percentWidth(0.25f, row1)).left();
        row2.add(new TitledWidget("Einwirkzeit", einwirken)).width(Value.percentWidth(0.25f, row1));
        
        Image separator = new Image(ERE.assets.createDrawable("generic/separator"));
        
        Table group = new Table();
        group.defaults().space(20);
        group.add(row1).grow();
        group.row();
        group.add(separator).grow().height(1);
        group.row();
        group.add(row2).grow();
        
        super.setActor(group);
        
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
