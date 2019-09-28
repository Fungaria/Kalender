/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.account;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.GenericTextButton;

/**
 *
 * @author sreis
 */
public class ConnectionFailedScreen extends ScreenAdapter {

    private Stage stage;
    private Viewport viewport;

    private final ConnectScreen cs;

    public ConnectionFailedScreen(ConnectScreen cs) {
        this.cs = cs;
    }
    
    @Override
    public void show() {
        super.show();

        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(viewport);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label l = new Label("Failed to connect to our servers. Stop DDOS-ing them!!", new LStyle());
        l.setWrap(true);
        l.setAlignment(Align.center, Align.center);
        root.add(l).width(Gdx.graphics.getWidth()).left().fill().pad(10);

        GenericTextButton b = new GenericTextButton("Retry", new GenericTextButton.OutlineStyle());
        root.row();
        root.add(b).expandX().padTop(30);
        
        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ERE.setScreen(cs);
                cs.connect();
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.clear();
        stage.dispose();
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    static class LStyle extends Label.LabelStyle {

        public LStyle() {
            super(ERE.assets.fonts.createFont("roboto", 10), Color.BLACK);
        }
    }
}
