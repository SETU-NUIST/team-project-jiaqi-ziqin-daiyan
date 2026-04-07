import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class plus04 {
    static Store shop = new Store();
    static JFrame mainFrame;
    static JTextArea showArea;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initSampleData();
                shop.loadFromXmlFile();
                buildWindow();
                updateShowArea();
            }
        });
    }

    private static void buildWindow() {
        mainFrame = new JFrame("🏪 商店商品管理系统");
        mainFrame.setSize(850, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.getContentPane().setBackground(new Color(245, 247, 250));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 2, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(new Color(232, 240, 254));

        String[] texts = {
                "添加商品",
                "查看所有商品",
                "查找最便宜商品",
                "查看促销商品",
                "计算平均价格",
                "查找高价商品",
                "编辑商品",
                "退出"
        };

        for (String text : texts) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(120, 38));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setBackground(new Color(59, 130, 246));
            btn.setForeground(Color.WHITE);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(37, 99, 235));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(59, 130, 246));
                }
            });
            btn.addActionListener(new MyButtonListener());
            buttonPanel.add(btn);
        }

        showArea = new JTextArea();
        showArea.setEditable(false);
        showArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        showArea.setBackground(Color.WHITE);
        showArea.setForeground(new Color(34, 40, 49));
        showArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(showArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 225)),
                " 📋 商品信息列表",
                0, 0, new Font("微软雅黑", Font.BOLD, 14), new Color(30, 60, 130)
        ));

        mainFrame.add(buttonPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static class MyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = ((JButton) e.getSource()).getText();
            if (text.equals("添加商品")) {
                doAddGoods();
            } else if (text.equals("查看所有商品")) {
                updateShowArea();
            } else if (text.equals("查找最便宜商品")) {
                showCheapest();
            } else if (text.equals("查看促销商品")) {
                showOnSaleOnly();
            } else if (text.equals("计算平均价格")) {
                showAveragePrice();
            } else if (text.equals("查找高价商品")) {
                showHigherPrice();
            } else if (text.equals("编辑商品")) {
                doEditGoods();
            } else if (text.equals("退出")) {
                System.exit(0);
            }
        }
    }

    private static void updateShowArea() {
        showArea.setText(shop.getAllGoodsString());
    }

    private static void initSampleData() {
        if (shop.getGoodsCount() == 0) {
            shop.addGoods(new Goods("苹果", 2.99, true));
            shop.addGoods(new Goods("香蕉", 3.99, true));
            shop.addGoods(new Goods("橙子", 4.59, true));
            shop.addGoods(new Goods("牛奶", 5.99, true));
            shop.addGoods(new Goods("面包", 6.99, true));
        }
    }

    private static void doAddGoods() {
        String name = JOptionPane.showInputDialog("请输入商品名称");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        try {
            String priceInput = JOptionPane.showInputDialog("请输入商品价格");
            double price = Double.parseDouble(priceInput);
            if (price < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负数");
                return;
            }
            int saleResult = JOptionPane.showConfirmDialog(null, "该商品是否促销？");
            boolean onSale = (saleResult == 0);
            shop.addGoods(new Goods(name, price, onSale));
            updateShowArea();
            shop.saveToXmlFile();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "输入格式无效");
        }
    }

    private static void doEditGoods() {
        if (shop.getGoodsCount() == 0) {
            JOptionPane.showMessageDialog(null, "暂无商品可编辑");
            return;
        }
        try {
            String input = JOptionPane.showInputDialog(shop.getAllGoodsString() + "\n请输入要修改的编号");
            int index = Integer.parseInt(input) - 1;
            Goods goods = shop.getByIndex(index);
            String newPriceInput = JOptionPane.showInputDialog("请输入新价格");
            double newPrice = Double.parseDouble(newPriceInput);
            if (newPrice < 0) {
                JOptionPane.showMessageDialog(null, "价格不能为负数");
                return;
            }
            goods.setPrice(newPrice);
            int saleResult = JOptionPane.showConfirmDialog(null, "是否促销？");
            goods.setOnSale(saleResult == 0);
            updateShowArea();
            shop.saveToXmlFile();
            JOptionPane.showMessageDialog(null, "修改完成");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "输入无效");
        }
    }

    private static void showCheapest() {
        Goods goods = shop.getCheapestGoods();
        if (goods == null) {
            JOptionPane.showMessageDialog(null, "暂无商品");
        } else {
            JOptionPane.showMessageDialog(null, "最便宜的商品：\n" + goods);
        }
    }

    private static void showAveragePrice() {
        Double avg = shop.getAveragePrice();
        if (avg == null) {
            JOptionPane.showMessageDialog(null, "暂无商品");
        } else {
            JOptionPane.showMessageDialog(null, "商品平均价格：" + String.format("%.2f", avg));
        }
    }

    private static void showHigherPrice() {
        try {
            String input = JOptionPane.showInputDialog("请输入价格阈值");
            double line = Double.parseDouble(input);
            showArea.setText(shop.getHigherPriceGoods(line));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "请输入数字");
        }
    }

    private static void showOnSaleOnly() {
        showArea.setText(shop.getOnSaleGoodsString());
    }

    static class Goods {
        private String name;
        private double price;
        private boolean onSale;

        public Goods(String name, double price, boolean onSale) {
            this.name = name;
            this.price = price;
            this.onSale = onSale;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isOnSale() {
            return onSale;
        }

        public void setOnSale(boolean onSale) {
            this.onSale = onSale;
        }

        public String toString() {
            return name + " | 价格：" + String.format("%.2f", price) + " | 促销：" + (onSale ? "是" : "否");
        }

        public void setSale(boolean b) {
        }
    }

    static class Store {
        private ArrayList<Goods> goodsList = new ArrayList<>();

        public boolean addGoods(Goods goods) {
            if (goods == null) return false;
            return goodsList.add(goods);
        }

        public int getGoodsCount() {
            return goodsList.size();
        }

        public Goods getByIndex(int index) {
            return goodsList.get(index);
        }

        public ArrayList<Goods> getGoodsList() {
            return goodsList;
        }

        public String getAllGoodsString() {
            if (goodsList.isEmpty()) {
                return "暂无商品";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                sb.append((i + 1) + ". ");
                sb.append(goodsList.get(i));
                sb.append("\n");
            }
            return sb.toString();
        }

        public String getOnSaleGoodsString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                Goods g = goodsList.get(i);
                if (g.isOnSale()) {
                    sb.append(g);
                    sb.append("\n");
                }
            }
            if (sb.length() == 0) {
                return "暂无促销商品";
            }
            return sb.toString();
        }

        public Goods getCheapestGoods() {
            if (goodsList.isEmpty()) return null;
            Goods min = goodsList.get(0);
            for (int i = 1; i < goodsList.size(); i++) {
                Goods now = goodsList.get(i);
                if (now.getPrice() < min.getPrice()) {
                    min = now;
                }
            }
            return min;
        }

        public Double getAveragePrice() {
            if (goodsList.isEmpty()) return null;
            double sum = 0;
            for (int i = 0; i < goodsList.size(); i++) {
                sum += goodsList.get(i).getPrice();
            }
            return sum / goodsList.size();
        }

        public String getHigherPriceGoods(double line) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < goodsList.size(); i++) {
                Goods g = goodsList.get(i);
                if (g.getPrice() > line) {
                    sb.append(g);
                    sb.append("\n");
                }
            }
            if (sb.length() == 0) {
                return "没有高于该价格的商品";
            }
            return sb.toString();
        }

        public void loadFromXmlFile() {
            File file = new File("goods.xml");
            if (!file.exists()) return;
            goodsList.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                String name = null;
                double price = 0;
                boolean sale = false;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("<name>")) {
                        name = line.replace("<name>", "").replace("</name>", "");
                    } else if (line.startsWith("<price>")) {
                        String pStr = line.replace("<price>", "").replace("</price>", "");
                        price = Double.parseDouble(pStr);
                    } else if (line.startsWith("<isSale>")) {
                        String sStr = line.replace("<isSale>", "").replace("</isSale>", "");
                        sale = Boolean.parseBoolean(sStr);
                    } else if (line.startsWith("</goods>")) {
                        addGoods(new Goods(name, price, sale));
                    }
                }
            } catch (Exception ignored) {
            }
        }

        public void saveToXmlFile() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("goods.xml"))) {
                bw.write("<goodsList>");
                bw.newLine();
                for (int i = 0; i < goodsList.size(); i++) {
                    Goods g = goodsList.get(i);
                    bw.write(" <goods>");
                    bw.newLine();
                    bw.write(" <name>" + g.getName() + "</name>");
                    bw.newLine();
                    bw.write(" <price>" + g.getPrice() + "</price>");
                    bw.newLine();
                    bw.write(" <isSale>" + g.isOnSale() + "</isSale>");
                    bw.newLine();
                    bw.write(" </goods>");
                    bw.newLine();
                }
                bw.write("</goodsList>");
            } catch (IOException ignored) {
            }
        }
    }
}