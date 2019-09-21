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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.database.NetworkData.LoginRequest;
import de.fungistudii.kalender.main.feneric.GenericTextButton;
import de.fungistudii.kalender.main.feneric.GenericTextField;

/**
 *
 * @author sreis
 */
public class LoginScreen extends ScreenAdapter {
    private Stage stage;
    private Viewport viewport;

    private TextButton login;
    private TextButton register;
    private TextField username;
    private TextField password;

    @Override
    public void show() {
        super.show();

        createUI();
    }

    private void createUI() {
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(viewport);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        register = new GenericTextButton("Register");
        Label user = new Label("Genossentitel: ", new LStyle());
        username = new GenericTextField("username");
        Label pw = new Label("Password: ", new LStyle());
        password = new GenericTextField("pw");
        login = new GenericTextButton("Login");

        user.setFontScale(0.6f);
        pw.setFontScale(0.6f);

        register.getLabel().setFontScale(0.4f);

        root.top();
        root.add().expandX().fill().width(Value.minWidth);
        root.add(register).pad(20).right().top();
        root.row();

        Table maint = new Table();
        root.add(maint).colspan(2).padTop(140);

        maint.add(user);
        maint.row();
        maint.add(username).width(Gdx.graphics.getWidth() * 0.9f);
        maint.row().padTop(50);
        maint.add(pw);
        maint.row();
        maint.add(password).width(Gdx.graphics.getWidth() * 0.9f);

        maint.row().padTop(100);
        maint.add(login);
    }

    private void addListeners() {
        login.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LoginRequest request = new LoginRequest();
                request.username = username.getMessageText();
                request.password = password.getMessageText();
                
                ERE.client.sendTCP(request);
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

    private static class LStyle extends Label.LabelStyle {
        public LStyle() {
            super(ERE.assets.openSans, Color.BLACK);
        }
    }
}
