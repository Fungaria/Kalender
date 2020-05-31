package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.database.Employee;
import de.fungistudii.kalender.main.generic.GenericDialog;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericTextArea;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.generic.TitledWidget;
import de.fungistudii.kalender.util.Popup;

/**
 *
 * @author sreis
 */
public class EmployeeContactDialog extends GenericMask{
    
    private GenericTextField name;
    private GenericTextField lastName;
    private GenericTextField phone;
    private GenericTextField mobile;
    private GenericDropDown gender;
    private GenericTextArea notizen;
    
    private Employee employee;
    
    public EmployeeContactDialog() {
        super(3, "Details");
        super.setColumnWeights(new float[]{0.3f, 0.3f, 0f});
        super.prefWidth(800);
        initGUI();
    }

    private void initGUI(){
        // ROW 1 ---------------------------------------------------------------
        name = new GenericTextField("John");
        lastName = new GenericTextField("Doe");
        
        super.addC(new TitledWidget("Vorname:", name));
        super.addC(new TitledWidget("Nachname:", lastName));
        super.addC(new Container());
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        phone = new GenericTextField("+089123456");
        mobile = new GenericTextField("+4912345678");
        
        super.addC(new TitledWidget("Telefon:", phone));
        super.addC(new TitledWidget("Mobil:", mobile));
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        gender = new GenericDropDown("Herr", "Frau", "Andere");
        notizen = new GenericTextArea("...");
        
        notizen.setRows(2);
        notizen.setAlignment(Align.topLeft);
        
        super.addC(new TitledWidget("Andere:", gender));
        super.addC(new TitledWidget("Notizen:", notizen), 2);
    }

    public Popup show(Stage stage, Employee employee) {
        this.employee = employee;
        return super.show(stage);
    }
}