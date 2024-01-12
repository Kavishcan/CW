import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private ShoppingCart shoppingCart;
    private boolean firstPurchase;

    public User() {
        this.shoppingCart = new ShoppingCart();
        this.firstPurchase = true;
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        // Check if the username only contains alphabet letters
        if (!username.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Username can only contain alphabet letters");
        }

        for (User user : WestminsterShoppingManager.getUserList()) {
            if (user.getUsername().equals(username) && user != this) {
                throw new IllegalArgumentException("Username already exists");
            } else if (user.getUsername().equals(username) && user == this) {
                continue;
            }
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.password = password;
    }

    public void setEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public void setFirstName(String firstName) {
        // Check if the first name only contains alphabet letters
        if (!firstName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("First name can only contain alphabet letters");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        // Check if the last name only contains alphabet letters
        if (!lastName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Last name can only contain alphabet letters");
        }
        this.lastName = lastName;
    }


    public void setAge(int age) {
        if (age <= 17 || age >= 100) {
            throw new IllegalArgumentException("Age must be a positive number and between 18 and 100");
        }
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
