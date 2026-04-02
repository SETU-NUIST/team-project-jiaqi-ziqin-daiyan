import javax.swing.*;
import java.util.ArrayList;

public class plus03 {
    static Store shop = new Store();

    public static void main(String[] args) {
        initGoods();
        while (true) {
            String[] menu = {
                    "1. 添加商品",
                    "2. 查看所有商品",
                    "3. 编辑商品",
                    "4. 删除商品",
                    "5.退出"
            };

            String choose = (String) JOptionPane.showInputDialog(
                    null, "商店商品管理系统 请选择功能:",
                    "菜单", 3, null, menu, menu[0]
            );

            if (choose == null) {
                if (JOptionPane.showConfirmDialog(null, "确认退出？", "提示", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "\n感谢您的使用!");
                    break;
                } else {
                    continue;
                }
            }

            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);

            doMenu(ch);

            if (ch == 5) {
                JOptionPane.showMessageDialog(null, "拜拜!");
                break;
            }
        }
    }

    static void initGoods() {
        shop.add(new Goods("苹果", 2.99, true));
        shop.add(new Goods("香蕉", 3.99, true));
        shop.add(new Goods("橘子", 4.59, true));
    }

    static void doMenu(int ch) {
        switch (ch) {
            case 1: addGoods(); break;
            case 2: showAllGoods(); break;
            case 3: editGoods(); break;
            case 4: deleteGoods(); break;
            case 5: break;
            default: JOptionPane.showMessageDialog(null, "选项无效!");
        }
    }

    static void addGoods() {
        String name = JOptionPane.showInputDialog("输入商品名称:");
        if (name == null || name.trim().isEmpty()) return;

        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("输入商品价格:"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格错误!");
            return;
        }

        int sale = JOptionPane.showConfirmDialog(null, "促销中?");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "添加成功!");
    }

    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "所有商品", 1);
    }

    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "没有商品!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());

        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("输入索引:"));
            if (num < 1 || num > shop.getCount()) return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效!");
            return;
        }

        Goods g = shop.getGoodsByIndex(num - 1);
        double newPrice;
        try {
            newPrice = Double.parseDouble(JOptionPane.showInputDialog("新价格:", g.price));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格错误!");
            return;
        }
        g.setPrice(newPrice);

        int s = JOptionPane.showConfirmDialog(null, "促销?");
        g.setSale(s == 0);

        JOptionPane.showMessageDialog(null, "编辑完成!\n" + g);
    }

    static void deleteGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "没有商品可以删除!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());

        int idx;
        try {
            idx = Integer.parseInt(JOptionPane.showInputDialog("输入要删除的索引:")) - 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效!");
            return;
        }

        if (idx < 0 || idx >= shop.getCount()) {
            JOptionPane.showMessageDialog(null, "Index error!");
            return;
        }

        shop.goodsList.remove(idx);
        JOptionPane.showMessageDialog(null, "成功删除!");
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
            return String.format("%s  价格:%.2f  促销:%s", name, price, isSale ? "Yes" : "No");
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
            if (goodsList.isEmpty()) return "没有商品";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                sb.append((i + 1) + ". " + goodsList.get(i) + "\n");
            }
            return sb.toString();
        }
    }
}



