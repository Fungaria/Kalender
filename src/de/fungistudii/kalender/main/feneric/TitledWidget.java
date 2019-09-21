package de.fungistudii.kalender.main.feneric;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import static de.fungistudii.kalender.Main.ERE;

/**
 *
 * @author sreis
 */
public class TitledWidget extends Table{

    private Label label;
    public Actor actor;
    
    public TitledWidget(String title, Actor actor) {
        super();
        this.actor = actor;
        this.label = new Label(title, new LStyle());
        super.add(label).left().grow();
        super.row();
        super.add(actor).left().grow();
    }

    private static final class LStyle extends Label.LabelStyle{
        public LStyle() {
            super.font = ERE.assets.fonts.createFont("roboto", 12);
            super.fontColor = ERE.assets.grey3;
        }
    }
}
