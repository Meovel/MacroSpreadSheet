package spreadSheet;

import java.io.File;
import java.util.HashMap;

/**
 * WorkSheet - this stores the information for the worksheet. This is made up of
 * all the cells. all the cells of the worksheet.
 * 
 * @author Eric McCreath
 * 
 */

public class WorkSheet {

	private static final String SPREAD_SHEET = "SpreadSheet";
	private static final String INDEXED_CELL = "IndexedCell";
	private static final String CELL = "Cell";
	private static final String CELL_INDEX = "CellIndex";
	private static final String FUNCTIONTEXT = "FunctionText";
	private static final String FUNCTIONS = "Functions";
	int height, width;

	HashMap<CellIndex, Cell> tabledata;
	// For simplicity the table data is just stored as a hashmap from a cell's
	// index to the
	// cells data. Cells that are not yet part of this mapping are assumed
	// empty.
	// Once a cell is constructed the object is not replaced in this mapping,
	// rather
	// the data of the cell can be modified.

	private String functions;
	private Functions funcs;

	public WorkSheet() {
		tabledata = new HashMap<CellIndex, Cell>();
		functions = "";
		funcs = null;
	}

	public Cell lookup(CellIndex index) {
		Cell cell = tabledata.get(index);
		if (cell == null) { // if the cell is not there then create it and add
							// it to the mapping
			cell = new Cell();
			tabledata.put(index, cell);
		}
		return cell;
	}

	// calculate all the values for each cell that makes up the worksheet
	// should be abandoned with auto calculation
	public void calculate() throws ParseException {
		for (CellIndex i : tabledata.keySet()) {
			tabledata.get(i).calcuate(this);
		}
	}

	public void save(File file, int height, int width) {
		StoreFacade sf = new StoreFacade(file, SPREAD_SHEET);
		for (CellIndex i : tabledata.keySet()) {
			if (!tabledata.get(i).isEmpty()) {
				sf.start(INDEXED_CELL);
				sf.addText(CELL_INDEX, i.show());
				sf.addText(CELL, tabledata.get(i).getText());
			}
		}
		sf.start(FUNCTIONTEXT);
		sf.addText(FUNCTIONS, functions);
		sf.start("InterfaceSize");
		sf.addText("Height", height+"");
		sf.addText("Width", width+"");
		sf.close();
	}

	public static WorkSheet load(File file) {
		LoadFacade lf = LoadFacade.load(file);
		WorkSheet worksheet = new WorkSheet();
		String name;
		while ((name = lf.nextElement()) != null) {
			if (name.equals(INDEXED_CELL)) {
				String index = lf.getText(CELL_INDEX);
				String text = lf.getText(CELL);
				worksheet.put(index, text);
			} else if (name.equals(FUNCTIONTEXT)) {
				worksheet.setFunctions(lf.getText(FUNCTIONS));
			} else if (name.equals("InterfaceSize")) {
				int h = Integer.parseInt(lf.getText("Height"));
				int w = Integer.parseInt(lf.getText("Width"));
				worksheet.setSize(h, w);
			}
		}
		try {
			worksheet.calculate();
		} catch (ParseException e) {
			System.out.println("This is an illegal file");
		}
		return worksheet;
	}

	private void setSize(int h, int w) {
		this.height = h;
		this.width = w;
	}

	public String getFuctions() {
		return functions;
	}
	
	public Functions getRealFunctions(){
		return funcs;
	}

	public void setFunctions(String fun) {
		functions = fun;
		try {
			funcs = Functions.parse(new FunctionTokenizer(fun), this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void put(String index, String text) {
		tabledata.put(new CellIndex(index), new Cell(text));
	}
}
