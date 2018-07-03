import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mixer {

    private List<Item> itemList;
    private ArrayList< Pair<Double,Material> > intervals;

    private List<Item> calcItemList;

    public void addItem(Item item) {
        if (item != null)
            itemList.add(item);
        else throw new NullPointerException();
    }



    public Mixer() {
        itemList = new LinkedList<Item>();
        intervals = new ArrayList< Pair<Double,Material> >();
        calcItemList = new LinkedList<Item>();
    }

    // make private later adter debug
    public void prepare() {
        if (itemList.size() <= 0)
            return;
        // find min / max temperature of items in system
        Pair<Double, Material> min = new Pair<Double, Material>(itemList.get(0).getTemperature(), itemList.get(0).getMaterial());
        Pair<Double, Material> max = new Pair<Double, Material>(itemList.get(0).getTemperature(), itemList.get(0).getMaterial());
        double t;
        for (Item i: itemList) {
            t = i.getTemperature();
            if (t < min.getKey())
                min = new Pair<Double, Material>(i.getTemperature(), i.getMaterial());
            if (t > max.getKey())
                max = new Pair<Double, Material>(i.getTemperature(), i.getMaterial());
        }

        intervals.add(min);
        intervals.add(max);

        // found intervals where may be answer
        for (Item i: itemList) {
            if (i.getCondenseTemperature() != null && isBetween(i.getCondenseTemperature(), min.getKey(), max.getKey()))
                intervals.add(new Pair<Double, Material>(i.getCondenseTemperature(), i.getMaterial()));
            if (i.getMeltTemperature() != null && isBetween(i.getMeltTemperature(), min.getKey(), max.getKey())) {
                intervals.add(new Pair<Double, Material>(i.getMeltTemperature(), i.getMaterial()));
            }
        }

    }

    private boolean isBetween(double value, double minValeu, double maxValue) {
        return value <= maxValue && value >= minValeu;
    }

    private void calcAdditionalEnergy(double a, double b){
        // if answer inside of interval
        double excessiveУтукпн = 0.0;
        double lackEnergy = 0.0;

        calcItemList.clear();

        if (a != b) {
            for (Item i: itemList) {

                Item tmp = i;

                if (tmp.getTemperature() < a) {
                    if (tmp.getTemperature() < tmp.getMaterial().getMeltTemperature()) {

                        if (isBetween(tmp.getMeltTemperature(), tmp.getTemperature(), a)) {
                            lackEnergy += (tmp.getMeltTemperature() - tmp.getTemperature()) * tmp.getMaterial().getHeatSolidCapacity() * i.getMass();
                            lackEnergy += tmp.getMass() * tmp.getMaterial().getMeltCapacity();
                            tmp.setTemperature(tmp.getMeltTemperature());
                        } else {
                            lackEnergy += (a - tmp.getTemperature()) * tmp.getMaterial().getHeatSolidCapacity() * i.getMass();
                            tmp.setTemperature(a);
                        }
                    }




                }
                else if (i.getTemperature() > b) {

                }

            }
        }
    }
}
