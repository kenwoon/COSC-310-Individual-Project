import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TransactionUI extends JDialog implements ActionListener {
    JTable dataTable;
    public TransactionUI(JTable _dataTable) {
        super(null, "Fake a Transaction", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        dataTable = _dataTable;
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}