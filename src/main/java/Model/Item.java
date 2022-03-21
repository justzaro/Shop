package Model;

public class Item {
    int customerID;
    int itemID;
    double price;
    String condition;
    String name;
    String description;

    public Item (double price, String condition, String name, String description) {
        this.price = price;
        this.condition = condition;
        this.name = name;
        this.description = description;
    }

    public Item (int customerID, int itemID, double price, String condition, String name, String description) {
        this.customerID = customerID;
        this.itemID = itemID;
        this.price = price;
        this.condition = condition;
        this.name = name;
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
