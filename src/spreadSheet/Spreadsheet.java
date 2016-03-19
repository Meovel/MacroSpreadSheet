package spreadSheet;

/**
 * Spreadsheet - GUI class
 * 
 * @author Eric McCreath
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Spreadsheet implements Runnable, ActionListener,
		SelectionObserver, DocumentListener {

	private static final Dimension PREFEREDDIM = new Dimension(1280, 640);

	private static final String EXITCOMMAND = "exitcommand";
	private static final String CLEARCOMMAND = "clearcommand";
	private static final String NEWCOMMAND = "newcommand";
	private static final String SAVECOMMAND = "savecommand";
	private static final String OPENCOMMAND = "opencommand";
	private static final String CHANGEROWSCOMMAND = "changerowscommand";
	private static final String CHANGECOLUMNSCOMMAND = "changecolumnscommand";
	private static final String EDITFUNCTIONCOMMAND = "editfunctioncommand";
	private static final String CALCULATE = "calculate";

	JFrame jframe;
	JDialog cr;
	WorksheetView worksheetview;
	FunctionEditor functioneditor;
	WorkSheet worksheet;
	JButton calculateButton;
	JTextField cellEditTextField, rows;
	JLabel selectedCellLabel;
	Cell last = null;
	JFileChooser filechooser = new JFileChooser();

	public Spreadsheet() {
		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		new Spreadsheet();
	}

	public void run() {
		jframe = new JFrame("Spreadsheet");
		//jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLocation(50, 50);
		// set up the menu bar
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		bar.add(menu);
		makeMenuItem(menu, "New Sheet", CLEARCOMMAND);
		makeMenuItem(menu, "New Instance", NEWCOMMAND);
		makeMenuItem(menu, "Open", OPENCOMMAND);
		makeMenuItem(menu, "Save", SAVECOMMAND);
		makeMenuItem(menu, "Exit", EXITCOMMAND);
		menu = new JMenu("Edit");
		bar.add(menu);
		makeMenuItem(menu, "Row Number", CHANGEROWSCOMMAND);
		makeMenuItem(menu, "Column Width", CHANGECOLUMNSCOMMAND);
		makeMenuItem(menu, "Edit Function", EDITFUNCTIONCOMMAND);

		jframe.setJMenuBar(bar);
		worksheet = new WorkSheet();
		worksheetview = new WorksheetView(worksheet);
		worksheetview.addSelectionObserver(this);

		// set up the tool area
		JPanel toolarea = new JPanel();
		//calculateButton is removed due to auto calculation
		//calculateButton = new JButton("Calculate");
		//calculateButton.addActionListener(this);
		//calculateButton.setActionCommand(CALCULATE);
		//toolarea.add(calculateButton);
		selectedCellLabel = new JLabel("--  ");
		selectedCellLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		toolarea.add(selectedCellLabel);
		cellEditTextField = new JTextField(20);
		cellEditTextField.getDocument().addDocumentListener(this);
		toolarea.add(cellEditTextField);

		functioneditor = new FunctionEditor(worksheet);

		jframe.getContentPane().add(new JScrollPane(worksheetview),
				BorderLayout.CENTER);
		jframe.getContentPane().add(toolarea, BorderLayout.PAGE_START);
		
		//jframe.getRootPane().setDefaultButton(calculateButton);
		jframe.setVisible(true);
		jframe.setPreferredSize(PREFEREDDIM);
		jframe.pack();
	}

	private void makeMenuItem(JMenu menu, String name, String command) {
		JMenuItem menuitem = new JMenuItem(name);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		menuitem.setActionCommand(command);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(EXITCOMMAND)) {
			exit();
		} else if (ae.getActionCommand().equals(SAVECOMMAND)) {
			int res = filechooser.showOpenDialog(jframe);
			if (res == JFileChooser.APPROVE_OPTION) {
				worksheet.save(filechooser.getSelectedFile(), jframe.getHeight(), jframe.getWidth());
			}
		} else if (ae.getActionCommand().equals(OPENCOMMAND)) {
			int res = filechooser.showOpenDialog(jframe);
			if (res == JFileChooser.APPROVE_OPTION) {
				worksheet = WorkSheet.load(filechooser.getSelectedFile());
				worksheetChange();
			}
		} else if (ae.getActionCommand().equals(CLEARCOMMAND)) {
			worksheet = new WorkSheet();
			worksheetChange();
		} else if (ae.getActionCommand().equals(NEWCOMMAND)) {
			new Spreadsheet();
		} else if (ae.getActionCommand().equals(CHANGEROWSCOMMAND)) {
			cr = new JDialog();
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			rows = new JTextField(8);
			JButton button = new JButton("OK");
			button.addActionListener(this);
			button.setActionCommand("changenorows");
			cr.getContentPane().add(new JScrollPane(rows), BorderLayout.CENTER);
			cr.getContentPane().add(button, BorderLayout.PAGE_END);
			cr.setLocation(600, 300);
			cr.setVisible(true);
			cr.pack();
		} else if (ae.getActionCommand().equals("changenorows")) {
			cr.setVisible(false);
			try {
				String text = rows.getText();
				if (!text.equals("")){
					int norows = Integer.parseInt(text);
					worksheetview.NUMROWS = norows;
					worksheetChange();
				}
			} catch (NumberFormatException e){
				System.out.println("Invalid number");
			}
		} else if (ae.getActionCommand().equals(CHANGECOLUMNSCOMMAND)) {
			cr = new JDialog();
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			rows = new JTextField(8);
			JButton button = new JButton("OK");
			button.addActionListener(this);
			button.setActionCommand("changenocolumns");
			cr.getContentPane().add(new JScrollPane(rows), BorderLayout.CENTER);
			cr.getContentPane().add(button, BorderLayout.PAGE_END);
			cr.setLocation(600, 300);
			cr.setVisible(true);
			cr.pack();
		} else if (ae.getActionCommand().equals("changenocolumns")) {
			cr.setVisible(false);
			try {
				String text = rows.getText();
				if (!text.equals("")){
					int norows = Integer.parseInt(text);
					worksheetview.COLUMNWIDTH = norows;
					worksheetChange();
				}
			} catch (NumberFormatException e){
				System.out.println("Invalid number");
			}
		} else if (ae.getActionCommand().equals(EDITFUNCTIONCOMMAND)) {
			functioneditor.setVisible(true);
		} else if (ae.getActionCommand().equals(CALCULATE)) {
			try {
				worksheet.calculate();
			} catch (ParseException e) {
				
				// Initialize the handler dialog and bring it on screen
				IncorrectFormatDialog handler = new IncorrectFormatDialog(jframe, "Expression Error", last);
				handler.setLocationRelativeTo(jframe);
				handler.setVisible(true);

				// If the apply button is pressed do this
				if (handler.didChange) {
					last.setText(handler.getNewCellText());
				}

				handler.dispose();
				update();
			}
		 	worksheetChange();
		}
	}

	private void worksheetChange() {
		jframe.setSize(worksheet.width, worksheet.height);
		worksheetview.setWorksheet(worksheet);
		functioneditor.setWorksheet(worksheet);
		worksheetview.repaint();
	}

	private void exit() {
		System.exit(0);
	}

	@Override
	public void update() {
		CellIndex index = worksheetview.getSelectedIndex();
		selectedCellLabel.setText(index.show());
		cellEditTextField.setText(worksheet.lookup(index).getText());
		
		if (last != null){
			try {
				last.calcuate(worksheet);
			} catch (ParseException e) { // Can add more than one exception type to specify to user
				// Initialize the handler dialog and bring it on screen
				IncorrectFormatDialog handler = new IncorrectFormatDialog(jframe, "Expression Error", last);
				handler.setLocationRelativeTo(jframe);
				handler.setVisible(true);

				// If the apply button is pressed do this
				if (handler.didChange) {
					last.setText(handler.getNewCellText());
				}

				handler.dispose();
				update();
			}
		}
		last = worksheet.lookup(index);
		
		jframe.repaint();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		textChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		textChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		textChanged();
	}

	private void textChanged() {
		CellIndex index = worksheetview.getSelectedIndex();
		Cell current = worksheet.lookup(index);
		current.setText(cellEditTextField.getText());
		worksheetview.repaint();
	}
}
