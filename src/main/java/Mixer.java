import javafx.util.Pair;

import java.util.*;

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

        // set for unique materials
        Set<Long> materialIdSet = new HashSet<Long>();

        // found intervals where may be answer
        for (Item i: itemList) {
            if (!materialIdSet.contains(i.getMaterial().getId())) {
                materialIdSet.add(i.getMaterial().getId());
                if (i.getCondenseTemperature() != null && isBetween(i.getCondenseTemperature(), min.getKey(), max.getKey()))
                    intervals.add(new Pair<Double, Material>(i.getCondenseTemperature(), i.getMaterial()));
                if (i.getMeltTemperature() != null && isBetween(i.getMeltTemperature(), min.getKey(), max.getKey())) {
                    intervals.add(new Pair<Double, Material>(i.getMeltTemperature(), i.getMaterial()));
                }
            }
        }

    }

    private boolean isBetween(double value, double minValeu, double maxValue) {
        return value <= maxValue && value >= minValeu;
    }

    private void calcAdditionalEnergy(double a, double b){
        // if answer inside of interval
        double excessiveEnergy = 0.0;
        double lackEnergy = 0.0;

        calcItemList.clear();

        if (a != b) {
            for (Item i: itemList) {

                Item tmp = i;

                if (tmp.getTemperature() < a) {
                    lackEnergy += tmp.heatTo(a);
                }
                else if (i.getTemperature() > b) {
                    // material still steam just cooling
                    if (b > tmp.getMaterial().getCondenseTemperature()) {
                        excessiveEnergy += (tmp.getTemperature() - b) * tmp.getMaterial().getHeatLiquidCapacity() * tmp.getMass();
                    }
                    else if (b == tmp.getMaterial().getCondenseTemperature()) {
                        // cooling to condence temperature
                        excessiveEnergy += (tmp.getTemperature() - b) * tmp.getMaterial().getHeatLiquidCapacity() * tmp.getMass();
                        // condence
                        excessiveEnergy += tmp.getMaterial().getCondenceCapacity() * tmp.getMass();
                        tmp.setState(State.LIQUID);
                    }
                    //else if () {}
                }

            }
        }
    }
}
