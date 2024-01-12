import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
    private JLabel totalLabel, discount1Label, discount2Label, finalTotalLabel;
    private JTable productsTable;
    private JScrollPane scrollPane;
    private ShoppingCart shoppingCart;
    private JButton checkoutButton, removeButton;

    public ShoppingCartGUI(User user) {
        this.shoppingCart = user.getShoppingCart();
        setTitle("Westminster Shopping Cart");
        setLayout(new GridBagLayout());
        initComponents(user);
        layoutGUI(user);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
    }

    public void initComponents(User user) {
        Color colour1 = Color.getHSBColor(255, 193, 159);
        Color colour2 = Color.getHSBColor(255, 20, 159);
        Color buttonBgColor = Color.getHSBColor(255, 193, 159);


        Double total = user.getShoppingCart().calculateTotalCost();
        Double discount1 = user.getShoppingCart().firstPurchaseDiscount(user);
        Double discount2 = user.getShoppingCart().categoryDiscount();

        totalLabel = new JLabel("Total: " + total);
        discount1Label = new JLabel("First Purchase Discount(10%): - " + discount1);
        discount2Label = new JLabel("Three items in same category Discount(20%):  - " + discount2);
        finalTotalLabel = new JLabel("Final Total: " + (total - discount1 - discount2));

        checkoutButton = createButton("Checkout", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(buttonBgColor));

        removeButton = createButton("Remove", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(buttonBgColor));

        String[] columnNames = { "Product", "Quantity", "Price" };
        DefaultTableModel model = new DefaultTableModel(convertListToData(shoppingCart.getProducts()), columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        productsTable = new JTable(model);
        productsTable.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        productsTable.setForeground(Color.BLACK);// Set the foreground and background colors of the table
        productsTable.setBackground(colour2);// Set the foreground and background colors of the table
        productsTable.setGridColor(Color.BLACK);// Set the grid color of the table
        productsTable.setRowHeight(20); // Set the row height

        JTableHeader header = productsTable.getTableHeader();
        header.setBackground(colour1);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Roboto Mono", Font.BOLD, 16));


        scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        productsTable.setModel(model);
    }

    private JButton createButton(String text, Font font, Color foreground, Color background, Border border) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(foreground);
        button.setBackground(background);
        button.setBorder(border);
        return button;
    }

    public void layoutGUI(User user) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(scrollPane, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        add(removeButton, constraints);

        removeButton.addActionListener(e -> {
            int row = productsTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a product to remove");
            } else {
                String productId = productsTable.getValueAt(row, 0).toString().split("  ")[0];
                Product product = WestminsterShoppingManager.getProduct(productId);
                product.setProductQty(product.getProductQty() + 1);
                user.getShoppingCart().removeFromCart(product);
                updateCart(user);
            }
        });

        constraints.gridy++;
        add(totalLabel, constraints);

        constraints.gridy++;
        add(discount1Label, constraints);

        constraints.gridy++;
        add(discount2Label, constraints);

        constraints.gridy++;
        add(finalTotalLabel, constraints);

        constraints.gridy++;
        add(checkoutButton, constraints);

        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Checkout Successful");
            user.getShoppingCart().getProducts().clear();

            // update users file
            WestminsterShoppingManager.saveUsersToFile();
            // update the products file
            WestminsterShoppingManager.saveProductsToFile();
            this.dispose();
        });
    }

    private Object[][] convertListToData(Map<Product, Integer> map) {
        Object[][] data = new Object[map.size()][3];
        int i = 0;
        for (Product product : map.keySet()) {
            if (product instanceof Electronics) {
                data[i][0] = product.getProductId() + "  " + product.getProductName() + "  "
                        + ((Electronics) product).getInfo();
            } else if (product instanceof Clothing) {
                data[i][0] = product.getProductId() + "  " + product.getProductName() + "  "
                        + ((Clothing) product).getInfo();
            }
            data[i][1] = map.get(product);
            data[i][2] = product.getPrice();
            i++;
        }
        return data;
    }

    public void updateCart(User user) {
        Map<Product, Integer> ShoppingtItems = new LinkedHashMap<>();
        ShoppingtItems = user.getShoppingCart().getProducts();
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        model.setDataVector(convertListToData(ShoppingtItems), new String[] { "Product", "Quantity", "Price" });
        model.fireTableDataChanged();
        productsTable.setModel(model);
        productsTable.repaint();

    }

}
