package spreadSheet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Dialog Box used for delaing with errors
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class IncorrectFormatDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTextField cellField;
	JButton apply;
	Boolean didChange = false;
	
	public IncorrectFormatDialog(JFrame window, String action, Cell last) {
		super(window, action, true);
		
		getContentPane().setLayout(new FlowLayout());
		
		JLabel errorLabel = new JLabel("Incorrect Format or Empty Cell Reference:");
		getContentPane().add(errorLabel);
		
		cellField = new JTextField(15);
		getContentPane().add(cellField);
		cellField.setText(last.getText());
		
		// Buttons
		apply = new JButton("Change");
		apply.addActionListener(this);
		apply.setActionCommand("APPLY");
		getContentPane().add(apply);
		
		getRootPane().setDefaultButton(apply);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "APPLY") {
			didChange = true;
			setVisible(false);
		}
	}
	
	public String getNewCellText() {
		return cellField.getText();
	}

}
