import javax.swing.*;
import java.util.ArrayList;

public class plus03 {
    static Store shop = new Store();

    public static void main(String[] args) {
        initGoods();
        while (true) {
            String[] menu = {
                    "1. Add Goods",
                    "2. View All Goods",
                    "3. Edit Goods",
                    "4. Delete Goods",
                    "5. Exit"
            };

            String choose = (String) JOptionPane.showInputDialog(
                    null, "Store Goods Management System - Please select a function:",
                    "Menu", 3, null, menu, menu[0]
            );

            if (choose == null) {
                if (JOptionPane.showConfirmDialog(null, "Confirm exit?", "Tip", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "\nThank you for using!");
                    break;
                } else {
                    continue;
                }
            }

            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);

            doMenu(ch);

            if (ch == 5) {
                JOptionPane.showMessageDialog(null, "Goodbye!");
                break;
            }
        }
    }

    static void initGoods() {
        shop.add(new Goods("Apple", 2.99, true));
        shop.add(new Goods("Banana", 3.99, true));
        shop.add(new Goods("Orange", 4.59, true));
    }

    static void doMenu(int ch) {
        switch (ch) {
            case 1: addGoods(); break;
            case 2: showAllGoods(); break;
            case 3: editGoods(); break;
            case 4: deleteGoods(); break;
            case 5: break;
            default: JOptionPane.showMessageDialog(null, "Invalid option!");
        }
    }

    static void addGoods() {
        String name = JOptionPane.showInputDialog("Enter goods name:");
        if (name == null || name.trim().isEmpty()) return;

        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("Enter goods price:"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price error!");
            return;
        }

        int sale = JOptionPane.showConfirmDialog(null, "On sale?");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "Added successfully!");
    }

    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "All Goods", 1);
    }

    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "No goods!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());

        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("Enter index:"));
            if (num < 1 || num > shop.getCount()) return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
            return;
        }

        Goods g = shop.getGoodsByIndex(num - 1);
        double newPrice;
        try {
            newPrice = Double.parseDouble(JOptionPane.showInputDialog("New price:", g.price));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Price error!");
            return;
        }
        g.setPrice(newPrice);

        int s = JOptionPane.showConfirmDialog(null, "On sale?");
        g.setSale(s == 0);

        JOptionPane.showMessageDialog(null, "Edit completed!\n" + g);
    }

    static void deleteGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "No goods to delete!");
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
            JOptionPane.showMessageDialog(null, "Index error!");
            return;
        }

        shop.goodsList.remove(idx);
        JOptionPane.showMessageDialog(null, "Deleted successfully!");
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
            return String.format("%s  Price:%.2f  On Sale:%s", name, price, isSale ? "Yes" : "No");
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

        String getAllGoods() {
            if (goodsList.isEmpty()) return "No goods";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                sb.append((i + 1) + ". " + goodsList.get(i) + "\n");
            }
            return sb.toString();
        }
    }
}
