/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.math.Matrix4.det;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Customer;
import de.fungistudii.kalender.database.Termin;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.Fonts;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 *
 * @author sreis
 */
public class ViewCustomerDialog extends GenericDialog{

    public ViewCustomerDialog() {
        super("Jens Rosenberg");
        super.prefWidth(900);
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
    
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE', 'dd' 'MMMMM' 'yyyy");
    
    private InfoTable createLastAppointment(Termin termin){
        InfoTable result = new InfoTable("Letzter Termin");
        
        result.add("icons/kalender_day", format.format(termin.start));
        result.add("icons/scissor", ERE.data.root.services.get(termin.service).name);
        result.add("icons/person", ERE.data.root.friseure.get(termin.friseur).name);
        
        if(termin.paidPrice != 0)
            result.add("icons/money", termin.paidPrice+"€");
        if(termin.color != null && !termin.color.isEmpty())
            result.add("icons/color", termin.color);
        if(termin.notes != null && !termin.notes.isEmpty())
            result.add("icons/note", termin.notes);
        
        return result;
    }

    public void show(Stage stage, int kunde) {
        super.show(stage);
        
        InfoTable terminTable = new InfoTable("Letzter Termin");
        terminTable.add("icons/money", "30€");
        
        Customer customer = ERE.data.root.kunden.get(kunde);
        Optional<Termin> optional = ERE.data.root.appointments.values().stream().filter(t -> t.kundenid==kunde).max((termin1, termin2) -> {
            return termin1.start.compareTo(termin2.start);
        });
        
        Table main = new Table();
        main.add(createInfo(customer)).growX().expandY().expandY().top();
        if(optional.isPresent())
            main.add(createLastAppointment(optional.get())).growX().expandY().top();
        main.pad(10, 30, 10, 30);
        
        setMainActor(main);
        setTitle(customer.vorname+" "+customer.name);
    }
    
    private static class InfoTable extends Table{
        private final LabelStyle def = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 16, Fonts.LIGHT), Color.BLACK);

        private Label title;
        
        public InfoTable(String title) {
            LabelStyle titleStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey7);
            this.title = new Label(title, titleStyle);
            super.add(this.title).colspan(2).left();
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
