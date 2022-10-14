import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AddUI extends JDialog implements ActionListener {
    public AddUI() {
        super(null, "Add Product", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}