/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.main.tabs.kalender.KalenderPane;

import de.fungistudii.kalender.client.database.Blockierung;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author sreis
 */
public class BlockierungElement extends BlockActor {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH':'mm");

    private static final Calendar calendar = Calendar.getInstance();

    private Blockierung blockierung;

    public BlockierungElement(Blockierung blockierung) {
        super(blockierung.start, blockierung.duration, blockierung.friseur, blockierung.msg);
        this.blockierung = blockierung;

    }
    public Blockierung getBlockierung() {
        return blockierung;
    }
}
