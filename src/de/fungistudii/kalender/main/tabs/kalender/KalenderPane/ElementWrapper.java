package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.util.DateUtil;
import de.fungistudii.kalender.util.value.ValueUtil;

/**
 *
 * @author sreis
 */
public class ElementWrapper<T extends GridElement> extends Table {

    public T element;

    private ValueUtil.PercentValue height;
    
    public ElementWrapper(T element, Value elementHeight) {
        super();
        this.element = element;

        height = ValueUtil.percentValue(element.getSpan(), elementHeight);
        
        int startCell = DateUtil.getMinuteOfDay(element.getStart()) - KalenderTable.startTime * 60;
        super.add(this.element).height(height).grow().fillY().padTop(ValueUtil.percentValue(startCell / 15, elementHeight)).top();
        
        setTouchable(Touchable.childrenOnly);
    }

    @Override
    public void invalidate() {
        height.setPercent(element.getSpan());
        super.invalidate(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public T get(){
        return element;
    }
}