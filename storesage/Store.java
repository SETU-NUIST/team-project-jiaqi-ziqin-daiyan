import java.util.ArrayList;

public class Store {
    private ArrayList<Product> products;

    public Store() {
        products = new ArrayList<>();
    }

    public boolean add(Product product) {
        return products.add(product);
    }


    // Use StringBuilder to concatenate strings.
    public String listProducts() {
        if (products.isEmpty()) {
            return "📦 No products available in the store.";
        }
        String result=("📋 All Products List："+"\n");
        for (int i = 0; i < products.size(); i++) {
            result +=(i+1)+". "+products.get(i)+"\n";
        }
        return result;
    }

    public Product cheapestProduct() {
        if (products.isEmpty()) {
            return null;
        }
        Product cheapest = products.getFirst();
        //Assume the first product is the cheapest.
        for (Product p : products) {
            if (p.getUnitCost() < cheapest.getUnitCost()) {
                cheapest = p;
            }
        }
        return cheapest;
    }

    public String listCurrentProducts() {
        if (products.isEmpty()) {
            return "📦 No products available in the store.";
        }
        String result=("🔥 List of Products for Sale："+"\n");
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).isInCurrentProductLine()) {
                result+=(i + 1)+(". ")+(products.get(i))+"\n";
                count++;
            }
        }
        return count == 0 ? "🚫 No products available for sale." : result;
    }

    // Optimize: return a Double type, return null for an empty list.
    public Double averageProductPrice() {
        if (products.isEmpty()) {
            return null;
        }
        double total = 0;
        for (Product p : products) {
            total += p.getUnitCost();
        }
        return total / products.size();
    }

    public String listProductsAboveAPrice(double price) {
        if (products.isEmpty()) {
            return "📦 No products available in the store.";
        }
        String result=String.format("💰Product with unit price higher than"+price+ "yuan：\n", price);
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            Product P=products.get(i);
            if (products.get(i).getUnitCost() > price) {
                result+=(i + 1)+(". ")+P+("\n");
                count++;
            }
        }
        if( count == 0){

            return("🚫 No products with a unit price higher than \" + price + \" yuan available.");
        }
        return result;
    }

    public int getProductCount() {
        return products.size();
    }

    public Product getProductByIndex(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        }
        return null;

    }
}