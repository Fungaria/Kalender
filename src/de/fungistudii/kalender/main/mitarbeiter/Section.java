package de.fungistudii.kalender.main.mitarbeiter;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public abstract class Section extends Table{

    public Section(String title) {
        super.add(new Label(title, ERE.assets.titleStyle2)).left().padBottom(10);
        super.row();
        
        super.left().top().padTop(10).padBottom(20);
        super.defaults().spaceTop(10);
    }
}
