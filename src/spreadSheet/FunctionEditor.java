package spreadSheet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FunctionEditor extends JDialog implements ActionListener {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * FunctionEditor - a simple modal dialog to edit the text of the functions. 
 * 
 * @author Eric McCreath
 * 
 */
	
	
	WorkSheet worksheet;
	JTextArea textarea;

	public FunctionEditor(WorkSheet worksheet) {
		this.worksheet = worksheet;
		textarea = new JTextArea(20, 50);
		JButton closebutton = new JButton("Close");
		closebutton.addActionListener(this);
		this.setModal(true);
		this.getContentPane().add(new JScrollPane(textarea), BorderLayout.CENTER);
		this.getContentPane().add(closebutton, BorderLayout.PAGE_END);
        this.pack();
	}

	public void setWorksheet(WorkSheet ws) {
		worksheet = ws;
		textarea.setText(worksheet.getFuctions());
	}
	public void actionPerformed(ActionEvent ae) { // it is always the close button
		updateWorksheet();
		dispose();
	}
	public void updateWorksheet() {
		worksheet.setFunctions(textarea.getText());
	}
}
