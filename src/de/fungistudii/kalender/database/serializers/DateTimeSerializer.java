package de.fungistudii.kalender.database.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author sreis
 */
public class DateTimeSerializer extends Serializer<LocalDateTime>{

    @Override
    public void write(Kryo kryo, Output output, LocalDateTime t) {
        output.writeInt(t.getYear());
        output.writeInt(t.getMonthValue());
        output.writeInt(t.getDayOfMonth());
        output.writeInt(t.getHour());
        output.writeInt(t.getMinute());
        output.writeInt(t.getSecond());
    }

    @Override
    public LocalDateTime read(Kryo kryo, Input input, Class<LocalDateTime> type) {
        return LocalDateTime.of(input.readInt(), input.readInt(), input.readInt(), input.readInt(), input.readInt(), input.readInt());
    }
    
}