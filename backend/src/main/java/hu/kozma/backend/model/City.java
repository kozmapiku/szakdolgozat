package hu.kozma.backend.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum City {
    BUDAPEST("Budapest"), DEBRECEN("Debrecen"), NYIREGYHAZA("Nyíregyháza");

    private static final Map<String, City> map;

    City(String name) {
        this.name = name;
    }

    static {
        map = new HashMap<>();
        for (City c : City.values()) {
            map.put(c.name, c);
        }
    }

    @Getter
    private final String name;

    public static City findByName(String name) {
        return map.get(name);
    }

    public String toString() {
        return name;
    }
}
