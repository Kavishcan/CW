import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
    private JLabel totalLabel, discount1Label, discount2Label, finalTotalLabel;
    private JTable productsTable;
    private JScrollPane scrollPane;
    private ShoppingCart shoppingCart;
    private JButton checkoutButton;

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

    public void initComponents(User user){

        Double total = user.getShoppingCart().calculateTotalCost();
        Double discount1 = user.getShoppingCart().firstPurchaseDiscount(user);
        Double discount2 = user.getShoppingCart().categoryDiscount();

        
        totalLabel = new JLabel("Total: " + total);
        discount1Label = new JLabel("First Purchase Discount(10%): - "+ discount1);
        discount2Label = new JLabel("Three items in same category Discount(20%):  - "+ discount2);
        finalTotalLabel = new JLabel("Final Total: "+ (total - discount1 - discount2));
        checkoutButton = new JButton("Checkout");

        Map<Product, Integer> shoppingItems =  user.getShoppingCart().getProducts();
        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(convertListToData(shoppingCart.getProducts()), columnNames);
        productsTable = new JTable(model);
        scrollPane = new JScrollPane(productsTable);
    }


    public void layoutGUI(User user){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(scrollPane, constraints);

        constraints.anchor = GridBagConstraints.LINE_END;

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
            ArrayList<User> i = WestminsterShoppingManager.getUserList();
            WestminsterShoppingManager.saveUsersToFile();
            ArrayList<User> j = WestminsterShoppingManager.getUserList();
            WestminsterShoppingManager.saveProductsToFile();
            this.dispose();
        });

    }

    private Object[][] convertListToData(Map<Product, Integer> map) {
        Object[][] data = new Object[map.size()][3];
        int i = 0;
        for (Product product : map.keySet()) {
            data[i][0] = product.getProductName();
            data[i][1] = map.get(product);
            data[i][2] = product.getPrice();
            i++;
        }
        return data;
    }

    public static void main(String[] args) {
        ShoppingCartGUI shoppingGUI = new ShoppingCartGUI(new User());
        shoppingGUI.setVisible(true);
    }
}