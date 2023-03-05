package org.example;

public class EntitateJuridica extends Utilizator {
    private String reprezentant;

    public EntitateJuridica(String nume, String reprezentant) {
        super(nume);
        this.reprezentant = reprezentant;
    }

    public String getReprezentant() {
        return reprezentant;
    }
}
