package spreadSheet;

/**
 * Power - Expression used in parsing, framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Power extends Expression {
	Expression a;
	Expression b;
	
	public Power(Expression a, Expression b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String show() {
		return "( " + a.show() + " ** " + b.show() + " )";
	}

	@Override
	public double evaluate() {
		return Math.pow(a.evaluate(), b.evaluate());
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
		return new Power(a.includePow(term), b);
	}
}
