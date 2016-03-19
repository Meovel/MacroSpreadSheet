package spreadSheet;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Test;

/**
 * 
 * SpreadsheetTest - This is a simple integration test.  
 * We basically set some text within cells of the spread sheet and check that they evaluate correctly.
 * 
 * @author Eric McCreath
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class SpreadsheetTest  {
	/**
	protected static final String sumandmaxfunctions = "SUM(array values) {\n" +
	                                                   "  double sum;" +
			                                           "  int i;" +
	                                                   "  sum = 0.0;" +
			                                           "  i = 0;" +
	                                                   "  while (i < #values) {" +
	                                                   "     sum = sum + values[i];" +
	                                                   "  }" +
	                                                   "  return sum;" +
	                                                   "}" + 
	                                                   "MAX(array values) {\n" +
	                                                   "  double max; " +
	                                                   "  int i;" +
	                                                   "  max = values[0];" +
	                                                   "  i = 1;" +
	                                                   "  while (i < #values) {" +
	                                                   "     if (values[i] > max) {" +
	                                                   "        max = values[i];" +
	                                                   "     }" +
	                                                   "  }" +
	                                                   "  return max;" +
	                                                   "}";
	**/
	protected static final String sumfunction = "sum(X,Y)=(X==0?Y:sum(dec(X),inc(Y)))\n"
			+ "max(X,Y)=(X>Y?X:Y)\n";
	Spreadsheet gui;

	@Test
	public void testSimple() {
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(1, 3, "Some Text");
					selectAndSet(4, 1, "5.12");
					//gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C2")).show(), "Some Text");
			assertEquals(gui.worksheet.lookup(new CellIndex("A5")).show(),
					"5.12");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	@Test
	public void testExpressionCal() {
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					selectAndSet(2, 3, "Some Text");
					selectAndSet(3, 3, "23.4");
					selectAndSet(4, 3, "34.1");
					selectAndSet(5, 3, "=2.6+C4*C5");
					selectAndSet(6,3, "");
					//gui.calculateButton.doClick();
				}
			});
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(), "Some Text");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"23.4");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"34.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"800.54");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	@Test
	public void testFunctionCal() {
		gui = new Spreadsheet();
		try {
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					gui.functioneditor.textarea.setText(sumfunction);
					gui.functioneditor.updateWorksheet();
					selectAndSet(2, 3, "1.1");
					selectAndSet(3, 3, "2.2");
					selectAndSet(4, 3, "3.3");
					selectAndSet(5, 3, "=sum(3,4)");
					selectAndSet(6, 3, "=max(C5,C6)");
					selectAndSet(7,3, "");
					//gui.calculateButton.doClick();
				}
			});
			
			assertEquals(gui.worksheet.lookup(new CellIndex("C3")).show(),
					"1.1");
			assertEquals(gui.worksheet.lookup(new CellIndex("C4")).show(),
					"2.2");
			assertEquals(gui.worksheet.lookup(new CellIndex("C5")).show(),
					"3.3");
			assertEquals(gui.worksheet.lookup(new CellIndex("C6")).show(),
					"7.0");
			assertEquals(gui.worksheet.lookup(new CellIndex("C7")).show(), "7.0");
		} catch (InvocationTargetException e) {
			fail();
		} catch (InterruptedException e) {
			fail();
		}
	}

	private void selectAndSet(int r, int c, String text) {
		gui.worksheetview.addRowSelectionInterval(r, r);
		gui.worksheetview.addColumnSelectionInterval(c, c);
		gui.cellEditTextField.setText(text);
	}
}
