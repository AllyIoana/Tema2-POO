package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Birou<T extends Utilizator> {
    PriorityQueue<Cerere> cereri = new PriorityQueue<Cerere>(100, new PriorityComparator());
    ArrayList<FunctionarPublic> functionari = new ArrayList<FunctionarPublic>();
    String userType;

    public Birou(String userType) {
        this.userType = userType;
    }

    public void adaugaFunctionar(String userName) {
        functionari.add(new FunctionarPublic(userName));
    }

    public void cerereNoua(Cerere cerere) {
        cereri.add(cerere);
    }

    public void stergeCerere(Date date) {
        Iterator<Cerere> it = cereri.iterator();
        while (it.hasNext()) {
            Cerere cerere = it.next();
            if (cerere.getDate().equals(date)) {
                cereri.remove(cerere);
                break;
            }
        }
    }

    public Cerere rezolvaCerere() {
        return cereri.poll();
    }

    public void afisare(PrintWriter output) {
        output.println(userType + " - cereri in birou:");
        PriorityQueue<Cerere> copie = new PriorityQueue<Cerere>(cereri);
        Cerere cerere = copie.poll();
        while (cerere != null) {
            if (cerere != null)
                output.println(cerere.getPriority() + " - " + cerere.toString());
            cerere = copie.poll();
        }
    }
}
