package de.fungistudii.kalender.main;

import com.badlogic.gdx.utils.Array;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.ContextMenu;
import de.fungistudii.kalender.main.kalender.KalenderPane.BackgroundElement;
import de.fungistudii.kalender.main.kalender.KalenderPane.BlockElement;
import de.fungistudii.kalender.main.kalender.KalenderPane.TerminElement;
import de.fungistudii.kalender.main.kalender.KalenderPane.ElementWrapper;

/**
 *
 * @author sreis
 */
public class ContextMenuManager {

    private Array<ContextMenu> menus = new Array<>();
    
    public void init(){
        ContextMenu<BlockElement> blockierung = new ContextMenu<>(BlockElement.class,
            new ContextMenu.ContextEntry<>("Blockierung löschen", (a) -> ERE.mainScreen.kalender.removeBlock(a)),
            new ContextMenu.ContextEntry<>("Blockierung bearbeiten", (a) -> {;})
        );
        ContextMenu<TerminElement> termin = new ContextMenu<>(TerminElement.class,
            new ContextMenu.ContextEntry<>("Termin stornieren", (a) -> ERE.mainScreen.dialogManager.showStorno(a.getTermin())),
            new ContextMenu.ContextEntry<>("Termin bearbeiten", (a) -> {;}),
            new ContextMenu.ContextEntry<>("Kundeninfo", a -> ERE.mainScreen.dialogManager.showViewCustomer(a.getTermin().kundenid))
        );
        
        ContextMenu<BackgroundElement> bg = new ContextMenu<>(BackgroundElement.class,
            new ContextMenu.ContextEntry<>("Termin hinzufügen", (a) -> ERE.mainScreen.kalender.addTermin()),
            new ContextMenu.ContextEntry<>("Blockierung hinzufügen", (a) -> ERE.mainScreen.dialogManager.showBlock())
        );
    }
}
