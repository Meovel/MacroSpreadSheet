package spreadSheet;
import java.util.HashMap;

/**
 * Function Expression Number - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class FNum extends FuncExpression {

    double value;
	
	public FNum(double v) {
		value = v;
	}
	
	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		return value;
	}

	@Override
	public String show() {
		return "" + value;
	}
	
	

}
