import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class ShoppingGUI extends JFrame {

    private JLabel selectProductLabel, cartHeading, cartProductId, cartCategory,cartName, cartSpeical1, cartSpecial2, cartQty;
    private JButton addToShoppingCart, viewShoppingCart, sortButton;
    private JComboBox productComboBox;
    private JTable productsTable;
    private JScrollPane scrollPane;
    private JPanel panel;

    WestminsterShoppingManager wsm = new WestminsterShoppingManager();

    public ShoppingGUI(User user) {
        setTitle("Westminster Shopping Login Page");

        setLayout(new GridBagLayout());
        initComponents();
        layoutGUI(user);
        setVisible(true);

        pack();
        setLocationRelativeTo(null);
}

    public void initComponents() {
        selectProductLabel = new JLabel("Select Product:");
        cartHeading = new JLabel("Selected Product - Details");
        cartProductId = new JLabel("Product Id");
        cartCategory = new JLabel("Category");
        cartName = new JLabel("Name");
        cartSpeical1 = new JLabel("Special 1");
        cartSpecial2 = new JLabel("Special 2");
        cartQty = new JLabel("Quantity");
        addToShoppingCart = new JButton("Add to Shopping Cart");
        viewShoppingCart = new JButton("View Shopping Cart");
        sortButton = new JButton("Sort");
        productComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"}); 

        String[] columnNames = {"Product Id", "Name","Category","Price","Info"};

        ArrayList<Product> products = WestminsterShoppingManager.getProductList();
        DefaultTableModel model = new DefaultTableModel(convertListToData(products), columnNames);
        productsTable = new JTable(model);
        // productsTable.isCellEditable(ERROR, ABORT);
        scrollPane = new JScrollPane(productsTable);
    }

    public void layoutGUI(User user) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 5, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(selectProductLabel, constraints);

        constraints.gridx = 1;
        add(viewShoppingCart, constraints);

        viewShoppingCart.addActionListener(e -> {
            new ShoppingCartGUI(user);
        });

        constraints.gridy++;
        constraints.gridx = 0;
        add(productComboBox, constraints);

        // show the product combo box selected item in the table
        productComboBox.addActionListener(e -> {
            String selectedCategory = (String) productComboBox.getSelectedItem();
            ArrayList<Product> products = WestminsterShoppingManager.getProductList();
            if (selectedCategory.equals("All")) {
                productsTable.setModel(new DefaultTableModel(convertListToData(products), new String[]{"Product Id", "Name","Category","Price","Info"}));
            } else if (selectedCategory.equals("Electronics")) {
                ArrayList<Product> electronics = new ArrayList<>();
                for (Product product : products) {
                    if (product instanceof Electronics) {
                        electronics.add(product);
                    }
                }
                productsTable.setModel(new DefaultTableModel(convertListToData(electronics), new String[]{"Product Id", "Name","Category","Price","Info"}));
            } else if (selectedCategory.equals("Clothing")) {
                ArrayList<Product> clothing = new ArrayList<>();
                for (Product product : products) {
                    if (product instanceof Clothing) {
                        clothing.add(product);
                    }
                }
                productsTable.setModel(new DefaultTableModel(convertListToData(clothing), new String[]{"Product Id", "Name","Category","Price","Info"}));
            }
        });

        constraints.gridx = 1;
        add(sortButton, constraints);

        sortButton.addActionListener(e -> {
            ArrayList<Product> products = WestminsterShoppingManager.getProductList();
            Collections.sort(products, Comparator.comparing(Product::getProductId));
            productsTable.setModel(new DefaultTableModel(convertListToData(products), new String[]{"Product Id", "Name","Category","Price","Info"}));
        });

        

        constraints.gridwidth = 2;

        constraints.gridy++;
        constraints.gridx = 0;
        add(scrollPane, constraints);
        

        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            // setPreferredSize(new Dimension(489, 831));
            super.mouseClicked(e);
            int row = productsTable.getSelectedRow();
            if (row >= 0) {
                String productId = (String) productsTable.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);
                panel.setVisible(true);
                pack();
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
        add(cartHeading, constraints);
           

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.fill = GridBagConstraints.HORIZONTAL;
        constraints1.weightx = 1;
        // constraints1.anchor = GridBagConstraints.LINE_START;
        constraints1.insets = new Insets(5, 0, 5, 10);
        // constraints1.gridwidth = 1;

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

        constraints.gridwidth = 1;

        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.insets = new Insets(10,10,10,175);
        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy++;
        add(addToShoppingCart, constraints);

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
                    // updateShoppingCartView(); // Update the shopping cart view
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
            data[i][3] = product.getPrice();
            if (product instanceof Electronics) {
                data[i][4] = ((Electronics) product).getInfo();
            } else if (product instanceof Clothing) {
                data[i][4] = ((Clothing) product).getInfo();
            }
        }
        return data;
    }


    

    public static void main(String[] args) {
        ShoppingGUI shoppingGUI = new ShoppingGUI(new User());
        shoppingGUI.setVisible(true);
    }


}
