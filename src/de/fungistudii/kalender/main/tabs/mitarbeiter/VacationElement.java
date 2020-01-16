/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.mitarbeiter;

import de.fungistudii.kalender.main.generic.TwoColorLabel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.client.database.Vacation;
import de.fungistudii.kalender.main.tabs.kalender.dialog.DatePickerPopup;
import de.fungistudii.kalender.util.NinePatchSolid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author sreis
 */
public class VacationElement extends Table {

    private DatePickerPopup navigator;
    private final Vector2 tmpVec = new Vector2();

    private final Button von;
    private final Button bis;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd'. 'MMMMM' 'yyyy");

    private final Vacation vacation;
    private final int workerId;

    int open;
    
    public VacationElement(int workerId, Vacation vacation) {
        this.vacation = vacation;
        this.workerId = workerId;

        BitmapFont font = ERE.assets.fonts.createFont("roboto", 14);
        SpriteDrawable separator = ERE.assets.createDrawable("generic/vertical_separator");

        ImageButton.ImageButtonStyle deleteStyle = new ImageButton.ImageButtonStyle();
        deleteStyle.imageUp = ERE.assets.createDrawable("generic/thrash");
        deleteStyle.up = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 6, ERE.assets.grey1);
        deleteStyle.over = ERE.assets.createNinePatchDrawable("generic/rounded_filled_right", 6, ERE.assets.grey2);
        ImageButton delete = new ImageButton(deleteStyle);
        delete.getImageCell().pad(5);

        von = new Button(ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 6, ERE.assets.grey1));
        von.getStyle().over = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 6, ERE.assets.grey2);
        von.getStyle().checked = ERE.assets.createNinePatchDrawable("generic/rounded_filled_left", 6, ERE.assets.grey3);
        TwoColorLabel vonLabel = new TwoColorLabel("Von: ", font, ERE.assets.grey5, dateFormat.format(vacation.start), font, Color.BLACK);
        von.add(vonLabel).pad(0, 7, 0, 12);

        bis = new Button(new NinePatchSolid(ERE.assets.grey1));
        bis.getStyle().over = new NinePatchSolid(ERE.assets.grey2);
        bis.getStyle().checked = new NinePatchSolid(ERE.assets.grey3);
        TwoColorLabel bisLabel = new TwoColorLabel("Bis: ", font, ERE.assets.grey5, dateFormat.format(vacation.end), font, Color.BLACK);
        bis.add(bisLabel).pad(0, 7, 0, 12);

        Image separator1 = new Image(separator);
        Image separator2 = new Image(separator);

        super.add(von).grow().minSize(0).uniform();
        super.add(separator1).width(1).pad(5, -1, 5, -1);
        super.add(bis).grow().minSize(0).uniform();
        super.add(separator2).width(1).pad(5, -1, 5, -1);
        super.add(delete).minSize(0).maxWidth(Value.percentHeight(1.2f, this));
        separator1.setZIndex(100);
        separator2.setZIndex(100);

        final RangeSelectBehavior sh = new RangeSelectBehavior(workerId, vacation.id, vacation.start, vacation.end);
        navigator = new DatePickerPopup((date, dir) -> {
            if (sh.isSelectBegin()) {
                vonLabel.setRightText(dateFormat.format(date));
            } else {
                bisLabel.setRightText(dateFormat.format(date));
            }
            requestVacation(sh.getBeginDate(), sh.getEndDate());
        });
        navigator.navigation.setSelectBehavior(sh);
        navigator.navigation.setHoverBehavior(sh);
        sh.updateSelection(navigator.navigation.getDateButtons());
        navigator.onHide = () -> {von.setChecked(false);bis.setChecked(false);};
        
        von.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                navigator.openButton = von;
                sh.setSelectBegin(true);
                sh.updateSelection(navigator.navigation.getDateButtons());
                von.localToStageCoordinates(tmpVec.set(0, 0));
                showNavigator(x, y);
                navigator.navigation.setDate(sh.getBeginDate());
            }
        });
        bis.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                navigator.openButton = bis;
                sh.setSelectBegin(false);
                sh.updateSelection(navigator.navigation.getDateButtons());
                bis.localToStageCoordinates(tmpVec.set(0, 0));
                showNavigator(x, y);
                navigator.navigation.setDate(sh.getEndDate());
            }
        });

        delete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                requestRemove();
            }
        });
    }

    private void requestRemove() {
        NetworkData.RemoveVacationRequest delete = new NetworkData.RemoveVacationRequest();
        delete.id = this.vacation.id;
        ERE.client.sendTCP(delete);
    }

    private void requestVacation(Date start, Date end) {
        NetworkData.EditVacationRequest request = new NetworkData.EditVacationRequest();
        request.start = start;
        request.end = end;
        request.workerId = workerId;
        request.id = vacation.id;
        ERE.client.sendTCP(request);
    }

    private void showNavigator(float x, float y) {
        navigator.show(tmpVec.x, tmpVec.y - 10, 250);
    }

    @Override
    public float getPrefHeight() {
        return 50;
    }
}
