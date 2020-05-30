///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package de.fungistudii.kalender.main.mitarbeiter;
//
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
//import com.badlogic.gdx.scenes.scene2d.ui.Container;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.Value;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//import static de.fungistudii.kalender.Main.ERE;
//import de.fungistudii.kalender.main.generic.GenericTextButton;
//import de.fungistudii.kalender.util.DrawableSolid;
//
///**
// *
// * @author sreis
// */
//public class SidePanel extends Container {
//
//    private GenericTextButton urlaub;
//    private GenericTextButton services;
//    private GenericTextButton zeiten;
//    
//    private Table content;
//    
//    private WorkerHeader header;
//    
//    private ButtonGroup<GenericTextButton> group;
//    
//    public SidePanel(MitarbeiterPage parent) {
//        super.setBackground(new DrawableSolid(ERE.assets.grey1));
//        super.top();
//        super.fill();
//        
//        group = new ButtonGroup<>();
//        
//        content = new Table();
//        urlaub = new GenericTextButton("Urlaub", new GenericTextButton.FilledStyle());
//        urlaub.getStyle().checked = urlaub.getStyle().down;
//        group.add(urlaub);
//        services = new GenericTextButton("Services", new GenericTextButton.FilledStyle());
//        services.getStyle().checked = urlaub.getStyle().down;
//        group.add(services);
//        zeiten = new GenericTextButton("Arbeitszeiten", new GenericTextButton.FilledStyle());
//        zeiten.getStyle().checked = urlaub.getStyle().down;
//        group.add(zeiten);
//        Drawable separator = ERE.assets.createDrawable("generic/vertical_separator");
//        
//        
//        header = new WorkerHeader((i) -> {
//            parent.setMitarbeiter(i);
//        });
//        
//        content.defaults().space(10);
//        
//        content.row();
//        content.add(header).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
//        content.row();
//        content.add(urlaub).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
//        content.row();
//        content.add(services).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
//        content.row();
//        content.add(zeiten).minSize(0).growX().prefHeight(Value.percentWidth(0.16f, this));
//        content.row();
//        content.add().grow();
//        
//        content.pad(Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this), Value.percentWidth(0.05f, this), Value.percentWidth(0.1f, this));
//        setActor(content);
//        
//        urlaub.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//                parent.setMode(0);
//            }
//        });
//        services.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//                parent.setMode(1);
//            }
//        });
//        zeiten.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y) {
//                parent.setMode(2);
//            }
//        });
//    }
//}
