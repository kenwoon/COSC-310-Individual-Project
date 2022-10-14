import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EditUI extends JDialog implements ActionListener {
    public EditUI() {
        super(null, "Edit Product", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}