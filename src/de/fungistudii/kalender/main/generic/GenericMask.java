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
    private Table contentTable;
    
    public GenericMask(int columns, String title) {
        super(title);
        
        super.prefWidth(900);
        
        contentTable = new Table();
        this.columns = columns;
        
        columnWeights = new float[columns];
        for (int i = 0; i < this.columns; i++) {
            columnWeights[i] = 1f/columns;
        }
        
        contentTable.defaults().spaceLeft(Cons.dialogHorizontalSpacing).spaceRight(Cons.dialogHorizontalSpacing);
        setMainActor(contentTable);
    }
    
    public void setColumnWeights(float[] columnWeights){
        for (int i = 0; i < columnWeights.length; i++) {
            contentTable.columnDefaults(i).width(Value.percentWidth(columnWeights[i], this));
        }
    }
    
    public Cell addC(String name, Actor actor){
        return addC(new TitledWidget(name, actor));
    }
    
    public Cell addC(String name, Actor actor, int colspan){
        return addC(new TitledWidget(name, actor),colspan);
    }
    
    public Container addContained(String name, Actor actor){
        return addContained(new TitledWidget(name, actor));
    }
    
    public Cell addC(Actor actor){
        return this.addC(actor, 1);
    }
    
    public Container addContained(Actor actor){
        Container container = new Container(actor);
        container.left();
        add(container);
        return container;
    }
    
    public Cell addC(Actor actor, int colspan){
        Cell res = contentTable.add(actor).left().top().growX().colspan(colspan);
        
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
    
    public void rowC(){
        contentTable.row();
    }
    
    public void separator(){
        contentTable.row().padTop(Cons.dialogRowPadBottom);
        contentTable.add(new Image(separator)).grow().colspan(columns).height(1).maxWidth(-1);
        contentTable.row().padTop(Cons.dialogRowPadTop);
    }
}
