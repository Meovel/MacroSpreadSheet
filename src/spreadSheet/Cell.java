package spreadSheet;

/**
 * Cell - an object of this class holds the data of a single cell. 
 * 
 * @author Eric McCreath
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Cell {

	private String text; // this is the text the person typed into the cell
	private Double calculatedValue; // this is the current calculated value for
									// the cell

	public Cell(String text) {
		this.text = text;
		calculatedValue = null;
	}

	public Cell() {
		text = "";
		calculatedValue = null;
	}

	public Double value() {
		return calculatedValue;
	}

	public void calcuate(WorkSheet worksheet) throws ParseException { 
		try {
			if (text.length() != 0) {
				if (text.charAt(0) == '=') { // Ensure that we are looking for an expression 
					Tokenizer t = new ExpressionTokenizer(text.substring(1), worksheet);
					Expression exp = Expression.parse(t, worksheet);
					calculatedValue = exp.evaluate();
				}else {
					calculatedValue = Double.parseDouble(text);
				}
			} else {
				calculatedValue = null;
			}
		} catch (NumberFormatException nfe) {
			calculatedValue = null;
		}
	}

	public String show() { // this is what is viewed in each Cell
		return calculatedValue == null ? text : calculatedValue.toString();
	}

	@Override
	public String toString() {
		return text + "," + calculatedValue;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isEmpty() {
		return text.equals("");
	}
}
