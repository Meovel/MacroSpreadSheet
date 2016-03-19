package spreadSheet;

/**
 * Addition - Expression used for parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Addition extends Expression {
	
	Expression a;
	Expression b;
	
	public Addition(Expression a, Expression b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String show() {
		return "( " + a.show() + " + " + b.show() + " )";
	}

	@Override
	public double evaluate() {
		return a.evaluate() + b.evaluate();
	}

	@Override
	public Expression includeSub(Expression term) {
		return new Addition(a.includeSub(term), b);
	}

	@Override
	public Expression includeAdd(Expression term) {
		return new Addition(a.includeAdd(term), b);
	}

	@Override
	public Expression includeMult(Expression term) {
		return new Addition(a.includeMult(term), b);
	}

	@Override
	public Expression includeDiv(Expression term) {
		return new Addition(a.includeDiv(term), b);
	}

	@Override
	public Expression includePow(Expression term) {
		return new Addition(a.includePow(term), b);
	}

}
