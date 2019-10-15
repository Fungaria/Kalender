package de.fungistudii.kalender.main;

import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.BlockierungElement;
import de.fungistudii.kalender.main.tabs.kalender.KalenderPane.TerminElement;

/**
 *
 * @author sreis
 */
public class ContextMenuManager {

    private Array<ContextMenu> menus = new Array<>();
    
    public void init(){
        ContextMenu<TerminElement> termin = new ContextMenu<>(TerminElement.class,
            new ContextMenu.ContextEntry<>("Termin stornieren", (a) -> ERE.mainScreen.kalender.stornoDialog.show(a.getTermin())),
            new ContextMenu.ContextEntry<>("Termin bearbeiten", (a) -> {;})
        );
        
        ContextMenu<BackgroundElement> bg = new ContextMenu<>(BackgroundElement.class,
            new ContextMenu.ContextEntry<>("Termin hinzufügen", (a) -> ERE.mainScreen.kalender.addTermin()),
            new ContextMenu.ContextEntry<>("Blockierung hinzufügen", (a) -> ERE.mainScreen.kalender.blockDialog.show())
        );
        
        ContextMenu<BlockierungElement> blockierung = new ContextMenu<>(BlockierungElement.class,
            new ContextMenu.ContextEntry<>("Blockierung löschen", (a) -> ERE.mainScreen.kalender.removeBlock(a)),
            new ContextMenu.ContextEntry<>("Blockierung bearbeiten", (a) -> {;})
        );
    }
}
