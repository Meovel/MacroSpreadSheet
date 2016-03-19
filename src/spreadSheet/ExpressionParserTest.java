package spreadSheet;

/**
 * Expression Parser Test - Tries different expressions to see if they evaluate correctly 
 * 
 * @author Hayden Dummett u5563578
 * @author Hao Zhang u5319850
 * 
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpressionParserTest {
	
	public static String expressions[] = {
			"2 + 2",
			"-2 + 2",
			"4 - 5 + 6 * (3 + 2)",
			"-2 + -2",
			"(3 * 4) + (3 * 5)",
			"(3 * 4) / (3 * 5)",
			"(-3 * 4) - -(5 + 2)",
			"-2^-3",
			"2^-3",
			"2 * 3^2",
			"-3 / 2",
			"5 + (-(2 - -3) * 6) "
	};
	
	public static double expAnswers[] = {
			4.0,
			0.0,
			29.0,
			-4.0,
			27.0,
			0.8,
			-5.0,
			-0.125,
			0.125,
			18,
			-1.5,
			-25
	};
	
	WorkSheet w = new WorkSheet();
	
	@Test
	public void expressionsTest() throws ParseException {
		for (int i = 0; i < expressions.length; i++) {
			Tokenizer tok = new ExpressionTokenizer(expressions[i], w);
			Expression exp = Expression.parse(tok, w);
			double val = exp.evaluate();
			assertTrue(val == expAnswers[i]);
		}
	}
	
}
