import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class plus03 {
    static Store shop = new Store();

    public static void main(String[] args) {
        initGoods();
        shop.loadFromXML();

        while (true) {
            String[] menu = {
                    "1. 添加商品",
                    "2. 查看所有商品",
                    "3. 找最便宜商品",
                    "4. 查看在售商品",
                    "5. 计算均价",
                    "6. 查高价商品",
                    "7. 修改商品",
                    "8. 删除商品",
                    "9. 搜索商品",
                    "10. 批量上下架",
                    "11. 价格排序",
                    "12. 退出"
            };

            String choose = (String) JOptionPane.showInputDialog(
                    null,
                    "商店商品管理系统\n请选择操作：",
                    "菜单",
                    3,
                    null,
                    menu,
                    menu[0]
            );

            if (choose == null) {
                if (JOptionPane.showConfirmDialog(null, "确定退出？", "提示", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "谢谢使用！");
                    break;
                } else {
                    continue;
                }
            }

            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);

            doMenu(ch);

            if (ch == 12) {
                JOptionPane.showMessageDialog(null, "再见！");
                break;
            }
        }
    }

    static void initGoods() {
        shop.add(new Goods("苹果", 2.99, true));
        shop.add(new Goods("香蕉", 3.99, true));
        shop.add(new Goods("橙子", 4.59, true));
        shop.add(new Goods("牛奶", 5.99, true));
        shop.add(new Goods("面包", 6.99, true));
    }

    static void doMenu(int ch) {
        switch (ch) {
            case 1:
                addGoods();
                break;
            case 2:
                showAllGoods();
                break;
            case 3:
                findCheap();
                break;
            case 4:
                showSaleGoods();
                break;
            case 5:
                calcAvgPrice();
                break;
            case 6:
                showHighPrice();
                break;
            case 7:
                editGoods();
                break;
            case 8:
                deleteGoods();
                break;
            case 9:
                searchGoods();
                break;
            case 10:
                batchSale();
                break;
            case 11:
                sortByPrice();
                break;
            case 12:
                break;
            default:
                JOptionPane.showMessageDialog(null, "选项错误！");
        }
        shop.saveToXML();
    }

    static void deleteGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品可删！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int idx;
        try {
            idx = Integer.parseInt(JOptionPane.showInputDialog("输入要删除的编号：")) - 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效！");
            return;
        }
        if (idx < 0 || idx >= shop.getCount()) {
            JOptionPane.showMessageDialog(null, "编号不存在！");
            return;
        }
        shop.goodsList.remove(idx);
        JOptionPane.showMessageDialog(null, "删除成功！");
    }

    static void searchGoods() {
        String key = JOptionPane.showInputDialog("输入搜索关键词：");
        if (key == null || key.trim().isEmpty()) return;

        ArrayList<Goods> list = shop.getGoodsList();
        StringBuilder sb = new StringBuilder("搜索结果：\n");
        int cnt = 0;
        for (int i = 0; i < list.size(); i++) {
            Goods g = list.get(i);
            if (g.name.contains(key)) {
                sb.append((i + 1) + ". " + g + "\n");
                cnt++;
            }
        }
        if (cnt == 0) sb.append("无匹配商品");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void batchSale() {
        String[] opt = {"全部上架", "全部下架"};
        int choose = JOptionPane.showOptionDialog(
                null, "选择操作", "批量上下架",
                0, 3, null, opt, opt[0]
        );
        if (choose == 0) {
            for (Goods g : shop.getGoodsList()) g.setSale(true);
            JOptionPane.showMessageDialog(null, "已全部上架！");
        } else if (choose == 1) {
            for (Goods g : shop.getGoodsList()) g.setSale(false);
            JOptionPane.showMessageDialog(null, "已全部下架！");
        }
    }

    static void sortByPrice() {
        if (shop.getCount() < 1) {
            JOptionPane.showMessageDialog(null, "商品不足，无法排序！");
            return;
        }
        ArrayList<Goods> sorted = new ArrayList<>(shop.getGoodsList());
        sorted.sort((g1, g2) -> Double.compare(g1.price, g2.price));

        StringBuilder sb = new StringBuilder("价格从低到高：\n");
        for (int i = 0; i < sorted.size(); i++) {
            sb.append((i + 1) + ". " + sorted.get(i) + "\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void addGoods() {
        String name = JOptionPane.showInputDialog("输入商品名称：");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "名称不能为空！");
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("输入商品价格："));
            if (price < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负！");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误！");
            return;
        }
        int sale = JOptionPane.showConfirmDialog(null, "是否在售？");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "添加成功！");
    }

    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "所有商品", 1);
    }

    static void showSaleGoods() {
        JOptionPane.showMessageDialog(null, shop.getSaleGoods(), "在售商品", 1);
    }

    static void findCheap() {
        Goods cheap = shop.getCheapGoods();
        if (cheap == null) JOptionPane.showMessageDialog(null, "暂无商品！");
        else JOptionPane.showMessageDialog(null, "最便宜：\n" + cheap);
    }

    static void calcAvgPrice() {
        Double avg = shop.getAvgPrice();
        if (avg == null) JOptionPane.showMessageDialog(null, "暂无商品！");
        else JOptionPane.showMessageDialog(null, "均价：" + String.format("%.2f", avg) + "元");
    }

    static void showHighPrice() {
        double p;
        try {
            p = Double.parseDouble(JOptionPane.showInputDialog("输入价格阈值："));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "请输入数字！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getHighPriceGoods(p), "高于" + p + "元商品", 1);
    }

    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("输入编号(1-" + shop.getCount() + ")："));
            if (num < 1 || num > shop.getCount()) {
                JOptionPane.showMessageDialog(null, "编号错误！");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效！");
            return;
        }
        Goods g = shop.getGoodsByIndex(num - 1);
        double newPrice;
        try {
            newPrice = Double.parseDouble(JOptionPane.showInputDialog("新价格："));
            if (newPrice < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负！");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误！");
            return;
        }
        g.setPrice(newPrice);
        int s = JOptionPane.showConfirmDialog(null, "是否在售？");
        g.setSale(s == 0);
        JOptionPane.showMessageDialog(null, "修改完成！\n" + g);
    }

    // ==================== Goods ====================
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
            return String.format("%s  价格：%.2f  在售：%s", name, price, isSale ? "是" : "否");
        }
    }

    // ==================== Store ====================
    static class Store {
        ArrayList<Goods> goodsList = new ArrayList<>();

        // 返回boolean，支持测试用例
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

        // ========== 测试专用方法 ==========
        int numberOfProducts() {
            return goodsList.size();
        }

        Goods findProduct(int index) {
            if (index >= 0 && index < goodsList.size()) {
                return goodsList.get(index);
            }
            return null;
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
            return cnt == 0 ? "暂无在售商品" : sb.toString();
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
            return cnt == 0 ? "无高于该价格商品" : sb.toString();
        }

        public void loadFromXML() {
            File file = new File("goods.xml");
            if (!file.exists()) return;

            goodsList.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                String name = null;
                double price = 0;
                boolean isSale = false;

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("<name>")) {
                        name = line.replace("<name>", "").replace("</name>", "");
                    } else if (line.startsWith("<price>")) {
                        price = Double.parseDouble(line.replace("<price>", "").replace("</price>", ""));
                    } else if (line.startsWith("<isSale>")) {
                        isSale = Boolean.parseBoolean(line.replace("<isSale>", "").replace("</isSale>", ""));
                    } else if (line.startsWith("</goods>")) {
                        goodsList.add(new plus03.Goods(name, price, isSale));
                    }
                }
            } catch (Exception ignored) {}
        }
        public void saveToXML() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("goods.xml"))) {
                bw.write("<goodsList>");
                bw.newLine();
                for (plus03.Goods g : goodsList) {
                    bw.write("  <goods>");
                    bw.newLine();
                    bw.write("    <name>" + g.name + "</name>");
                    bw.newLine();
                    bw.write("    <price>" + g.price + "</price>");
                    bw.newLine();
                    bw.write("    <isSale>" + g.isSale + "</isSale>");
                    bw.newLine();
                    bw.write("  </goods>");
                    bw.newLine();
                }
                bw.write("</goodsList>");
            } catch (IOException ignored) {}
        }
    }
}




