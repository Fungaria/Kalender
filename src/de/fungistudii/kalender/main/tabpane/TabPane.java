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
import de.fungistudii.kalender.util.DrawableSolid;
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
    private final TabButton services;
    private final Image filler;
    
    public TabPane() {
        super();
        
        ButtonGroup bg = new ButtonGroup();
        
        orplid = new Logo();
        orplid.setDisabled(true);
        kalender = new TabButton("Kalender");
        kunden = new TabButton("Kunden");
        mitarbeiter = new TabButton("Mitarbeiter");
        produkte = new TabButton("Produkte");
        services = new TabButton("Services");
        filler = new Image(new DrawableSolid(ERE.assets.tabGrey));
        
        tabs.add(kalender);
        tabs.add(kunden);
        tabs.add(mitarbeiter);
        tabs.add(produkte);
        tabs.add(services);
        
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
        services.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ERE.mainScreen.setTab(ERE.mainScreen.service);
            }
        });
        
        super.setRound(false);
        

        super.align(Align.left);
        super.add(orplid).width(Cons.sideBarWidth).fill();
        for (Button tab : tabs) {
            bg.add(tab);
            super.add(tab).prefWidth(Value.percentWidth(Cons.tabButtonWidth, this)).fill();
        }
        super.add(filler).grow();
    }
}