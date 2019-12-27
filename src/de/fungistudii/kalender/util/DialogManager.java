package de.fungistudii.kalender.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import de.fungistudii.kalender.Cons;
import de.fungistudii.kalender.client.database.Service;
import de.fungistudii.kalender.client.database.Termin;
import de.fungistudii.kalender.main.tabs.kalender.AddBlockDialog;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.StornoDialog;
import de.fungistudii.kalender.main.tabs.kalender.dialog.AddAppointmentDialog;
import de.fungistudii.kalender.main.tabs.kalender.dialog.DatePickerPopup;
import de.fungistudii.kalender.main.tabs.kunden.CustomerDialog;
import de.fungistudii.kalender.main.tabs.servies.AddServiceDialog;
import java.util.Date;

/**
 *
 * @author arein
 */
public class DialogManager extends WidgetGroup {

    private final AddAppointmentDialog appointment;
    private final CustomerDialog customer;
    private final AddServiceDialog service;
    private final StornoDialog storno;
    private final AddBlockDialog block;

    private Array<Popup> openPops = new Array<>();

    private Image stageBackground;
    private boolean open = false;
    private final Vector2 tmpPosition = new Vector2();
    private final Vector2 tmpSize = new Vector2();

    public DialogManager() {
        this.appointment = new AddAppointmentDialog();
        this.customer = new CustomerDialog();
        this.service = new AddServiceDialog();
        this.storno = new StornoDialog();
        this.block = new AddBlockDialog();
        super.setFillParent(true);

        stageBackground = new Image(new DrawableSolid(new Color(0, 0, 0, 0.6f)));
        stageBackground.setWidth(5000);
        stageBackground.setHeight(5000);

        this.setTouchable(Touchable.childrenOnly);
    }

    public void showAppointment(Date date, int friseur) {
        appointment.init(date, friseur);
        appointment.show(getStage());
        openDialog(appointment);
    }

    public void showCustomer() {
        customer.show(getStage());
        openDialog(customer);
    }
    
    public void showService(Service service) {
        this.service.show(getStage(), service);
        openDialog(this.service);
    }
    
    public void showStorno(Termin termin) {
        storno.show(getStage(), termin);
        openDialog(storno);
    }
    
    public void showBlock() {
        block.show(getStage());
        openDialog(block);
    }

    public void showDatePicker(float x, float y, float width, DatePickerPopup popup) {
        popup.show(x, y, width, getStage());
        openDialog(popup);
    }
    
    private void openDialog(Popup popup) {
        for (Popup pop : openPops) {
            pop.setTouchable(Touchable.disabled);
        }
        popup.manager = this;
        openPops.add(popup);
        addActor(popup);
        updateBG();
        open = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Popup pop : openPops) {
            pop.setWidth(pop.getPrefWidth());
            pop.setHeight(pop.getPrefHeight());

            if(pop.isCentered()){
                pop.setX((getWidth() - pop.getPrefWidth()) / 2);
                pop.setY((getHeight() - pop.getPrefHeight()) / 2);
            }
        }
        
        if (openPops.size > 0) {
            openPops.peek().setTouchable(Touchable.enabled);
        }
        super.draw(batch, parentAlpha);
    }

    private void updateBG() {
        if (openPops.size == 0 && open) {
            hideBG();
            open = false;
            return;
        }
        removeActor(stageBackground);
        Popup top = null;
        for (Popup pop : openPops) {
            if (pop.isFadeBackground()) {
                top = pop;
            }
        }
        if (top != null) {
            addActorBefore(top, stageBackground);
            if (!open) {
                stageBackground.addAction(sequence(alpha(0), fadeIn(Cons.dialogFadeTime, Interpolation.fade)));
            }
        }
    }

    private void hideBG() {
        stageBackground.addAction(sequence(fadeOut(Cons.dialogFadeTime, Interpolation.fade), Actions.removeActor()));
    }

    public Drawable getStageBackground() {
        return stageBackground.getDrawable();
    }

    public void setStageBackground(Drawable stageBackground) {
        this.stageBackground.setDrawable(stageBackground);
    }

    void removePopup(Popup popup) {
        if(openPops.contains(popup, true)){
            openPops.removeValue(popup, true);
            updateBG();
        }
    }
}
