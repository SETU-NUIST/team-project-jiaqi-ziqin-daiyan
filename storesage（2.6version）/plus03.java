import javax.swing.*;
import java.util.ArrayList;

public class plus03 {
    static Store shop = new Store();

    public static void main(String[] args) {
        initGoods();
        while (true) {
            String[] menu = {
                    "1. Add Product",
                    "2. View All Products",
                    "3. Find Cheapest Product",
                    "4. View On-Sale Products",
                    "5. Calculate Average Price",
                    "6. View High-Price Products",
                    "7. Edit Product",
                    "8. Delete Product",
                    "9. Search Product",
                    "10. Exit"
            };
            String choose = (String) JOptionPane.showInputDialog(
                    null,
                    "Store Product Management System\nPlease select an operation:",
                    "Menu",
                    3,
                    null,
                    menu,
                    menu[0]
            );
            if (choose == null) {
                if (JOptionPane.showConfirmDialog(null, "Confirm exit?", "Tip", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "Thank you for using!");
                    break;
                } else {
                    continue;
                }
            }
            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);
            doMenu(ch);
            if (ch == 10) {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break;
            }
        }
    }

    static void initGoods() {
        shop.add(new Goods("Apple", 2.99, true));
        shop.add(new Goods("Banana", 3.99, true));
        shop.add(new Goods("Orange", 4.59, true));
        shop.add(new Goods("Milk", 5.99, true));
        shop.add(new Goods("Bread", 6.99, true));
    }

    static void doMenu(int ch) {
        switch (ch) {
            case 1: addGoods(); break;
            case 2: showAllGoods(); break;
            case 3: findCheap(); break;
            case 4: showSaleGoods(); break;
            case 5: calcAvgPrice(); break;
            case 6: showHighPrice(); break;
            case 7: editGoods(); break;
            case 8: deleteGoods(); break;
            case 9: searchGoods(); break;
            case 10: break;
            default: JOptionPane.showMessageDialog(null, "Invalid option!");
        }
    }


    // --- Function 1 ---
    static void addGoods() {
        String name = JOptionPane.showInputDialog("Enter product name (Please note that the first letter should be capitalized):");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!");
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("Enter product price:"));
            if (price < 0) {
                JOptionPane.showMessageDialog(null, "Price cannot be negative!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price input error!");
            return;
        }
        int sale = JOptionPane.showConfirmDialog(null, "On sale?");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "Added successfully!");
    }

    // --- Function 2 ---
    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "All Products", 1);
    }

    // --- Function 3 ---
    static void findCheap() {
        Goods cheap = shop.getCheapGoods();
        if (cheap == null) JOptionPane.showMessageDialog(null, "No products!");
        else JOptionPane.showMessageDialog(null, "Cheapest:\n" + cheap);
    }

    // --- Function 4 ---
    static void showSaleGoods() {
        JOptionPane.showMessageDialog(null, shop.getSaleGoods(), "On-Sale Products", 1);
    }

    // --- Function 5 ---
    static void calcAvgPrice() {
        Double avg = shop.getAvgPrice();
        if (avg == null) JOptionPane.showMessageDialog(null, "No products!");
        else JOptionPane.showMessageDialog(null, "Average price: " + String.format("%.2f", avg));
    }

    // --- Function 6 ---
    static void showHighPrice() {
        double p;
        try {
            p = Double.parseDouble(JOptionPane.showInputDialog("Enter price threshold:"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter a number!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getHighPriceGoods(p), "Products higher than " + p, 1);
    }

    // --- Function 7 ---
    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "No products!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("Enter index (1-" + shop.getCount() + "):"));
            if (num < 1 || num > shop.getCount()) {
                JOptionPane.showMessageDialog(null, "Index error!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
            return;
        }
        Goods g = shop.getGoodsByIndex(num - 1);
        double newPrice;
        try {
            newPrice = Double.parseDouble(JOptionPane.showInputDialog("New price:"));
            if (newPrice < 0) {
                JOptionPane.showMessageDialog(null, "Price cannot be negative!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price input error!");
            return;
        }
        g.setPrice(newPrice);
        int s = JOptionPane.showConfirmDialog(null, "On sale?");
        g.setSale(s == 0);
        JOptionPane.showMessageDialog(null, "Edit completed!\n" + g);
    }

    // --- Function 8 ---
    static void deleteGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "No products to delete!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int idx;
        try {
            idx = Integer.parseInt(JOptionPane.showInputDialog("Enter index to delete:")) - 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
            return;
        }
        if (idx < 0 || idx >= shop.getCount()) {
            JOptionPane.showMessageDialog(null, "Index does not exist!");
            return;
        }
        shop.goodsList.remove(idx);
        JOptionPane.showMessageDialog(null, "Deleted successfully!");
    }

    // --- Function 9 ---
    static void searchGoods() {
        String key = JOptionPane.showInputDialog("Enter search keyword (Please note that the first letter should be capitalized):");
        if (key == null || key.trim().isEmpty()) return;
        ArrayList<Goods> list = shop.getGoodsList();
        StringBuilder sb = new StringBuilder("Search results:\n");
        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            Goods g = list.get(i);
            if (g.name.contains(key)) {
                sb.append((i + 1) + ". " + g + "\n");
                cnt++;
            }
        }
        if (cnt == 0) sb.append("No matching products");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static class Goods {
        String name;
        double price;
        boolean isSale;

        Goods(String n, double p, boolean s) {
            name = n;
            price = p;
            isSale = s;
        }

        void setPrice(double p) {
            price = p;
        }

        void setSale(boolean s) {
            isSale = s;
        }

        public String toString() {
            return String.format("%s Price:%.2f On Sale:%s", name, price, isSale ? "Yes" : "No");
        }
    }

    static class Store {
        ArrayList<Goods> goodsList = new ArrayList<>();

        boolean add(Goods g) {
            if (g == null) return false;
            return goodsList.add(g);
        }

        int getCount() {
            return goodsList.size();
        }

        Goods getGoodsByIndex(int idx) {
            return goodsList.get(idx);
        }

        ArrayList<Goods> getGoodsList() {
            return goodsList;
        }

        String getAllGoods() {
            if (goodsList.isEmpty()) return "No products";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                sb.append((i + 1) + ". " + goodsList.get(i) + "\n");
            }
            return sb.toString();
        }

        String getSaleGoods() {
            StringBuilder sb = new StringBuilder();
            int cnt = 0;
            for (int i = 0; i < goodsList.size(); i++) {
                Goods g = goodsList.get(i);
                if (g.isSale) {
                    sb.append((i + 1) + ". " + g + "\n");
                    cnt++;
                }
            }
            return cnt == 0 ? "No on-sale products" : sb.toString();
        }

        Goods getCheapGoods() {
            if (goodsList.isEmpty()) return null;
            Goods min = goodsList.get(0);
            for (Goods g : goodsList) if (g.price < min.price) min = g;
            return min;
        }

        Double getAvgPrice() {
            if (goodsList.isEmpty()) return null;
            double sum = 0;
            for (Goods g : goodsList) sum += g.price;
            return sum / goodsList.size();
        }

        String getHighPriceGoods(double p) {
            StringBuilder sb = new StringBuilder();
            int cnt = 0;
            for (int i = 0; i < goodsList.size(); i++) {
                Goods g = goodsList.get(i);
                if (g.price > p) {
                    sb.append((i + 1) + ". " + g + "\n");
                    cnt++;
                }
            }
            return cnt == 0 ? "No products higher than this price" : sb.toString();
        }
    }
}