package lab03.Beer;

import java.util.Comparator;

public class BeerNameComparator implements Comparator<Beer> {
    @Override
    public int compare(Beer o1, Beer o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
