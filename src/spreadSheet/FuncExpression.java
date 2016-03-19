package spreadSheet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract Function Expression - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

public abstract class FuncExpression {

	public abstract  double evaluate(HashMap<String,Double> vars, Functions functions);
	public abstract  String show();
	
	public static FuncExpression parse(Tokenizer tok, WorkSheet w) throws ParseException {
		Object t = tok.current();
		if (t instanceof Integer) {
			int v = (Integer) t;
			tok.next();
			return new FNum(v);
		} else if (t.equals("inc")) {
			tok.next();
			tok.parse("(");
			FuncExpression exp = parse(tok, w);
			tok.parse(")");
			return new FInc(exp);
		} else if (t.equals("dec")) {
			tok.next();
			tok.parse("(");
			FuncExpression exp = parse(tok, w);
			tok.parse(")");
			return new FDec(exp);
		} else if (t.equals("(")) {
			tok.next();
			FuncExpression bool1 = parse(tok, w);
			String cop = "";
			if (tok.current().equals("=")) {
				tok.parse("=");
				tok.parse("=");
				cop = "==";
			} else if (tok.current().equals("!")) {
				tok.parse("!");
				tok.parse("=");
				cop = "!=";
			} else if (tok.current().equals(">")) {
				tok.parse(">");
				cop = ">";
			} else if (tok.current().equals("<")) {
				tok.parse("<");
				cop = "<";
			}
			FuncExpression bool2 = parse(tok, w);
			tok.parse("?");
			FuncExpression exp1 = parse(tok, w);
			tok.parse(":");
			FuncExpression exp2 = parse(tok, w);
			tok.parse(")");
			FuncBranch fb = new FuncBranch(bool1,cop,bool2,exp1,exp2);
			return fb;
		} else if (((String) t).length() == 2 && 
			Character.isDigit(((String) t).charAt(1))) {
			int column = ((String) t).charAt(0) - 65;
			int row = Integer.parseInt(((String) t).substring(1)) - 1;
			tok.next();
			Cell cell = w.lookup(new CellIndex(column, row));
			cell.calcuate(w);
			if (cell.value() != null) {
				return new FNum(cell.value());
			} else {
				throw new ParseException();
			}
		} else if (t instanceof String && Character.isUpperCase(((String) t).charAt(0))) {
			tok.next();
			return new FVariable((String) t);
		} else if (t instanceof String) {
			FunctionCall res = new FunctionCall();
			res.expressions = new ArrayList<FuncExpression>();
			res.name = (String) t;
			tok.next();
			tok.parse("(");
			while (!")".equals(tok.current())) {
				res.expressions.add(parse(tok, w));
				if (tok.current().equals(",")) tok.next();
			}
			tok.parse(")");
			return res;
		} else {
			throw new ParseException();
		}
	}

}
