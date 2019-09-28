//package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.math.Interpolation;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// *
// * @author sreis
// */
//public class TitledPane extends ScrollPane{
//
//    private Kalender kalenderNew;
//    public Kalender kalender;
//    private TestContainer container;
//    
//    private Calendar calendar;
//    
//    public TitledPane(Calendar calendar) {
//        super(null);
//        
//        kalenderNew = new Kalender(calendar);
//        kalender = new Kalender(calendar);
//        container = new TestContainer(kalenderNew, kalender);
//        kalenderNew.setVisible(false);
//        super.setActor(container);
//        
//        this.calendar = calendar;
//    }
//    
//    public void updateDate(int direction){
//        container.removeActor(kalenderNew);
//        kalenderNew = new Kalender(calendar);
//        container.add(kalenderNew);
//        kalenderNew.setVisible(false);
//        
//        if(direction == 0)
//            return;
//        
//        kalenderNew.clearActions();
//        kalender.clearActions();
//        
//        Gdx.app.postRunnable(() -> {
//            kalenderNew.setVisible(true);
//            kalenderNew.setPosition(direction*kalender.getWidth(), 0);
//            kalender.setPosition(0, 0);
//            kalenderNew.addAction(Actions.moveBy(-direction*kalender.getWidth(), 0, 0.3f, Interpolation.pow2));
//            kalender.addAction(Actions.sequence(Actions.moveBy(-direction*kalender.getWidth(), 0, 0.3f, Interpolation.pow2), Actions.hide(), Actions.run(() -> {
//                Kalender g = kalender;
//                kalender = kalenderNew;
//                kalenderNew = g;
//            })));
//        });
//    }
//    
//    public void updateCurrentTable(){
//        container.removeActor(kalender);
//        kalender = new Kalender(calendar);
//        container.add(kalender);
//    }
//    
//}
