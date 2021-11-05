import java.io.*;
import java.util.*;

public class RPN {

	public static void main(String[] args) throws Exception {	
		String file_result = readStkFile("src/Calc1.stk");
		Stack<String> tokens = new Stack<String>();
		tokens.addAll(Arrays.asList(file_result.trim().split("[ \t]+")));
		try {
			double result = evalrpn(tokens);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Invalid Data!");
		}
	}

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
			else
				throw new Exception();
		}
		return op1;
	}

}
