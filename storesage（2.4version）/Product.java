public class Product {
    private final String name;       // product name
    private double unitCost;   // unit price
    private boolean inCurrentProductLine; // on sale status

    // constructor
    public Product(String name, double unitCost, boolean inCurrentProductLine) {
        this.name = name;
        // price cannot be negative, add validity check
        this.unitCost = Math.max(unitCost, 0.0);
        this.inCurrentProductLine = inCurrentProductLine;
    }


    // getter method
    public double getUnitCost() {
        return unitCost;
    }

    public boolean isInCurrentProductLine() {
        return inCurrentProductLine;
    }

    // make product info easier to read
    public String toString() {
        return ("| Product Name："+name +" | Unit Price："+unitCost+" | Sale Status："+ (inCurrentProductLine ? "yes" : "no")+" | ");
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = Math.max(unitCost, 0.0);
    }

    public void setInCurrentProductLine(boolean inCurrentProductLine) {
        this.inCurrentProductLine = inCurrentProductLine;
    }
}
