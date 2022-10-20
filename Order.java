import java.time.*;

public class Order {
    Product product;
    int quantity;
    LocalDate date;
    public Order(Product _product, int _quantity, LocalDate _date) {
        this.product = _product;
        this.quantity = _quantity;
        this.date = _date;
    }

    public boolean hasArrived(LocalDate currentDate) {
        Period timeToShip = Period.of(0, 0, product.getShipTimeDays());
        LocalDate arrivalDate = date.plus(timeToShip);
        if (arrivalDate.isAfter(currentDate))
            return false;
        return true;
    }

    public void receive(Database db, MainUI ui) {
        Product original = Database.getProductById(product.getId(), db.products).get(0);
        int total = original.getCurrentStock() + quantity;
        original.setCurrentStock(total);
        ui.updateRows(db);
        ui.log("Order of " + quantity + " '" + product.getName() + "' has been received.");
    }
}