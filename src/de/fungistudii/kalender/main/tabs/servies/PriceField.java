package de.fungistudii.kalender.main.tabs.servies;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import de.fungistudii.kalender.main.generic.GenericTextField;

/**
 *
 * @author sreis
 */
public class PriceField extends GenericTextField{
    
    private int price;
    
    public PriceField(String defaultText) {
        super(defaultText);
        super.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                super.keyboardFocusChanged(event, actor, focused); //To change body of generated methods, choose Tools | Templates.
                if(focused){
                    setText(getText().replace("€", ""));
                }else if(!getText().isEmpty() && !getText().endsWith("€")){
                    setText(getText()+"€");
                }
            }
        });
    }

    public int getPrice(){
        return Integer.parseInt(getText().replace("€", ""));
    }
}
