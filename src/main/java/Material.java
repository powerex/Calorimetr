import java.io.Serializable;

public class Material implements Serializable {

    private long id;

    private String name;

    private Double meltTemperature;
    private Double condenseTemperature;

    private Double heatSolidCapacity;
    private Double heatLiquidCapacity;
    private Double heatSteamCapacity;

    private Double meltCapacity;
    private Double condenceCapacity;

    public Material(long id, String name, Double meltTemperature, Double condenseTemperature, Double heatSolidCapacity, Double heatLiquidCapacity, Double heatSteamCapacity, Double meltCapacity, Double condenceCapacity) {
        this.id = id;
        this.name = name;
        this.meltTemperature = meltTemperature;
        this.condenseTemperature = condenseTemperature;
        this.heatSolidCapacity = heatSolidCapacity;
        this.heatLiquidCapacity = heatLiquidCapacity;
        this.heatSteamCapacity = heatSteamCapacity;
        this.meltCapacity = meltCapacity;
        this.condenceCapacity = condenceCapacity;
    }

    @Override
    public String toString() {
        return "ID:\t" + id + "; Name:\t" + name;
    }

    public Double getMeltTemperature() {
        return meltTemperature;
    }

    public Double getCondenseTemperature() {
        return condenseTemperature;
    }

    public Double getHeatSolidCapacity() {
        return heatSolidCapacity;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getHeatLiquidCapacity() {
        return heatLiquidCapacity;
    }

    public Double getHeatSteamCapacity() {
        return heatSteamCapacity;
    }

    public Double getMeltCapacity() {
        return meltCapacity;
    }

    public Double getCondenceCapacity() {
        return condenceCapacity;
    }
}
