/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.database.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.time.LocalDate;

/**
 *
 * @author sreis
 */
public class DateSerializer extends Serializer<LocalDate>{

    @Override
    public void write(Kryo kryo, Output output, LocalDate t) {
        output.writeInt(t.getYear());
        output.writeInt(t.getMonthValue());
        output.writeInt(t.getDayOfMonth());
    }

    @Override
    public LocalDate read(Kryo kryo, Input input, Class<LocalDate> type) {
        return LocalDate.of(input.readInt(), input.readInt(), input.readInt());
    }
    
}
