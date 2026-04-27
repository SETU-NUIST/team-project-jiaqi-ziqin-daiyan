import java.lang.reflect.Field;

// New utility class: used to modify private properties of Product (without modifying the original Product class)
public class ProductModifier {
    // Modify product price (bypass private encapsulation via reflection)
    public static boolean modifyPrice(Product product, double newPrice) {
        if (product == null || newPrice < 0) {
            return false;
        }
        try {
            // Get the private unitCost field of the Product class
            Field costField = Product.class.getDeclaredField("unitCost");
            costField.setAccessible(true); // Allow access to private fields
            costField.set(product, newPrice); // Modify the price
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Modify product on-sale status (bypass private encapsulation via reflection)
    public static boolean modifyStatus(Product product, boolean newStatus) {
        if (product == null) {
            return false;
        }
        try {
            // Get the private inCurrentProductLine field of the Product class
            Field statusField = Product.class.getDeclaredField("inCurrentProductLine");
            statusField.setAccessible(true); // Allow access to private fields
            statusField.set(product, newStatus); // Modify the status
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}