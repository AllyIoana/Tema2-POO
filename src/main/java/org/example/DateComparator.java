package org.example;

import java.util.Comparator;

public class DateComparator implements Comparator<Cerere> {

    @Override
    public int compare(Cerere cerere1, Cerere cerere2) {
        return cerere1.getDate().compareTo(cerere2.getDate());
    }
}
