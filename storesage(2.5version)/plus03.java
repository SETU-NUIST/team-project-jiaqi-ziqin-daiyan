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
                    "3. 查找最便宜商品",
                    "4. 查看促销商品",
                    "5. 计算平均价格",
                    "6. 查看高价商品",
                    "7. 编辑商品",
                    "8. 删除商品",
                    "9. 搜索商品",
                    "10. 退出"
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
                if (JOptionPane.showConfirmDialog(null, "确认退出？", "提示", 0) == 0) {
                    JOptionPane.showMessageDialog(null, "感谢使用！");
                    break;
                } else {
                    continue;
                }
            }
            String numStr = choose.split("\\.")[0];
            int ch = Integer.parseInt(numStr);
            doMenu(ch);
            if (ch == 10) {
                JOptionPane.showMessageDialog(null, "再见！");
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
            case 8: deleteGoods(); break;
            case 9: searchGoods(); break;
            case 10: break;
            default: JOptionPane.showMessageDialog(null, "无效选项！");
        }
    }

    static void addGoods() {
        String name = JOptionPane.showInputDialog("请输入商品名称（请注意首字母大写）：");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "名称不能为空！");
            return;
        }
        double price = 0;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog("请输入商品价格："));
            if (price < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负数！");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误！");
            return;
        }
        int sale = JOptionPane.showConfirmDialog(null, "是否促销？");
        shop.add(new Goods(name, price, sale == 0));
        JOptionPane.showMessageDialog(null, "添加成功！");
    }

    static void showAllGoods() {
        JOptionPane.showMessageDialog(null, shop.getAllGoods(), "所有商品", 1);
    }

    static void findCheap() {
        Goods cheap = shop.getCheapGoods();
        if (cheap == null) JOptionPane.showMessageDialog(null, "暂无商品！");
        else JOptionPane.showMessageDialog(null, "最便宜的商品：\n" + cheap);
    }

    static void showSaleGoods() {
        JOptionPane.showMessageDialog(null, shop.getSaleGoods(), "促销商品", 1);
    }    // --- 功能 5 ---
    static void calcAvgPrice() {
        Double avg = shop.getAvgPrice();
        if (avg == null) JOptionPane.showMessageDialog(null, "暂无商品！");
        else JOptionPane.showMessageDialog(null, "平均价格：" + String.format("%.2f", avg));
    }

    static void showHighPrice() {
        double p;
        try {
            p = Double.parseDouble(JOptionPane.showInputDialog("输入价格阈值："));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "请输入数字！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getHighPriceGoods(p), "高于价格 " + p + " 的商品", 1);
    }

    static void editGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int num;
        try {
            num = Integer.parseInt(JOptionPane.showInputDialog("输入序号(1-" + shop.getCount() + ")："));
            if (num < 1 || num > shop.getCount()) {
                JOptionPane.showMessageDialog(null, "序号错误！");
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
                JOptionPane.showMessageDialog(null, "价格不能为负数！");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "价格输入错误！");
            return;
        }
        g.setPrice(newPrice);
        int s = JOptionPane.showConfirmDialog(null, "是否促销？");
        g.setSale(s == 0);
        JOptionPane.showMessageDialog(null, "修改完成！\n" + g);
    }

    static void deleteGoods() {
        if (shop.getCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品可删除！");
            return;
        }
        JOptionPane.showMessageDialog(null, shop.getAllGoods());
        int idx;
        try {
            idx = Integer.parseInt(JOptionPane.showInputDialog("输入要删除的序号：")) - 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "输入无效！");
            return;
        }
        if (idx < 0 || idx >= shop.getCount()) {
            JOptionPane.showMessageDialog(null, "序号不存在！");
            return;
        }
        shop.goodsList.remove(idx);
        JOptionPane.showMessageDialog(null, "删除成功！");
    }

    static void searchGoods() {
        String key = JOptionPane.showInputDialog("输入搜索关键词（请注意首字母大写）：");
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
        if (cnt == 0) sb.append("未找到匹配商品");
        JOptionPane.showMessageDialog(null, sb.toString());
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

        public void setSale(boolean b) {
            
        }

        public void setPrice(double newPrice) {
        }
    }
}