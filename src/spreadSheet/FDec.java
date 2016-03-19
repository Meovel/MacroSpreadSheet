package spreadSheet;
import java.util.HashMap;

/**
 * Function Expression Decrement - Based on lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class FDec extends FuncExpression {
	FuncExpression exp;
	
    public FDec(FuncExpression exp2) {
		this.exp = exp2;
	}
	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		double v = exp.evaluate(vars,functions);
		return (v==0?v:v - 1);
	}

	@Override
	public String show() {
		return "dec(" + exp.show() + ")";
	}

}
