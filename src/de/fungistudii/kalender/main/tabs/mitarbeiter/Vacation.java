package de.fungistudii.kalender.main.tabs.mitarbeiter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static com.badlogic.gdx.scenes.scene2d.ui.Table.debugTableColor;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.util.Fonts;

/**
 *
 * @author sreis
 */
public class Vacation extends VerticalGroup{
    
    public Vacation() {
        Label title = new Label("Urlaub", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 20, Fonts.MEDIUM), ERE.assets.grey5));
        
        
        addActor(title);
        addActor(new VacationElement());
        addActor(new AddButton());
        
        super.grow();
        super.space(10);
    }
    
    public void addVacation(){
        addActorAt(super.getChildren().size-1, new VacationElement());
    }
    
    private class VacationElement extends Table{
        
        
        public VacationElement() {
            NinePatchDrawable bg = ERE.assets.createNinePatchDrawable("generic/rounded_filled", 6, ERE.assets.grey2);
            super.setBackground(bg);
            
            Label date = new Label("11.09 - 28.09", new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14), Color.BLACK));
            ImageButton edit = new ImageButton(ERE.assets.createDrawable("employes/edit"));
            ImageButton delete = new ImageButton(ERE.assets.createDrawable("generic/thrash"));
            
            SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");
            
            add(date).grow().pad(10);
            add(new Image(separator)).width(1);
            add(delete).minSize(0).pad(8, 3, 8, 3);
            
         
            delete.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    remove();
                }
            });
        }

        @Override
        public float getPrefHeight() {
            return 50;
        }
    }
    
    private class AddButton extends ImageButton{

        public AddButton() {
            super(new ImageButtonStyle(ERE.assets.createNinePatchDrawable("generic/rounded_filled", 6, 6, 6, 6, ERE.assets.grey1), null, null, ERE.assets.createDrawable("employes/add"), null, null));
            super.pad(10, 0, 10, 0);
            
            super.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    addVacation();
                }
            });
        }

        @Override
        public float getPrefHeight() {
            return 50;
        }
    }
}
