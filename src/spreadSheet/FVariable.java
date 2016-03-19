package spreadSheet;
import java.util.HashMap;

public class FVariable extends FuncExpression {
    String var;
    
    public FVariable(String var) {
		this.var = var;
	}
	
	@Override
	public double evaluate(HashMap<String, Double> vars, Functions functions) {
		return vars.get(var);
	}

	@Override
	public String show() {
		
		return var;
	}

}
