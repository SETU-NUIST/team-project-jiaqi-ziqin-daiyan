public class Product {
    private final String name;       // Product Name
    private double unitCost;   // Unit Price
    private boolean inCurrentProductLine; //On Sale Status

    // Constructor
    public Product(String name, double unitCost, boolean inCurrentProductLine) {
        this.name = name;
        //The price cannot be negative. Add legality validation.
        this.unitCost = Math.max(unitCost, 0.0);
        this.inCurrentProductLine = inCurrentProductLine;
    }


    // Getter Method
    public double getUnitCost() {
        return unitCost;
    }

    public boolean isInCurrentProductLine() {
        return inCurrentProductLine;
    }

    // Make product information more readable
    public String toString() {
        return ("| Product Name："+name +" | Uint Price："+unitCost+" | Sales Status："+ (inCurrentProductLine ? "yes" : "no")+" | ");
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = Math.max(unitCost, 0.0);
    }

    public void setInCurrentProductLine(boolean inCurrentProductLine) {
        this.inCurrentProductLine = inCurrentProductLine;
    }
}