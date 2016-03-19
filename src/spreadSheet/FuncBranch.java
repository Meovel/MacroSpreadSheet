package spreadSheet;
import java.util.HashMap;

/**
 * Function Branch - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class FuncBranch extends FuncExpression {
	FuncExpression bool1, bool2, exp1, exp2;
	String cop;
	
	
	public FuncBranch(FuncExpression bool21, String compare, FuncExpression bool22,FuncExpression exp12, FuncExpression exp22) {
		super();
		this.bool1 = bool21;
		this.cop = compare;
		this.bool2 = bool22;
		this.exp1 = exp12;
		this.exp2 = exp22;
	}

	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		boolean f = false ;
		if (this.cop=="==") {
			f = bool1.evaluate(vars,functions) == bool2.evaluate(vars,functions);
		} else if (this.cop=="!=") {
			f = bool1.evaluate(vars,functions) != bool2.evaluate(vars,functions);
		} else if (this.cop==">") {
			f = bool1.evaluate(vars,functions) > bool2.evaluate(vars,functions);
		} else if (this.cop=="<") {
			f = bool1.evaluate(vars,functions) < bool2.evaluate(vars,functions);
		}
		return (f ? exp1.evaluate(vars,functions) : exp2.evaluate(vars,functions));
	}

	@Override
	public String show() {
		return "(" + bool1.show() + cop + bool2.show() + exp1.show() + ":" + exp2.show() + ")";
	}

}
