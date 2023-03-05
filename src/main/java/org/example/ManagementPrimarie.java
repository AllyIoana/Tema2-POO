package org.example;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManagementPrimarie {
    ArrayList<Utilizator> utilizatori = new ArrayList<Utilizator>();
    Birou<Persoana> birouPers = new Birou<Persoana>("persoana");
    Birou<Angajat> birouAng = new Birou<Angajat>("angajat");
    Birou<Pensionar> birouPens = new Birou<Pensionar>("pensionar");
    Birou<EntitateJuridica> birouEnt = new Birou<EntitateJuridica>("entitate juridica");
    Birou<Elev> birouElev = new Birou<Elev>("elev");

    public static void main(String[] args) throws IOException, ParseException {
        ManagementPrimarie management = new ManagementPrimarie();

        BufferedReader input = new BufferedReader(new FileReader("src/main/resources/input/" + args[0]));
        PrintWriter output = new PrintWriter(new FileWriter("src/main/resources/output/" + args[0]));
        String line;
        File[] fisiereFunctionari = new File("src/main/resources/output/").listFiles();
        int i;
        for (i = 0; i < fisiereFunctionari.length; i++)
            if (fisiereFunctionari[i].getName().startsWith("functionar"))
                fisiereFunctionari[i].delete();

        while ((line = input.readLine()) != null) {
            String[] comp = line.split("; ");
            switch (comp[0]) {
                case "adauga_utilizator":
                    management.adaugaUtilizator(comp);
                    break;
                case "cerere_noua":
                    management.cerereNoua(comp, output);
                    break;
                case "afiseaza_cereri_in_asteptare":
                    management.cereriInAsteptare(comp[1], output);
                    break;
                case "afiseaza_cereri_finalizate":
                    management.cereriFinalizate(comp[1], output);
                    break;
                case "retrage_cerere":
                    management.stergeCerere(comp[1], comp[2]);
                    break;
                case "rezolva_cerere":
                    management.rezolvaCerere(comp[1], comp[2]);
                    break;
                case "afiseaza_cereri":
                    management.afiseazaCereri(comp[1], output);
                    break;
                case "adauga_functionar":
                    management.adaugaFunctionar(comp[1], comp[2]);
                    break;
            }
        }
        output.close();
    }

    public void adaugaFunctionar(String tip, String userName) {
        switch (tip) {
            case "persoana":
                birouPers.adaugaFunctionar(userName);
                break;
            case "elev":
                birouElev.adaugaFunctionar(userName);
                break;
            case "angajat":
                birouAng.adaugaFunctionar(userName);
                break;
            case "entitate juridica":
                birouEnt.adaugaFunctionar(userName);
                break;
            case "pensionar":
                birouPens.adaugaFunctionar(userName);
                break;
        }
    }

    public void afiseazaCereri(String tip, PrintWriter output) {
        switch (tip) {
            case "persoana":
                birouPers.afisare(output);
                break;
            case "elev":
                birouElev.afisare(output);
                break;
            case "angajat":
                birouAng.afisare(output);
                break;
            case "entitate juridica":
                birouEnt.afisare(output);
                break;
            case "pensionar":
                birouPens.afisare(output);
                break;
        }
    }

    public void rezolvaCerere(String tip, String userName) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter("src/main/resources/output/functionar_" + userName + ".txt", true));
            Cerere cerere = null;
            if (tip.equals("persoana"))
                cerere = birouPers.rezolvaCerere();
            else if (tip.equals("elev"))
                cerere = birouElev.rezolvaCerere();
            else if (tip.equals("pensionar"))
                cerere = birouPens.rezolvaCerere();
            else if (tip.equals("entitate juridica"))
                cerere = birouEnt.rezolvaCerere();
            else if (tip.equals("angajat"))
                cerere = birouAng.rezolvaCerere();
            if (cerere != null) {
                cerere.getUtilizator().rezolvaCerere(cerere);
                String dateFormat = "dd-MMM-yyy HH:mm:ss";
                String date = new SimpleDateFormat(dateFormat).format(cerere.getDate());
                output.println(date + " - " + cerere.getUtilizator().getNume());
            }
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void adaugaUtilizator(String[] comp) {
        switch (comp[1]) {
            case "angajat":
                Angajat angajat = new Angajat(comp[2], comp[3]);
                utilizatori.add(angajat);
                break;
            case "elev":
                Elev elev = new Elev(comp[2], comp[3]);
                utilizatori.add(elev);
                break;
            case "pensionar":
                Pensionar pensionar = new Pensionar(comp[2]);
                utilizatori.add(pensionar);
                break;
            case "entitate juridica":
                EntitateJuridica entitateJuridica = new EntitateJuridica(comp[2], comp[3]);
                utilizatori.add(entitateJuridica);
                break;
            case "persoana":
                Persoana persoana = new Persoana(comp[2]);
                utilizatori.add(persoana);
                break;
        }
    }

    private void cerereNoua(String[] comp, PrintWriter output) {
        int i;
        for (i = 0; i < utilizatori.size(); i++) {
            if (utilizatori.get(i).getNume().equals(comp[1])) {
                try {
                    String textCerere = utilizatori.get(i).scriereCerere(comp[2]);
                    String dateFormat = "dd-MMM-yyy HH:mm:ss";
                    Date date = new SimpleDateFormat(dateFormat).parse(comp[3]);
                    Cerere cerere = new Cerere(textCerere, date, Integer.parseInt(comp[4]), utilizatori.get(i));
                    utilizatori.get(i).cerereNoua(cerere);
                    if (utilizatori.get(i) instanceof Persoana) {
                        birouPers.cerereNoua(cerere);
                    } else if (utilizatori.get(i) instanceof Angajat) {
                        birouAng.cerereNoua(cerere);
                    } else if (utilizatori.get(i) instanceof EntitateJuridica) {
                        birouEnt.cerereNoua(cerere);
                    } else if (utilizatori.get(i) instanceof Elev) {
                        birouElev.cerereNoua(cerere);
                    } else if (utilizatori.get(i) instanceof Pensionar) {
                        birouPens.cerereNoua(cerere);
                    }
                } catch (WrongUser user) {
                    output.println(user.getMessage());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void cereriInAsteptare(String userName, PrintWriter output) {
        int i;
        for (i = 0; i < utilizatori.size(); i++) {
            if (utilizatori.get(i).getNume().equals(userName)) {
                output.println(userName + " - cereri in asteptare:");
                utilizatori.get(i).cereriInAsteptare(output);
            }
        }
    }

    private void cereriFinalizate(String userName, PrintWriter output) {
        int i;
        for (i = 0; i < utilizatori.size(); i++) {
            if (utilizatori.get(i).getNume().equals(userName)) {
                output.println(userName + " - cereri in finalizate:");
                utilizatori.get(i).cereriFinalizate(output);
            }
        }
    }

    public void stergeCerere(String userName, String givenDate) {
        int i;
        for (i = 0; i < utilizatori.size(); i++) {
            if (utilizatori.get(i).getNume().equals(userName)) {
                try {
                    String dateFormat = "dd-MMM-yyy HH:mm:ss";
                    Date date = new SimpleDateFormat(dateFormat).parse(givenDate);
                    utilizatori.get(i).stergeCerere(date);
                    if (utilizatori.get(i) instanceof Persoana) {
                        birouPers.stergeCerere(date);
                    } else if (utilizatori.get(i) instanceof Angajat) {
                        birouAng.stergeCerere(date);
                    } else if (utilizatori.get(i) instanceof EntitateJuridica) {
                        birouEnt.stergeCerere(date);
                    } else if (utilizatori.get(i) instanceof Elev) {
                        birouElev.stergeCerere(date);
                    } else if (utilizatori.get(i) instanceof Pensionar) {
                        birouPens.stergeCerere(date);
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}