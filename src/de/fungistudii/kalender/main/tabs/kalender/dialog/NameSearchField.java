/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.Kunde;
import de.fungistudii.kalender.util.DrawableSolid;
import de.fungistudii.kalender.util.Fonts;
import de.fungistudii.kalender.util.SearchField;

/**
 *
 * @author sreis
 */
public class NameSearchField extends SearchField<Kunde>{
    public NameSearchField() {
        super(new test(), (String s, Kunde k) -> (k.name.startsWith(s) || (k.vorname+" "+k.name).startsWith(s)));
        super.setMessageText("Enter name");
        super.setItems(ERE.data.root.kunden.values());
    }
    
    private static final class test extends SFStyle {
        public test() {
            super.textFieldStyle = new TextFieldStyle();
            super.textFieldStyle.background = ERE.assets.createNinePatchDrawable("generic/rounded", 10, 10, 5, 10);
            super.textFieldStyle.font = ERE.assets.fonts.createFont("roboto", 15, Fonts.LIGHT);
            super.textFieldStyle.fontColor = Color.BLACK;
            super.textFieldStyle.cursor = ERE.assets.createDrawable("generic/textfield_cursor");
            super.textFieldStyle.cursor.setMinWidth(1);
            super.textFieldStyle.selection = ERE.assets.createDrawable("generic/textfield_selection");
            super.listStyle = new List.ListStyle(textFieldStyle.font, ERE.assets.grey5, ERE.assets.grey4, new DrawableSolid(ERE.assets.grey2));
            super.scrollStyle = new ScrollPane.ScrollPaneStyle();
            super.scrollStyle.background = ERE.assets.createNinePatchDrawable("generic/square", 15);
        }
    }
}
