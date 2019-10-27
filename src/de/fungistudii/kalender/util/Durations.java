package de.fungistudii.kalender.util;

/**
 *
 * @author sreis
 */
public enum Durations {

    d15("0:15", 15),
    d30("0:30", 30),
    d45("0:45", 45),
    d60("1:00", 60),
    d75("1:15", 70),
    d90("1:30", 90),
    d105("1:45", 105),
    d120("2:00", 120),
    d135("2:15", 135),
    d150("2:30", 150),
    d165("2:45", 165),
    d180("3:00", 180);

    private String name;
    private int minutes;

    private Durations(String name, int minutes) {
        this.name = name;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
