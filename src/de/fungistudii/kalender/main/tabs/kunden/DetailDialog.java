package de.fungistudii.kalender.main.tabs.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.main.generic.GenericDropDown;
import de.fungistudii.kalender.main.generic.GenericMask;
import de.fungistudii.kalender.main.generic.GenericTextArea;
import de.fungistudii.kalender.main.generic.GenericTextField;
import de.fungistudii.kalender.main.generic.TitledWidget;

/**
 *
 * @author sreis
 */
public class DetailDialog extends GenericMask{
    
    private GenericTextField name;
    private GenericTextField lastName;
    private GenericTextField phone;
    private GenericTextField mobile;
    private GenericDropDown gender;
    private GenericTextArea notizen;
    
    public DetailDialog() {
        super(3, "Details");
        super.setColumnWeights(new float[]{0.3f, 0.3f, 0f});
        initGUI();
    }

    private void initGUI(){
        // ROW 1 ---------------------------------------------------------------
        name = new GenericTextField("John");
        lastName = new GenericTextField("Doe");
        
        super.add(new TitledWidget("Vorname:", name));
        super.add(new TitledWidget("Nachname:", lastName));
        super.add(new Container());
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        phone = new GenericTextField("+089123456");
        mobile = new GenericTextField("+4912345678");
        
        super.add(new TitledWidget("Telefon:", phone));
        super.add(new TitledWidget("Mobil:", mobile));
        super.separator();
        
        //ROW 2  ---------------------------------------------------------------
        gender = new GenericDropDown("Herr", "Frau", "Andere");
        notizen = new GenericTextArea("...");
        
        notizen.setRows(2);
        notizen.setAlignment(Align.topLeft);
        
        super.add(new TitledWidget("Andere:", gender));
        super.add(new TitledWidget("Notizen:", notizen), 2);
    }
}
