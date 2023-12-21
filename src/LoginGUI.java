import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LoginGUI extends JFrame {
    
    private JTextField usernameField, firstNameField, lastNamField, genderField, ageField, signUpUserNameField, signUpEmailField;
    private JLabel welcomLabel ,usernameLabel, passwordLabel,firstNameLabel, lastNameLabel, genderLabel, ageLabel, signUpUserNameLabel, signUpPasswordLabel, signUpConfirmPasswordLabel,  signUpEmailLabel; 
    private JButton loginButton, signUpButton, cancButton, createButton;
    private JPasswordField loginPasswordField,signUpPasswordField, signUpConfirmPasswordField;

    public LoginGUI(){
        setTitle("Westminster Shopping Login Page");
        setSize(500, 500);
        setLayout(new GridBagLayout());
        initComponents();
        layoutGUI();
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        setVisible(true);

    }

    private void initComponents(){
        usernameField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNamField = new JTextField(20);
        genderField = new JTextField(20);
        ageField = new JTextField(20);
        signUpUserNameField = new JTextField(20);
        signUpEmailField = new JTextField(20);
        welcomLabel = new JLabel("Welcome to Westminster Shopping");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        firstNameLabel = new JLabel("First Name");
        lastNameLabel = new JLabel("Last Name");
        genderLabel = new JLabel("Gender");
        ageLabel = new JLabel("Age");
        signUpUserNameLabel = new JLabel("Username");
        signUpPasswordLabel = new JLabel("Password");
        signUpConfirmPasswordLabel = new JLabel("Confirm Password");
        signUpEmailLabel = new JLabel("Email");
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        cancButton = new JButton("Cancel");
        createButton = new JButton("Create Account");
        loginPasswordField = new JPasswordField(20);
        signUpPasswordField = new JPasswordField(20);
        signUpConfirmPasswordField = new JPasswordField(20);

    }

    private void layoutGUI(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets.set(10, 10, 10, 10);
        
        add(welcomLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy++;
        add(usernameLabel, constraints);

        constraints.gridx = 1;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        add(loginPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 2;
        add(loginButton, constraints);

        constraints.gridy++;
        add(signUpButton, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(loginPasswordField.getPassword());
                User user = WestminsterShoppingManager.getUser(username, password);
                if (user != null) {
                    WestminsterShoppingManager.setCurrentUser(user);
                    User currentUser = WestminsterShoppingManager.getCurrentUser();
                    JOptionPane.showMessageDialog(LoginGUI.this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new ShoppingGUI(currentUser);
                    
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                signUpGUI();
            }
        });
        
    }

    private void signUpGUI(){
        JDialog signUpGUI = new JDialog(this, "Sign Up", true);
        signUpGUI.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.set(5, 5, 5, 5);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        signUpGUI.add(firstNameLabel, constraints);

        constraints.gridx = 1;
        signUpGUI.add(lastNameLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(firstNameField, constraints);

        constraints.gridx = 1;
        signUpGUI.add(lastNamField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(ageLabel, constraints);

        constraints.gridx = 1;
        signUpGUI.add(genderLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(ageField, constraints);

        constraints.gridx = 1;
        signUpGUI.add(genderField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(signUpUserNameLabel, constraints);

        constraints.gridx = 1;
        signUpGUI.add(signUpEmailLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(signUpUserNameField, constraints);

        constraints.gridx = 1;
        signUpGUI.add(signUpEmailField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(signUpPasswordLabel, constraints);

        constraints.gridx = 1;
        signUpGUI.add(signUpConfirmPasswordLabel, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(signUpPasswordField, constraints);

        constraints.gridx = 1;
        signUpGUI.add(signUpConfirmPasswordField, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        signUpGUI.add(cancButton, constraints);

        constraints.gridx = 1;
        signUpGUI.add(createButton, constraints);

        createButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                User user = new User();
                try {
                    user.setUsername(signUpUserNameField.getText());
                    user.setPassword(new String(signUpPasswordField.getPassword()));
                    user.setEmail(signUpEmailField.getText());
                    user.setFirstName(firstNameField.getText());
                    user.setLastName(lastNamField.getText());
                    user.setAge(Integer.parseInt(ageField.getText()));
                    user.setGender(genderField.getText());

                    WestminsterShoppingManager.getUserList().add(user);
                    ArrayList <User> userList = WestminsterShoppingManager.getUserList();
                    System.out.println(userList);
                    for (User use : userList) {
                        System.out.println(use.getUsername());
                    }
                    JOptionPane.showMessageDialog(signUpGUI, "Account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    WestminsterShoppingManager.saveUsersToFile();
                    signUpGUI.setVisible(false);
                    signUpGUI.dispose();

                } catch (IllegalArgumentException error) {
                    JOptionPane.showMessageDialog(signUpGUI, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }    
        });

        cancButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                signUpGUI.setVisible(false);
                signUpGUI.dispose();
            }
        });

        signUpGUI.pack();
        signUpGUI.setLocationRelativeTo(this);
        signUpGUI.setVisible(true);

    }

    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }


}
