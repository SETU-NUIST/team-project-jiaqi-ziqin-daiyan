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
                    "3. 查看最便宜商品",
                    "4. 查看促销商品",
                    "5. 计算平均价格",
                    "6. 查看高价商品",
                    "7. 编辑商品",
                    "8. 退出"
            };
            String choose = (String) JOptionPane.showInputDialog(
                    null,
                    "商店商品管理系统 请选择功能:",
                    "菜单",
                    3,
                    null,
                    menu,
                    menu[0]
            );
            if (choose == null) {
                if (JOptionPane.showConfirmDialog(null, "确认退出?", "提示", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "感谢你使用!");
                    break;
                } else {
                    continue;
                }
            }
            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);
            doMenu(ch);
            if (ch == 8) {
                JOptionPane.showMessageDialog(null, "拜拜!");
                break;
            }
        }
    }

    static void initGoods() {
        shop.add(new Goods("苹果", 2.99, true));
        shop.add(new Goods("香蕉", 3.99, true));
        shop.add(new Goods("橘子", 4.59, true));
        shop.add(new Goods("牛奶", 5.99, true));
        shop.add(new Goods("面包", 6.99, true));
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
            case 8: break;
            default: JOptionPane.showMessageDialog(null, "选项无效!");
        }
    }
    static void addGoods() {
        String name = JOptionPane.showInputDialog("输入商品名称:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "商品名称不能为空!");
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("输入商品价格:"));
            if (price < 0) {
                JOptionPane.showMessageDialog(null, "价格不可为负!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误!");
            return;
        }
        int sale = JOptionPane.showConfirmDialog(null, "是否促销?");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "添加成功!");
    }

    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "所有商品", 1);
    }

    static void findCheap() {
        Goods cheap = shop.getCheapGoods();
        if (cheap == null) JOptionPane.showMessageDialog(null, "暂无商品!");
        else JOptionPane.showMessageDialog(null, "最便宜的商品:\n" + cheap);
    }

    static void showSaleGoods() {
        JOptionPane.showMessageDialog(null, shop.getSaleGoods(), "促销商品", 1);
    }

    static void calcAvgPrice() {
        Double avg = shop.getAvgPrice();
        if (avg == null) JOptionPane.showMessageDialog(null, "暂无商品!");
        else JOptionPane.showMessageDialog(null, "平均价格:" + String.format("%.2f", avg) + "");
    }

    static void showHighPrice() {
        double p;
        try {
            p = Double.parseDouble(JOptionPane.showInputDialog("输入价格阈值:"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "请输入数字!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getHighPriceGoods(p), "高于价格 " + p + " 的商品", 1);
    }

    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品!");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("输入序号(1-" + shop.getCount() + "):"));
            if (num < 1 || num > shop.getCount()) {
                JOptionPane.showMessageDialog(null, "序号错误!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效!");
            return;
        }
        Goods g = shop.getGoodsByIndex(num - 1);
        double newPrice;
        try {
            newPrice = Double.parseDouble(JOptionPane.showInputDialog("新价格:"));
            if (newPrice < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负数!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误!");
            return;
        }
        g.setPrice(newPrice);
        int s = JOptionPane.showConfirmDialog(null, "是否促销?");
        g.setSale(s == 0);
        JOptionPane.showMessageDialog(null, "修改完成!\n" + g);
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
            return String.format("%s 价格:%.2f 促销:%s", name, price, isSale ? "是" : "否");
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
            if (goodsList.isEmpty()) return "暂无商品";
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
            return cnt == 0 ? "暂无促销商品" : sb.toString();
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
            return cnt == 0 ? "没有高于此价格的商品" : sb.toString();
        }
    }
}