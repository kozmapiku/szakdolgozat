package hu.kozma.backend.models;

public enum City {
    BUDAPEST("Budapest"), DEBRECEN("Debrecen"), NYIREGYHAZA("Nyíregyháza");

    private final String name;
    City(String name) {
        this.name = name;
    }
    public String toString ()
    {
        return name;
    }
}
