import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TransactionUI extends JDialog implements ActionListener {
    public TransactionUI() {
        super(null, "Fake a Transaction", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}