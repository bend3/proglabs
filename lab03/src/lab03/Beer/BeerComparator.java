package lab03.Beer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeerComparator implements Comparator<Beer> {
    enum Property {Name , Style, Strength}
    private final String[] args;
    public BeerComparator(String... args) {
        this.args = args;
    }

    private int sortBy(Property property, Beer o1, Beer o2){
        if (property == Property.Name){
            return new BeerNameComparator().compare(o1, o2);
        }
        if (property == Property.Style){
            return new BeerStyleComparator().compare(o1, o2);
        }
        if (property == Property.Strength){
            return new BeerStrengthComparator().compare(o1, o2);
        }
        return 0;
    }

    @Override
    public int compare(Beer o1, Beer o2) {
        ArrayList<Property> sortOrder = new ArrayList<>();
        for (String arg: args) {
            if (arg.equals("name")){
                sortOrder.add(Property.Name);
            } else if (arg.equals("style")){
                sortOrder.add(Property.Style);
            } else if (arg.equals("strength")){
                sortOrder.add(Property.Strength);
            }
        }
        for (Property property: sortOrder) {
            if (sortBy(property, o1, o2) != 0){
                return sortBy(property, o1, o2);
            }
        }
        return 0;
    }
}
