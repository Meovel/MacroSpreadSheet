package spreadSheet;
import java.util.ArrayList;

/**
 * Function Class - Parses a function expression 
 * Framework based on lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public class Function {
    String name;
    ArrayList<String> variables;
    FuncExpression exp;
     
	public static Function parse(Tokenizer tok, WorkSheet w) throws ParseException {
		Function res = new Function();
		if (!(tok.current() instanceof String))  throw new ParseException();
		res.name = (String) tok.current();
		tok.next();
		res.variables =  new ArrayList<String>();
		tok.parse("(");
		
		String var;
		while (!")".equals(tok.current())) {
			var = (String) tok.current();
			res.variables.add(var);
			tok.next();
			if (tok.current().equals(",")) tok.next();
		}
		tok.parse(")");
		tok.parse("=");
		res.exp = FuncExpression.parse(tok, w);
		return res;
	}

	public String show() {
		String varstr = "";
		for (int i = 0; i< variables.size(); i++) varstr += variables.get(i) + (i <variables.size()-1?",":""); 
		return name + "(" + varstr + ")=" + exp.show();
	}
    
}
