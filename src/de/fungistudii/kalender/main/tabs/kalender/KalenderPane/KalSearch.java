package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.NinePatchIconDrawable;
import de.fungistudii.kalender.util.SearchField;

/**
 *
 * @author sreis
 */
public class KalSearch extends SearchField<Kunde>{
    public KalSearch() {
        super(new Style(), (String s, Kunde k) -> (k.name.startsWith(s) || (k.vorname+" "+k.name).startsWith(s)));
        super.setMessageText("        Enter name");
        super.setItems(ERE.data.root.kunden.values());
        super.addListener(new FocusListener() {
            @Override
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if(focused){
                    ((Style)getSFStyle()).hideIcon();
                }else if(getText().isEmpty()){
                    ((Style)getSFStyle()).showIcon();
                }
            }
        });
    }
    
    public int getId(){
        return getSelected().id;
    }
    
    private static final class Style extends SearchField.SFStyle {
        
        private final NinePatchIconDrawable bg;
        
        public Style() {
            super.textFieldStyle = new TextField.TextFieldStyle();
            
            this.bg = new NinePatchIconDrawable(
                ERE.assets.createDrawable("icons/search", ERE.assets.grey4),
                ERE.assets.createRounded("outline"), 15, Align.left
            );
            super.textFieldStyle.background = bg;
            super.textFieldStyle.font = ERE.assets.fonts.createFont("roboto", 16);
            super.textFieldStyle.fontColor = Color.BLACK;
            super.textFieldStyle.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.textFieldStyle.cursor.setMinWidth(1);
            super.textFieldStyle.selection = ERE.assets.createDrawable("generic/textfield_selection");
            super.listStyle = new List.ListStyle(textFieldStyle.font, ERE.assets.grey7, ERE.assets.grey5, new DrawableSolid(ERE.assets.grey2));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
        }
        
        void hideIcon(){
            ((SpriteDrawable)bg.getIcon()).getSprite().setColor(1, 1, 1, 0);
        }
        
        void showIcon(){
            ((SpriteDrawable)bg.getIcon()).getSprite().setColor(ERE.assets.grey4);
        }
    }
}