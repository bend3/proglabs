package lab03.Beer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeerComparator implements Comparator<Beer> {
    enum Property {Name , Style, Strength}
    private final String[] args;
    public BeerComparator(String[] args) {
        this.args = args;
    }

    private int sortBy(Property property, Beer o1, Beer o2){
        if (property == Property.Name){
            return o1.getName().compareTo(o2.getName());
        }
        if (property == Property.Style){
            return o1.getStyle().compareTo(o2.getStyle());
        }
        if (property == Property.Strength){
            return Double.compare(o1.getStrength(), o2.getStrength());
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
