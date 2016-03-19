package spreadSheet;

/**
 * Number - Expression used in parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Number extends Expression {
	
	double value;
	
	public Number(double v) {
		this.value = v;
	}
	
	@Override
	public String show() {
		return "" + value;
	}

	@Override
	public double evaluate() {
		return value;
	}

	@Override
	public Expression includeSub(Expression term) {
		return new Subtraction(term, this);
	}

	@Override
	public Expression includeAdd(Expression term) {
		return new Addition(term, this);
	}

	@Override
	public Expression includeMult(Expression term) {
		return new Multiplication(term, this);
	}

	@Override
	public Expression includeDiv(Expression term) {
		return new Division(term, this);
	}

	@Override
	public Expression includePow(Expression term) {
		return new Power(term, this);
	}
	
}
