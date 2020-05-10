package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.util.Fonts;
import java.util.Optional;

/**
 *
 * @author sreis
 */
public class ViewCustomerDialogNu extends GenericDialog{

    private final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(){
        {
            super.down = ERE.assets.createNinePatchDrawable("underscore", 0, 0, 0, 5);
            super.checked = ERE.assets.createNinePatchDrawable("underscore", 0, 0, 0, 5);
            super.font = ERE.assets.fonts.createFont("roboto", 15);
            super.fontColor = ERE.assets.grey6;
        }
    };
    
    private Container container;
    
    public ViewCustomerDialogNu() {
        super("Jens Hoffmann");
        
        Table contentTable = new Table();
        Button details = new TextButton("Kontaktinformationen", style);
        Button termine = new TextButton("Termine", style);
        
        ButtonGroup group = new ButtonGroup(details, termine);
        
        container = new Container();
        
        Table t = new Table();
        
        t.add(details).left();
        t.add(termine).left();
        t.add(new Container()).grow();
        t.row();
        t.add(container).grow().colspan(3);
        
        setMainActor(t);
    }
    
    public void show(Stage stage, int kunde) {
        super.show(stage);
        
        Customer customer = ERE.data.root.kunden.get(kunde);
        Optional<Termin> optional = ERE.data.root.appointments.values().stream().filter(t -> t.kundenid==kunde).max((termin1, termin2) -> {
            return termin1.start.compareTo(termin2.start);
        });
        
        container.setActor(createInfo(customer));
        setTitle(customer.vorname+" "+customer.name);
    }
    
    private InfoTable createInfo(Customer customer){
        InfoTable result = new InfoTable("Kontakinformationen");
        
        if(customer.phone != null && !customer.phone.isEmpty())
            result.add("icons/phone", customer.phone);
        if(customer.mail!= null && !customer.mail.isEmpty())
            result.add("icons/mail", customer.mail);
        if(customer.notes!=null && !customer.notes.isEmpty())
            result.add("icons/note", customer.notes);
        
        return result;
    }
    
    private static class InfoTable extends Table{
        private final Label.LabelStyle def = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT), Color.BLACK);

        public InfoTable(String title) {
            super.defaults().padTop(Cons.defaultRowHeight/4f);
            row();
        }
        
        public void add(String icon, String info){
            Image image = new Image(ERE.assets.createDrawable(icon, ERE.assets.grey3));
            image.setScaling(Scaling.fillY);
            Label label = new Label(info, def);
            
            add(image).minWidth(60).minHeight(0).prefHeight(Cons.defaultRowHeight/2).padLeft(5).padRight(5).fill();
            add(label).expandX().left();
            row();
        }
        
    }
    
}
