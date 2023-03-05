package org.example;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Cerere> {
    public int compare(Cerere cerere1, Cerere cerere2) {
        if (cerere1.getPriority() == cerere2.getPriority())
            return cerere1.getDate().compareTo(cerere2.getDate());
        else if (cerere1.getPriority() > cerere2.getPriority())
            return -1;
        else return 1;
    }
}
