public class InventorySystem {
    static MainUI ui;
    public static void main(String[] args) {
        // password check
        PasswordUI passwordDialog = new PasswordUI();   // thread will wait until passwordDialog is disposed before continuing
        if (!passwordDialog.verified)
            return;

        ui = new MainUI(new String[][]{{"1", "pencils", "30"}}, new String[]{"id", "name", "currentStock"}, "C:\\Users\\Paul\\Documents\\School\\etcbruhjustgonnapadthisstringsuperlong.csv");
    }

    public static void load() {         // this method is called by the listener in MainUI
        ui.log("load");
    }
    public static void save() {         // this method is called by the listener in MainUI
        ui.log("save");
    }
    public static void add() {          // this method is called by the listener in MainUI
        ui.log("add");
    }
    public static void edit() {         // this method is called by the listener in MainUI
        ui.log("edit");
    }
    public static void order() {        // this method is called by the listener in MainUI
        ui.log("order");
    }
    public static void transaction() {  // this method is called by the listener in MainUI
        ui.log("transaction");
    }
    public static void setTime() {      // this method is called by the listener in MainUI
        ui.log("set time");
    }
}