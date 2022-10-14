import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SetTimeUI extends JDialog implements ActionListener {
    public SetTimeUI() {
        super(null, "Fast-forward Time", ModalityType.DOCUMENT_MODAL);   // set modality so the main thread in InventorySystem that calls this constructor waits until this dialog gets disposed
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}