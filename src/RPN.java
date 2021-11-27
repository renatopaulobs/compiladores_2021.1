import java.io.*;
import java.util.*;

public class RPN {

	private static String readStkFile(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
		    StringBuilder sb = new StringBuilder();
		    String read_line = br.readLine();

		    while (read_line != null) {
		        sb.append(read_line);
		        sb.append(" ");
		        read_line = br.readLine();
		    }
		    return sb.toString();
		} finally {
		    br.close();
		}
	}
	
	private static double evalrpn(Stack<String> tokens) throws Exception {
		String token = tokens.pop();
		double op1, op2;
		try {
			op1 = Double.parseDouble(token);
		} catch (Exception e) {
			op2 = evalrpn(tokens);
			op1 = evalrpn(tokens);
			if (token.equals("-"))
				op1 -= op2;
			else if (token.equals("+"))
				op1 += op2;
			else if (token.equals("*"))
				op1 *= op2;
			else if (token.equals("/"))
				op1 /= op2;
			else {
				throw new Exception();
			}
		}
		return op1;
	}
	
	private static boolean isScanningValid(Stack<String> tokens) {
		Stack<String> evalToken = (Stack<String>) tokens.clone();
		String token = null;
		List<String> lexicalMsg = new ArrayList<String>();
		
		Token Test = null;
		
		while (!evalToken.isEmpty()) {
			token = evalToken.pop();
	
			if (token.matches("\\d*(\\.\\d+)?")) {
				Test = new Token(TokenType.NUM, token);			
			} else if (token.equals("-")) {
				Test = new Token(TokenType.MINUS, token);
			} else if (token.equals("+")) {
				Test = new Token(TokenType.PLUS, token);
			} else if (token.equals("*")) {
				Test = new Token(TokenType.STAR, token);
			} else if (token.equals("/")) {
				Test = new Token(TokenType.SLASH, token);
			} else {
				System.out.println("Error: Unexpected character: "+token);
				return false;
			}
			lexicalMsg.add(Test.toString());
			
		}
		
		for (int i=lexicalMsg.size()-1; i>=0; i--)
			System.out.println(lexicalMsg.toArray()[i]);
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String file_result = readStkFile("src/Calc1.stk");
		Stack<String> tokens = new Stack<String>();	
		tokens.addAll(Arrays.asList(file_result.trim().split("[ \t]+")));
		
		if (isScanningValid(tokens)) {
			try {
				double result = evalrpn(tokens);
				System.out.println("\n"+result);
			} catch (Exception e) {
				System.out.println("\nSemantic Invalid Expression");
			}
		}
		
	}

}
