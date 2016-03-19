package spreadSheet;

/**
 * Subtraction - Expression used in parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Subtraction extends Expression {
	
	Expression a;
	Expression b;
	
	public Subtraction(Expression a, Expression b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String show() {
		return "( " + a.show() + " - " + b.show() + " )";
	}

	@Override
	public double evaluate() {
		return a.evaluate() - b.evaluate();
	}
	
	@Override
	public Expression includeSub(Expression term) {
		return new Subtraction(a.includeSub(term), b);
	}

	@Override
	public Expression includeAdd(Expression term) {
		return new Subtraction(a.includeAdd(term), b);
	}

	@Override
	public Expression includeMult(Expression term) {
		return new Subtraction(a.includeMult(term), b);
	}

	@Override
	public Expression includeDiv(Expression term) {
		return new Subtraction(a.includeDiv(term), b);
	}

	@Override
	public Expression includePow(Expression term) {
		return new Subtraction(a.includePow(term), b);
	}
	
}
