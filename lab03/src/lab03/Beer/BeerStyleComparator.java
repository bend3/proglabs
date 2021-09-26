package lab03.Beer;

import java.util.Comparator;

public class BeerStyleComparator implements Comparator<Beer> {
    @Override
    public int compare(Beer o1, Beer o2) {
        return o1.getStyle().compareTo(o2.getStyle());
    }
}
