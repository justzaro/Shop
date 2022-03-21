package Model;

public class FavouriteItem {
    int customerID;
    int itemID;

    public FavouriteItem(int customerID, int itemID) {
        this.customerID = customerID;
        this.itemID = itemID;
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
}
