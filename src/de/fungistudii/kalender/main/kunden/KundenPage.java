package de.fungistudii.kalender.main.kunden;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.fungistudii.kalender.main.TabPage;

/**
 *
 * @author sreis
 */
public class KundenPage extends TabPage{
    
    KSidePanel sidePanel;
    
    Table contentTable;
    
    public KundenPage() {
        sidePanel = new KSidePanel();
        contentTable = new Table();
        add(sidePanel).width(580).growY();
        add(contentTable).minSize(0).grow().pad(20, 10, 5, 10);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }
}
