package de.fungistudii.kalender.main.servies;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.database.Service;

/**
 *
 * @author sreis
 */
public class ServiceElement extends Button{
    
    private Service service;
    
    public ServiceElement(Service service) {
        super(new ButtonStyle());
        
        this.service = service;
        
        NinePatchDrawable up = ERE.assets.createNinePatchDrawable("rounded/filled", 15);
        NinePatchDrawable down = ERE.assets.createNinePatchDrawable("rounded/filled", 15, ERE.assets.grey2);
        SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");
        Label.LabelStyle lStyle = new Label.LabelStyle(ERE.assets.fonts.createFont("roboto", 14), ERE.assets.grey7);
        
        ButtonStyle bStyle = new ButtonStyle();
        bStyle.up = up;
        bStyle.focused = down;
        bStyle.checkedFocused = down;
        
        super.setTouchable(Touchable.enabled);
        
        Button settings = new Button(ERE.assets.createDrawable("icons/edit", ERE.assets.grey4));
        
        super.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                getStage().setKeyboardFocus(ServiceElement.this);
                return false;
            }
        });
        
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.mainScreen.dialogManager.showService(service);
//                ERE.mainScreen.service.dialog.setTitle("Service Bearbeiten");
            }
        });
        
        super.setStyle(bStyle);
        
        String title = service.name;
        if(service.id == 0)
            title = "Herren - "+title;
        else if(service.id == 1)
            title = "Damen - "+title;
        
        Label name = new Label(title, lStyle);
        Label duration = new Label("1:30", lStyle);
        Label price = new Label(service.price+"€", lStyle);
        
        super.defaults().padLeft(20);
        super.add(name).minSize(0).width(Value.percentWidth(0.4f, this)).left();
        super.add(new Image(separator)).minHeight(0).width(1).pad(5, 7, 5, 7);
        super.add(duration).grow().minSize(0);
        super.add(new Image(separator)).minHeight(0).width(1).pad(5, 7, 5, 7);
        super.add(price).grow().minSize(0);
        super.add(new Image(separator)).minHeight(0).width(1).pad(5, 7, 5, 7);
        super.add(settings).minSize(0).size(Value.percentHeight(0.5f, this)).padLeft(10).padRight(10);
    }
}
