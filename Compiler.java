import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Compiler {

	//creates string from file
	public static String inputFileToString(String inputFilePath) {
		StringBuilder inputString = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				
				 if (line.startsWith("//")) {
		                continue;
				 }
				
				 inputString.append(line).append(" ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputString.toString();
	}

	
	public static void main(String[] args) {
	
		String file1 = inputFileToString("/Users/janisgrikstas/eclipse-workspace/PLCExam2/test1.txt");
		System.out.println(file1);
		
		Lexer lex = new Lexer(file1);
		ArrayList<Token> checking_lex = lex.lexYay();
		System.out.println(checking_lex.toString());
		
		Parser parsey = new Parser(checking_lex);
		System.out.print(parsey.getTree().toString());
		
}

}