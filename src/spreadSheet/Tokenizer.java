package spreadSheet;

/**
 * Abstract Tokeniser - As provided in lectures
 * 
 * @author Eric McCreath
 * 
 */

public abstract class Tokenizer {

	abstract boolean hasNext();

	abstract Object current();

	abstract void next() throws ParseException;

	public void parse(Object o) throws ParseException {
		if (current() == null || !current().equals(o))
			throw new ParseException();
		next();
	}
}

