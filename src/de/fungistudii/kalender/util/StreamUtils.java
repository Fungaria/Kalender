package de.fungistudii.kalender.util;

import com.badlogic.gdx.utils.Array;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author sreis
 */
public class StreamUtils {

    public static <T> Stream<T> stream(Array<T> array) {
        Spliterator<T> spliterator = Spliterators.spliterator(array.iterator(), array.size, 0);
        return StreamSupport.stream(spliterator, false);
    }
    
}
