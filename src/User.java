import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private ShoppingCart shoppingCart;
    private boolean firstPurchase;

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public User() {
        this.shoppingCart = new ShoppingCart();
        this.firstPurchase = true;
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
        for(User user : WestminsterShoppingManager.getUserList()) {
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
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

}
