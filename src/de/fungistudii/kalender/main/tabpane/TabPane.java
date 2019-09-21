/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabpane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import java.util.ArrayList;

/**
 *
 * @author sreis
 */
public class TabPane extends Table {

    private final ArrayList<Button> tabs = new ArrayList<Button>();

    private final Logo orplid;
    private final TabButton kalender;
    private final TabButton produkte;
    private final TabButton mitarbeiter;
    private final TabButton kunden;
    private final Image filler;
    
    public TabPane() {
        super();
        
        orplid = new Logo();
        orplid.setDisabled(true);
        kalender = new TabButton("Kalender");
        kunden = new TabButton("Kunden");
        mitarbeiter = new TabButton("Mitarbeiter");
        produkte = new TabButton("Produkte");
        filler = new Image(ERE.assets.createDrawable("tabs/button_up"));
        
        tabs.add(kalender);
        tabs.add(kunden);
        tabs.add(mitarbeiter);
        tabs.add(produkte);
        
        kalender.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ERE.mainScreen.setTab(ERE.mainScreen.kalender);
            }
        });
        kunden.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ERE.mainScreen.setTab(ERE.mainScreen.kunden);
            }
        });
        mitarbeiter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ERE.mainScreen.setTab(ERE.mainScreen.mitarbeiter);
                ERE.mainScreen.mitarbeiter.show();
            }
        });
        produkte.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ERE.mainScreen.setTab(ERE.mainScreen.produkte);
            }
        });

        super.align(Align.left);
        super.add(orplid).width(Value.percentWidth(Cons.sideBarPercentWidth, this)).growY().minHeight(0);
        ButtonGroup bg = new ButtonGroup();
        for (Button tab : tabs) {
            bg.add(tab);
            super.add(tab).width(Cons.tabButtonWidth*Gdx.graphics.getWidth()).growY().minHeight(0);
        }
        super.add(filler).grow();
    }

}
