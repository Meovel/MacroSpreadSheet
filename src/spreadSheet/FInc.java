package spreadSheet;
import java.util.HashMap;

/**
 * Function Expression Increment - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class FInc extends FuncExpression {
	FuncExpression exp;
	
    public FInc(FuncExpression exp2) {
		this.exp = exp2;
	}
    
	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		return 1 + exp.evaluate(vars,functions);
	}

	@Override
	public String show() {
		return "inc(" + exp.show() + ")";
	}
}
