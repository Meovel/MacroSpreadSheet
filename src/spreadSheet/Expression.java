package spreadSheet;

import java.util.HashMap;

/**
 * Expression - Abstract class used for parsing
 * Framework based on lecture material by Eric McCreath.
 * 
 * @author Eric McCreath
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public abstract class Expression {
	// this should show the expression as a string (which can be parsed back into a expression).
	public abstract String show(); 
	// this should evaluate the expression
    public abstract double evaluate();
    
    /* Grammar
     * 
     * <exp> ::= <term> | <term> + <exp> | <term> - <exp>
     * 
     * <term> ::= <term2> | <term2> * <term> | <term2> / <term>
     * 
     * <term2> ::= <term3> | <uniop><term3>
     * 
     * <term3> ::= <term4> | <term4> ^ <term3>
     * 
     * <term4> ::= (exp) | <num> | <cell> | <Func>
     * 
     * <uniop> ::= -
     * <cell> ::= <num> 
     * <Func> ::= <num>
     * 
     * Note: Only cells that begin with an "=" will be calculated 
     * 
     */
    
    // Top level, Addition and Subtraction
    static public Expression parse(Tokenizer tok, WorkSheet worksheet) throws ParseException {
    	Expression term = parseTerm(tok, worksheet);
		if (tok.current() != null && tok.current().equals("+")) {
			tok.next();
			Expression exp2 = parse(tok, worksheet);
			return exp2.includeAdd(term);
		} else if (tok.current() != null && tok.current().equals("-")) {
			tok.next();
			Expression exp2 = parse(tok, worksheet);
			return exp2.includeSub(term);
		} else {
			return term;
		}
    }
	
	// Multiplication and Division
    static public Expression parseTerm(Tokenizer tok, WorkSheet worksheet) throws ParseException {
    	Expression term2 = parseTerm2(tok, worksheet);
		if (tok.current() != null && tok.current().equals("*")) {
			tok.next();
			Expression exp2 = parseTerm(tok, worksheet); 
			return exp2.includeMult(term2);
		} else if (tok.current() != null && tok.current().equals("/")) {
			tok.next();
			Expression exp2 = parseTerm(tok, worksheet); 
			return exp2.includeDiv(term2);
		} else {
			return term2;
		}
    }
    
    // Unary Operator 
    static public Expression parseTerm2(Tokenizer tok, WorkSheet worksheet) throws ParseException {
		if (tok.current() != null && tok.current().equals("-")) {
			tok.next();
			Expression term3 = parseTerm3(tok, worksheet);
			return new Inverse(term3); 
		} else {
			Expression term3 = parseTerm3(tok, worksheet);
			return term3;
		}
    }
    
    // Power
    static public Expression parseTerm3(Tokenizer tok, WorkSheet worksheet) throws ParseException {
    	Expression term4 = parseTerm4(tok, worksheet);
		if (tok.current() != null && tok.current().equals("^")) {
			tok.next();
			Expression exp2 = parseTerm2(tok, worksheet);  
			return exp2.includePow(term4);
		} else {
			return term4;
		}
    }
    
    // Bracket, number or function, cells handled in Tokenizer
    static public Expression parseTerm4(Tokenizer tok, WorkSheet worksheet) throws ParseException {
    	Object t = tok.current();
		if (t instanceof Double) {
			double v = (double) t;
			tok.next();
			return new Number(v);
		} else if (t.equals("(")) {
			tok.next();
			Expression exp = parse(tok, worksheet);
			tok.parse(")");
			return new Brackets(exp);
		} else {
			FuncExpression e = FuncExpression.parse(new FunctionTokenizer(tok.current().toString()), worksheet);
			double v = (double) e.evaluate(new HashMap<String, Double>(), worksheet.getRealFunctions());
			return new Number(v);
		}
    }
    
    public abstract Expression includeSub(Expression term);
	public abstract Expression includeAdd(Expression term);
	public abstract Expression includeMult(Expression term);
	public abstract Expression includeDiv(Expression term);
	public abstract Expression includePow(Expression term);
}
