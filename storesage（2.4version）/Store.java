import java.util.ArrayList;

public class Store {
    private ArrayList<Product> products;

    public Store() {
        products = new ArrayList<>();
    }

    public boolean add(Product product) {
        return products.add(product);
    }


    // Use StringBuilder to concatenate strings
    public String listProducts() {
        if (products.isEmpty()) {
            return "📦 No products in store";
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
        // Assume the first product is the cheapest
        for (Product p : products) {
            if (p.getUnitCost() < cheapest.getUnitCost()) {
                cheapest = p;
            }
        }
        return cheapest;
    }

    public String listCurrentProducts() {
        if (products.isEmpty()) {
            return "📦 No products in store";
        }
        String result=("🔥 On-Sale Products List："+"\n");
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).isInCurrentProductLine()) {
                result+=(i + 1)+(". ")+(products.get(i))+"\n";
                count++;
            }
        }
        return count == 0 ? "🚫 No on-sale products" : result;
    }

    // Optimization: return Double type, return null for empty list
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
            return "📦 No products in store";
        }
        String result=String.format("💰 Products with unit price higher than "+price+ " yuan：\n", price);
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            Product P=products.get(i);
            if (products.get(i).getUnitCost() > price) {
                result+=(i + 1)+(". ")+P+("\n");
                count++;
            }
        }
        if( count == 0){

            return("🚫 No products with unit price higher than "+price+" yuan");
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

    ArrayList<plus03.Goods> goodsList = new ArrayList<>();

    public boolean add(plus03.Goods g) {
        if (g == null) return false;
        return goodsList.add(g);
    }

    int getCount() { return goodsList.size(); }
    public plus03.Goods getGoodsByIndex(int idx) { return goodsList.get(idx); }
    ArrayList<plus03.Goods> getGoodsList() { return goodsList; }

    public int numberOfProducts() {
        return goodsList.size();
    }

    public plus03.Goods findProduct(int index) {
        if (index >= 0 && index < goodsList.size()) {
            return goodsList.get(index);
        }
        return null;
    }

    String getAllGoods() {
        if (goodsList.isEmpty()) return "No products available";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < goodsList.size(); i++) {
            sb.append((i + 1) + ". " + goodsList.get(i) + "\n");
        }
        return sb.toString();
    }

    public String getSaleGoods() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < goodsList.size(); i++) {
            plus03.Goods g = goodsList.get(i);
            if (g.isSale) {
                sb.append((i + 1) + ". " + g + "\n");
                cnt++;
            }
        }
        return cnt == 0 ? "No on-sale products" : sb.toString();
    }

    public plus03.Goods getCheapGoods() {
        if (goodsList.isEmpty()) return null;
        plus03.Goods min = goodsList.get(0);
        for (plus03.Goods g : goodsList)
            if (g.price < min.price) min = g;
        return min;
    }

    public Double getAvgPrice() {
        if (goodsList.isEmpty()) return null;
        double sum = 0;
        for (plus03.Goods g : goodsList) sum += g.price;
        return sum / goodsList.size();
    }

    public String getHighPriceGoods(double p) {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        for (int i = 0; i < goodsList.size(); i++) {
            plus03.Goods g = goodsList.get(i);
            if (g.price > p) {
                sb.append((i + 1) + ". " + g + "\n");
                cnt++;
            }
        }
        return cnt == 0 ? "No products above this price" : sb.toString();
    }

    // Delete method for testing
    public boolean removeProduct(int index) {
        if (index < 0 || index >= goodsList.size()) return false;
        goodsList.remove(index);
        return true;
    }
}
