package spreadSheet;
import java.util.ArrayList;

/**
 * Functions ArrayList class - framework based on 
 * lecture material by Eric McCreath
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

@SuppressWarnings("serial")
public class Functions extends ArrayList<Function> {
	
	public Function find(String name) {
		for(Function f : this) {
			if (f.name.equals(name)) return f;
		}
		return null;
	}

	static Functions parse(Tokenizer tok, WorkSheet w) throws ParseException {
		Functions res = new Functions();
		while (tok.hasNext()) {
			res.add(Function.parse(tok, w));
		}
		return res;
	}
	
	String show() {
	   String res = "";
	   for (Function f : this) {
		   res += f.show();
	   }
	   return res;
	}
	
}
