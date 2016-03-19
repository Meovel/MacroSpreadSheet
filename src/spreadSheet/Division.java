package spreadSheet;

/**
 * Division - Expression used for parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Division extends Expression {
	
	Expression a;
	Expression b;
	
	public Division(Expression a, Expression b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String show() {
		return "( " + a.show() + " / " + b.show() + " )";
	}

	@Override
	public double evaluate() {
		return a.evaluate() / b.evaluate();
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
		return new Division(a.includeMult(term), b);
	}

	@Override
	public Expression includeDiv(Expression term) {
		return new Division(a.includeDiv(term), b);
	}

	@Override
	public Expression includePow(Expression term) {
		return new Division(a.includePow(term), b);
	}
}
