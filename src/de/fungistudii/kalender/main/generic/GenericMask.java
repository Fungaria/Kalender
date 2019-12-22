package de.fungistudii.kalender.main.generic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import de.fungistudii.kalender.Cons;

/**
 *
 * @author sreis
 */
public class GenericMask extends GenericDialog{

    private int columns;
    private float[] columnWeights;
    private Table ttt;
    
    public GenericMask(int columns, String title) {
        super(title);
        
        ttt = new Table();
        
        this.columns = columns;
        
        columnWeights = new float[columns];
        for (int i = 0; i < this.columns; i++) {
            columnWeights[i] = 1f/columns;
        }
        
        ttt.defaults().spaceLeft(Cons.dialogHorizontalSpacing).spaceRight(Cons.dialogHorizontalSpacing);
        setMainActor(ttt);
    }
    
    public void setColumnWeights(float[] columnWeights){
        for (int i = 0; i < columnWeights.length; i++) {
            ttt.columnDefaults(i).width(Value.percentWidth(columnWeights[i], contentTable));
        }
    }
    
    public Cell add(String name, Actor actor){
        return add(new TitledWidget(name, actor));
    }
    
    public Cell add(String name, Actor actor, int colspan){
        return add(new TitledWidget(name, actor),colspan);
    }
    
    public Container addContained(String name, Actor actor){
        return addContained(new TitledWidget(name, actor));
    }
    
    public Cell add(Actor actor){
        return this.add(actor, 1);
    }
    
    public Container addContained(Actor actor){
        Container container = new Container(actor);
        container.left();
        add(container);
        return container;
    }
    
    public Cell add(Actor actor, int colspan){
        Cell res = ttt.add(actor).left().top().growX().colspan(colspan);
        
        //erste Zelle Einrücken
        if(res.getColumn() == 0){
            res.padLeft(Cons.dialogOuterPadding);
        }
        if(res.getColumn() == columns-colspan){
            res.padRight(Cons.dialogOuterPadding);
        }
        
        //Zellen mit colspan sollen nicht von der in contentTable.columnDefaults gesetzten Width beeinflusst werden
        if(colspan != 0){
            res.maxWidth(-1);
        }
        
        return res;
    }
    
    public void row(){
        ttt.row();
    }
    
    public void separator(){
        ttt.row().padTop(Cons.dialogRowPadBottom);
        ttt.add(new Image(separator)).grow().colspan(columns).height(1).maxWidth(-1);
        ttt.row().padTop(Cons.dialogRowPadTop);
    }
}
