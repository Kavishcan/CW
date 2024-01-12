import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ShoppingGUI extends JFrame {

    private JLabel selectProductLabel, cartHeading, cartProductId, cartCategory, cartName, cartSpeical1, cartSpecial2,
            cartQty;
    private JButton addToShoppingCart, viewShoppingCart, sortButton, logOutButton;
    private JComboBox productComboBox;
    private JTable productsTable;
    private JScrollPane scrollPane;
    private JPanel panel;

    public ShoppingGUI(User user) {
        setTitle("Westminster Shopping");

        setLayout(new GridBagLayout());
        initComponents();
        layoutGUI(user);
        setVisible(true);

        setSize(800, 800);


        pack();
        setLocationRelativeTo(null);
    }

    public void initComponents() {

        Color colour1 = Color.getHSBColor(255, 193, 159);
        Color colour2 = Color.getHSBColor(255, 20, 159);
        Color buttonBgColor = Color.getHSBColor(255, 193, 159);

        selectProductLabel = new JLabel("Select Product Category:");
        selectProductLabel.setFont(new Font("Roboto Mono", Font.BOLD, 18));

        cartHeading = new JLabel("Selected Product - Details");
        cartHeading.setFont(new Font("Roboto Mono", Font.BOLD, 14));

        cartProductId = new JLabel("Product Id");
        cartCategory = new JLabel("Category");
        cartName = new JLabel("Name");
        cartSpeical1 = new JLabel("Special 1");
        cartSpecial2 = new JLabel("Special 2");
        cartQty = new JLabel("Quantity");
        addToShoppingCart = createButton("Add to Shopping Cart", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(colour1));
        viewShoppingCart = createButton("View Shopping Cart", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(colour1));
        sortButton = createButton("Sort", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(colour1));
        logOutButton = createButton("Log Out", new Font("Roboto Mono", Font.PLAIN, 18), Color.WHITE, colour1, new LineBorder(colour1));
        productComboBox = new JComboBox<>(new String[] { "All", "Electronics", "Clothing" });

        String[] columnNames = { "Product Id", "Name", "Category", "Price(£)", "Info" };

        ArrayList<Product> products = WestminsterShoppingManager.getProductList();
        DefaultTableModel model = new DefaultTableModel(convertListToData(products), columnNames) {
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

        // Center align the table cells

        scrollPane = new JScrollPane(productsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
    }

    // method to style a button
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
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(logOutButton, constraints);

        logOutButton.addActionListener(e -> {
            dispose();
            new LoginGUI();
            WestminsterShoppingManager.saveUsersToFile();
        });

        constraints.gridx = 1;
        add(viewShoppingCart, constraints);

        viewShoppingCart.addActionListener(e -> {
            ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(user);
            shoppingCartGUI.setVisible(true);
        });

        constraints.gridy++;
        constraints.gridx = 0;
        add(selectProductLabel, constraints);

        constraints.gridx = 1;
        add(productComboBox, constraints);

        // shows the product combo box selected item in the table
        productComboBox.addActionListener(e -> {
            String selectedCategory = (String) productComboBox.getSelectedItem();
            ArrayList<Product> products = WestminsterShoppingManager.getProductList();
            if (selectedCategory.equals("All")) {
                productsTable.setModel(new DefaultTableModel(convertListToData(products),
                        new String[] { "Product Id", "Name", "Category", "Price(£)", "Info" }));

            } else if (selectedCategory.equals("Electronics")) {
                ArrayList<Product> electronics = new ArrayList<>();
                for (Product product : products) {
                    if (product instanceof Electronics) {
                        electronics.add(product);
                    }
                }
                productsTable.setModel(new DefaultTableModel(convertListToData(electronics),
                        new String[] { "Product Id", "Name", "Category", "Price(£)", "Info" }));

            } else if (selectedCategory.equals("Clothing")) {
                ArrayList<Product> clothing = new ArrayList<>();
                for (Product product : products) {
                    if (product instanceof Clothing) {
                        clothing.add(product);
                    }
                }
                productsTable.setModel(new DefaultTableModel(convertListToData(clothing),
                        new String[] { "Product Id", "Name", "Category", "Price(£)", "Info" }));
            }

        });

        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String productId = (String) table.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);
                if (product != null && product.getProductQty() <= 3) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                }
                return c;
            }
        });

        constraints.gridy++;
        constraints.gridx = 1;
        add(sortButton, constraints);

        sortButton.addActionListener(e -> {
            ArrayList<Product> products = WestminsterShoppingManager.getProductList();
            Collections.sort(products, Comparator.comparing(Product::getProductId));
            productsTable.setModel(new DefaultTableModel(convertListToData(products),
                    new String[] { "Product Id", "Name", "Category", "Price(£)", "Info" }));
        });

        constraints.gridwidth = 2;

        constraints.gridy++;
        constraints.gridx = 0;
        add(scrollPane, constraints);

        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                int row = productsTable.getSelectedRow();
                if (row >= 0) {
                    String productId = (String) productsTable.getValueAt(row, 0);
                    Product product = WestminsterShoppingManager.getProduct(productId);

                    panel.setVisible(true);
                    pack();

                    if (product.getProductQty() == 0) {
                        addToShoppingCart.setEnabled(false);
                    } else {
                        addToShoppingCart.setEnabled(true);
                    }

                    if (product != null) {

                        cartProductId.setText("Product Id: " + product.getProductId());
                        cartCategory.setText("Category: " + product.getClass().getName());
                        cartName.setText("Name: " + product.getProductName());
                        if (product instanceof Electronics) {
                            cartSpeical1.setText("Brand: " + ((Electronics) product).getBrand());
                            cartSpecial2.setText("Warranty Period: " + ((Electronics) product).getWarrantyPeriod());
                        } else if (product instanceof Clothing) {
                            cartSpeical1.setText("Size: " + ((Clothing) product).getSize());
                            cartSpecial2.setText("Colour: " + ((Clothing) product).getColour());
                        }
                        cartQty.setText("Quantity: " + product.getProductQty());
                    }
                }
            }
        });





        constraints.gridy++;
        constraints.gridx = 0;
        add(cartHeading, constraints);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.fill = GridBagConstraints.HORIZONTAL;
        constraints1.weightx = 1;
        constraints1.insets = new Insets(5, 0, 5, 10);

        constraints1.gridy = 0;
        constraints1.gridx = 0;
        panel.add(cartProductId, constraints1);
        constraints1.gridy++;
        panel.add(cartCategory, constraints1);
        constraints1.gridy++;
        panel.add(cartName, constraints1);
        constraints1.gridy++;
        panel.add(cartSpeical1, constraints1);
        constraints1.gridy++;
        panel.add(cartSpecial2, constraints1);
        constraints1.gridy++;
        panel.add(cartQty, constraints1);
        panel.setVisible(false);

        constraints.gridy++;
        add(panel, constraints);

        constraints.gridwidth = 0;

        constraints.fill = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 20, 10, 20);
        constraints.weightx = 2;
        constraints.gridy++;
        add(addToShoppingCart, constraints);

        ShoppingCartGUI ShoppingCartGUI = new ShoppingCartGUI(user);
        ShoppingCartGUI.setVisible(false);

        addToShoppingCart.addActionListener(e -> {
            int row = productsTable.getSelectedRow();
            if (row >= 0) {
                String productId = (String) productsTable.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);
                if (product != null) {
                    user.getShoppingCart().addToCart(product, 1);
                    productsTable.clearSelection();
                    JOptionPane.showMessageDialog(null, product.getProductName() + " added to the cart.");
                    product.setProductQty(product.getProductQty() - 1);
                    panel.setVisible(false);

                    // ShoppingCartGUI.setVisible(false);
                    ShoppingCartGUI.updateCart(user);
                }
                pack();
            }
        });
    }

    private Object[][] convertListToData(ArrayList<Product> products) {
        Object[][] data = new Object[products.size()][5];
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = product.getClass().getName();
            data[i][3] = product.getPrice() + "0 £";
            if (product instanceof Electronics) {
                data[i][4] = ((Electronics) product).getInfo();
            } else if (product instanceof Clothing) {
                data[i][4] = ((Clothing) product).getInfo();
            }
        }
        return data;
    }
}
