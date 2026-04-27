import java.lang.reflect.Field;

// 新增工具类：用于修改Product的私有属性（不改动原有Product类）
public    class ProductModifier {
    // 修改商品价格（通过反射绕过私有封装）
    public static boolean modifyPrice(Product product, double newPrice) {
        if (product == null || newPrice < 0) {
            return false;
        }
        try {
            // 获取Product类的unitCost私有字段
            Field costField = Product.class.getDeclaredField("unitCost");
            costField.setAccessible(true); // 允许访问私有字段
            costField.set(product, newPrice); // 修改价格
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 修改商品在售状态（通过反射绕过私有封装）
    public static boolean modifyStatus(Product product, boolean newStatus) {
        if (product == null) {
            return false;
        }
        try {
            // 获取Product类的inCurrentProductLine私有字段
            Field statusField = Product.class.getDeclaredField("inCurrentProductLine");
            statusField.setAccessible(true); // 允许访问私有字段
            statusField.set(product, newStatus); // 修改状态
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}