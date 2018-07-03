public class Item {

    private Material material;
    private Double temperature;
    private Double mass;

    public Item(Material material, Double temperature, Double mass) {
        this.material = material;
        this.temperature = temperature;
        this.mass = mass;
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
}
