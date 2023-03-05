package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public abstract class Utilizator {
    private String nume;
    private ArrayList<Cerere> waiting = new ArrayList<Cerere>();
    private ArrayList<Cerere> done = new ArrayList<Cerere>();

    public Utilizator(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public String scriereCerere(String tip) throws WrongUser {
        TipuriDeCerere tipDeCerere;

        /* dacă o persoană nu poate face o anumită cerere */
        switch (tip) {
            case "inlocuire buletin":
                tipDeCerere = TipuriDeCerere.inlocuireBuletin;
                if (this instanceof EntitateJuridica)
                    throw new WrongUser("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip);
                break;
            case "inregistrare venit salarial":
                tipDeCerere = TipuriDeCerere.venitSalarial;
                if (this instanceof Persoana)
                    throw new WrongUser("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Pensionar)
                    throw new WrongUser("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Elev)
                    throw new WrongUser("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip);
                else if (this instanceof EntitateJuridica)
                    throw new WrongUser("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip);
                break;
            case "inlocuire carnet de sofer":
                tipDeCerere = TipuriDeCerere.carnetSofer;
                if (this instanceof Elev)
                    throw new WrongUser("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip);
                else if (this instanceof EntitateJuridica)
                    throw new WrongUser("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip);
                break;
            case "inlocuire carnet de elev":
                tipDeCerere = TipuriDeCerere.carnetElev;
                if (this instanceof Persoana)
                    throw new WrongUser("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Pensionar)
                    throw new WrongUser("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Angajat)
                    throw new WrongUser("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tip);
                else if (this instanceof EntitateJuridica)
                    throw new WrongUser("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip);
                break;
            case "creare act constitutiv":
                tipDeCerere = TipuriDeCerere.actConstitutiv;
                if (this instanceof Persoana)
                    throw new WrongUser("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Pensionar)
                    throw new WrongUser("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Elev)
                    throw new WrongUser("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Angajat)
                    throw new WrongUser("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tip);
                break;
            case "reinnoire autorizatie":
                tipDeCerere = TipuriDeCerere.autorizatie;
                if (this instanceof Persoana)
                    throw new WrongUser("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Pensionar)
                    throw new WrongUser("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Elev)
                    throw new WrongUser("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Angajat)
                    throw new WrongUser("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tip);
                break;
            case "inregistrare cupoane de pensie":
                tipDeCerere = TipuriDeCerere.cupoanePensie;
                if (this instanceof Persoana)
                    throw new WrongUser("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Angajat)
                    throw new WrongUser("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tip);
                else if (this instanceof Elev)
                    throw new WrongUser("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tip);
                else if (this instanceof EntitateJuridica)
                    throw new WrongUser("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tip);
                break;
        }

        /* scrierea propriu-zisă a textului cererii */
        if (this instanceof Angajat)
            return "Subsemnatul " + this.nume + ", angajat la compania " + ((Angajat) this).getCompanie() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip;
        else if (this instanceof Elev)
            return "Subsemnatul " + this.nume + ", elev la scoala " + ((Elev) this).getScoala() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip;
        else if (this instanceof EntitateJuridica)
            return "Subsemnatul " + ((EntitateJuridica) this).getReprezentant() + ", reprezentant legal al companiei " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip;
        else
            return "Subsemnatul " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: " + tip;
    }

    public void cerereNoua(Cerere cerere) {
        waiting.add(cerere);
        Collections.sort(waiting, new DateComparator());
    }

    public void cereriInAsteptare(PrintWriter output) {
        int i;
        for (i = 0; i < waiting.size(); i++) {
            output.println(waiting.get(i).toString());
        }
    }

    public void cereriFinalizate(PrintWriter output) {
        int i;
        for (i = 0; i < done.size(); i++) {
            output.println(done.get(i).toString());
        }
    }

    public void stergeCerere(Date date) {
        int i;
        for (i = 0; i < waiting.size(); i++) {
            if (waiting.get(i).getDate().equals(date)) {
                waiting.remove(i);
                break;
            }
        }
    }

    public void rezolvaCerere(Cerere cerere) {
        int i;
        for (i = 0; i < waiting.size(); i++) {
            if(waiting.get(i) == cerere){
                waiting.remove(i);
                done.add(cerere);
            }
        }
        Collections.sort(done, new DateComparator());
    }
}
