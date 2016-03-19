package spreadSheet;

/**
 * Brackets - Expression used for parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Brackets extends Expression {
	
	Expression e;
	
	public Brackets(Expression e) {
		this.e = e;
	}
	
	@Override
	public String show() {
		return e.show();
	}

	@Override
	public double evaluate() {
		return e.evaluate();
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
