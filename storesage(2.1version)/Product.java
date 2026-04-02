public class Product {
    private final String name;       // 商品名称
    private double unitCost;   // 单价
    private boolean inCurrentProductLine; // 是否在售

    // 构造方法
    public Product(String name, double unitCost, boolean inCurrentProductLine) {
        this.name = name;
        // 价格不能为负数，增加合法性校验
        this.unitCost = Math.max(unitCost, 0.0);
        this.inCurrentProductLine = inCurrentProductLine;
    }


    // getter方法
    public double getUnitCost() {
        return unitCost;
    }

    public boolean isInCurrentProductLine() {
        return inCurrentProductLine;
    }

    // 让商品信息更易读
    public String toString() {
        return ("| 商品名称："+name +" | 单价："+unitCost+" | 在售状态："+ (inCurrentProductLine ? "yes" : "no")+" | ");
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = Math.max(unitCost, 0.0);
    }

    public void setInCurrentProductLine(boolean inCurrentProductLine) {
        this.inCurrentProductLine = inCurrentProductLine;
    }
}