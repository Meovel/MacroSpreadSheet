package spreadSheet;

/**
 * Expression Tokeniser - Modified tokeniser based on Eric McCreath's lecture examples
 * 
 * @author Eric McCreath
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class ExpressionTokenizer extends Tokenizer {
	
	private String text;
	private int pos;
	private Object current;
	private WorkSheet worksheet;

	static final char whitespace[] = { ' ', '\n', '\t' };
	static final char symbol[] = {'(', ')','*', '+', '-', '/', '.', '=', '^'};

	public ExpressionTokenizer(String text, WorkSheet worksheet) throws ParseException {
		this.text = text;
		this.pos = 0;
		this.worksheet= worksheet;
		next();
	}

	boolean hasNext() {
		return current != null;
	}

	Object current() {
		return current;
	}

	public void next() throws ParseException {
		consumewhite();	
		if (pos == text.length()) {
			current = null;
		} else if (isin(text.charAt(pos), symbol)) {
			current = "" + text.charAt(pos);
			pos++;
		} else if (Character.isDigit(text.charAt(pos))) {
			int start = pos;
			while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
				pos++;
			}
			if (pos+1 < text.length() && text.charAt(pos) == '.' &&
					Character.isDigit(text.charAt(pos+1))) {
				pos++;
				while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
					pos++;
				}
				current = Double.parseDouble(text.substring(start, pos));
			} else {
			    current = Double.parseDouble(text.substring(start, pos));
			}

		} else if (pos+1 < text.length() && 
				Character.isUpperCase(text.charAt(pos)) && 
				Character.isDigit(text.charAt(pos+1)))  {
			int column = ((int) text.charAt(pos)) - 65;
			pos++;

			int start = pos;
			while (pos < text.length() && Character.isDigit(text.charAt(pos))) {
				pos++;
			}
			
			if (start != pos) {
				int row = Integer.parseInt(text.substring(start, pos))-1;
				Cell cell = worksheet.lookup(new CellIndex(column, row));
				if (cell.value() != null) {
					current = cell.value();
				} else {
					throw new ParseException();
				}
			}
		} else {
			int start = pos;
			int diff = 0;
			boolean f = true;
			while (pos < text.length() && (diff!=0 || f)){
				if (text.charAt(pos) == '(') {
					diff++;
					f = false;
				}
				if (text.charAt(pos) == ')') {
					diff--;
					f = false;
				}
				pos++;
			}
			if (pos == text.length() && diff!=0) throw new ParseException();
			else  current = text.substring(start, pos);
		}
	}

	private void consumewhite() {
		while (pos < text.length() && isin(text.charAt(pos), whitespace)) {
			pos++;
		}
	}

	private boolean isin(char c, char charlist[]) {
		for (char w : charlist) {
			if (w == c)
				return true;
		}
		return false;
	}

}
