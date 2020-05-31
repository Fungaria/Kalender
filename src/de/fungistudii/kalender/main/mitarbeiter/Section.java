package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.GenericImageButton;

/**
 *
 * @author sreis
 */
public abstract class Section extends Table{

    protected ImageButton edit;
    
    public Section(String title) {
        super.add(new Label(title, ERE.assets.titleStyle2)).left().padBottom(10).width(180);
        edit = new ImageButton(ERE.assets.createDrawable("icons/edit2", ERE.assets.grey2));
        super.add(edit).expandX().right().height(30);
        super.row();
        
        super.left().top().padTop(10).padBottom(30);
        super.defaults().spaceTop(10);
    }
}
