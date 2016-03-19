package spreadSheet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Function Call Expression - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class FunctionCall extends FuncExpression {

	String name;
	ArrayList<FuncExpression> expressions;
	
	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		Function fun = functions.find(name);
		HashMap<String,Double> callmap = new HashMap<String,Double>();
		for (int i=0;i<fun.variables.size();i++) {
			callmap.put(fun.variables.get(i), expressions.get(i).evaluate(vars, functions));
		}
		return fun.exp.evaluate(callmap,functions);
	}

	@Override
	public String show() {
		String res = "";
		for(int i=0;i<expressions.size();i++) {
			res += expressions.get(i).show() + (i<expressions.size()-1?",":"");
		}
		return name + "(" + res + ")";
	}

}
