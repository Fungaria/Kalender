/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fungistudii.kalender.client.database.adapters;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author sreis
 */
public abstract class MapAdapter<T extends MapElement> extends XmlAdapter<T[], Map<Integer, T>> {

    private final IntFunction<T[]> generator;
    
    public MapAdapter(IntFunction<T[]> generator) {
        this.generator = generator;
    }

    public T[] marshal(Map<Integer, T> arg0) throws Exception {
        T[] mapElements = generator.apply(arg0.size());
        int i = 0;
        for (Map.Entry<Integer, T> entry : arg0.entrySet()) {
            mapElements[i++] = entry.getValue();
        }

        return mapElements;
    }
    
    public Map<Integer, T> unmarshal(T[] arg0) throws Exception {
        Map<Integer, T> r = new HashMap<>();
        for (T mapelement : arg0) {
            r.put(mapelement.id, mapelement);
        }
        return r;
    }
}