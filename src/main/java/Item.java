public class Item {

    private Material material;
    private Double temperature;
    private Double mass;
    private State state;

    public Item(Material material, Double temperature, Double mass) {
        this.material = material;
        this.temperature = temperature;
        this.mass = mass;

        if (temperature == material.getCondenseTemperature() || temperature == material.getMeltTemperature())
            throw new IllegalArgumentException();

        state = State.LIQUID;
        if (temperature > material.getCondenseTemperature())
            state = State.STEAM;
        if (temperature < material.getMeltTemperature())
            state = State.SOLID;
    }

    public Item(Material material, Double temperature, Double mass, State state) {
        this.material = material;
        this.temperature = temperature;
        this.mass = mass;
        this.state = state;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getMeltTemperature() {
        return material.getMeltTemperature();
    }

    public Double getCondenseTemperature() {
        return material.getCondenseTemperature();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getMass() {
        return mass;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    /**
     *
     * @param temp result temperature
     * @param MB true if need to melt or boil
     * @return lack energy of process
     */
    public double heatTo(double temp, boolean MB) {
        if (temp < temperature)
            throw new IllegalArgumentException("Result temperature must be bigger than current");

        double energy = 0.0;
        if (temp < material.getMeltTemperature()) {
            energy += (temp - temperature) * material.getHeatSolidCapacity() * mass;
        }
        if (temp >= material.getMeltTemperature() && temp < material.getCondenseTemperature()) {
            if (state.equals(State.SOLID)) {
                energy += (material.getMeltTemperature() - temperature) * material.getHeatSolidCapacity() * mass;
                setTemperature(material.getMeltTemperature());
                // if need to melt
                if (MB || temp != material.getMeltTemperature()) {
                    energy += material.getMeltCapacity() * mass;
                    setState(State.LIQUID);
                }
            }
            energy += (temp - temperature) * material.getHeatLiquidCapacity() * mass;
        }
        if (temp >= material.getCondenseTemperature()) {
            if (state.equals(State.SOLID)) {
                energy += (material.getMeltTemperature() - temperature) * material.getHeatSolidCapacity() * mass;
                energy += material.getMeltCapacity() * mass;
                setTemperature(material.getMeltTemperature());
                setState(State.LIQUID);
            }
            if (state.equals(State.LIQUID)) {
                energy += (material.getCondenseTemperature() - temperature) * material.getHeatLiquidCapacity() * mass;
                setTemperature(material.getCondenseTemperature());
                // if need to boil
                if (MB || temp != material.getCondenseTemperature()) {
                    energy += material.getCondenceCapacity() * mass;
                    setState(State.STEAM);
                }
            }
            energy += (temp - temperature) * material.getHeatSteamCapacity() * mass;
        }
        return energy;
    }

    /** {@link Item#heatTo(double, boolean)} with boolean parameter is true
     */
    public double heatTo(double temp) {
        return heatTo(temp, true);
    }

}
