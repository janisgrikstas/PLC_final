import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String input;
    private ArrayList<Token> lexies;

    // Patterns for each type of token
    private Pattern real_lit_pattern = Pattern.compile("[+-]?\\d+\\.\\d+");
    private Pattern natural_lit_pattern = Pattern.compile("[+-]?\\d+");
    private Pattern bool_lit_pattern = Pattern.compile("true|false");
    private Pattern char_lit_pattern = Pattern.compile("'(\\\\[\\s\\S]|[^'])'");
    private Pattern string_lit_pattern = Pattern.compile("\"(\\\\[\\s\\S]|[^\"])*\"");
    private Pattern var = Pattern.compile("[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]?[A-Za-z]?");
   
    boolean currentlyInComment = false;

    // Constructor
    public Lexer(String input) {
        this.input = input;
        this.lexies = new ArrayList<Token>();
    }
    
    public static boolean isVar(String string) {
		 boolean bool = Pattern.matches("[A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z][A-Za-z]?[A-Za-z]?", string);  
		 return bool;
	 }

    
    public static void lexError(String token) {
		
		System.out.println("lexical error oh nooooooo " + "{"+token+"}");
	}
    
    // Convert the input string into a list of lexies
    public ArrayList<Token> lexYay() {
        // Split the input string into an array of individual words
        String[] words = input.split("\\s+");

        // Loop through each word
        for (String word : words) {
            // Ignore single-line comments
            if (word.startsWith("//")) {
                continue;
            }

            if(word.trim().startsWith("/*")){
               currentlyInComment = true;
               continue;
            }
            
            if(word.trim().startsWith("*/") && currentlyInComment) {
                currentlyInComment = false;
                continue;
            }
            

            // Check if the word matches any of the patterns for the different token types
            Matcher realLiteralMatcher = real_lit_pattern.matcher(word);
            Matcher naturalLiteralMatcher = natural_lit_pattern.matcher(word);
            Matcher boolLiteralMatcher = bool_lit_pattern.matcher(word);
            Matcher charLiteralMatcher = char_lit_pattern.matcher(word);
            Matcher stringLiteralMatcher = string_lit_pattern.matcher(word);

            
            
            if(!currentlyInComment) {
            
            	
            	
            // If the word matches a real literal pattern, create a real_lit token
            if (realLiteralMatcher.matches()) {
                lexies.add(new Token("real_lit", 1)); 
            }
            else if(word.equals("begin")) {
            	lexies.add(new Token("begin", 2));
            }
            // If the word matches a natural literal pattern, create a nat_lit token
            else if (naturalLiteralMatcher.matches()) {
            	lexies.add(new Token("nat_lit", 3));
            }
            // If the word matches a bool literal pattern, create a bool_lit token
            else if (boolLiteralMatcher.matches()) {
            	lexies.add(new Token("bool_lit", 4));
            }
            // If the word matches a char literal pattern, create a char_lit token
            else if (charLiteralMatcher.matches()) {
            	lexies.add(new Token("char_lit", 5));
            }
            // If the word matches a string literal pattern, create a string_lit token
            else if (stringLiteralMatcher.matches()) {
            	lexies.add(new Token("string_lit", 6));
            }
            
            //keyword for function declaration
            else if(word.equals("function")) {
            	lexies.add(new Token("function_dec", 7));
            }	
            //keyword for string declaration
            else if(word.equals("String")) {
            	lexies.add(new Token("string_dec", 8));
            }
            //keyword for nat_lit declaration
            else if(word.equals("int")) {
            	lexies.add(new Token("nat_dec", 9));
            }
            //keyword for real_lit declaration
            else if(word.equals("double")) {
            	lexies.add(new Token("real_dec", 10));
            }
            //keyword for if statement
            else if(word.equals("if")) {
            	lexies.add(new Token("if", 11));
            }
            //keyword for else statement
            else if(word.equals("else")) {
            	lexies.add(new Token("else", 12));
            }
            //keyword for while loop
            else if(word.equals("while")) {
            	lexies.add(new Token("while_loop", 13));
            }
            //keyword for bool_lit declaration
            else if(word.equals("boolean")) {
            	lexies.add(new Token("bool_dec", 14));
            }
            //keyword for char_lit declaration
            else if(word.equals("char")) {
            	lexies.add(new Token("char_dec", 15));
            }
            //or operator
            else if(word.equals("||")) {
            	lexies.add(new Token("||", 16));
			}
            //and operator
            else if(word.equals("&&")) {
				lexies.add(new Token("&&", 17));
			}
			//end of statement operator
            else if(word.equals(";")) {
				lexies.add(new Token(";", 18));
			}
			// addition operator
            else if(word.equals("+")) {
				lexies.add(new Token("+", 19));
			}
			//subtraction operator
            else if(word.equals("-")) {
				lexies.add(new Token("-", 20));
			}
			//multiplication operator
            else if(word.equals("*")) {
				lexies.add(new Token("*", 21));
			}
			//mod operator
            else if(word.equals("%")) {
				lexies.add(new Token("%", 22));
			}
			//assignment operator
            else if(word.equals("=")) {
				lexies.add(new Token("=", 23));
			}
			//less than bool operator
            else if(word.equals("<")) {
				lexies.add(new Token("<", 24));
			}
			//greater than bool operator
            else if(word.equals(">")) {
				lexies.add(new Token(">", 25));
			}
			//less than or equal to bool operator
            else if(word.equals("<<=")) {
				lexies.add(new Token("<=", 26));
			}
			//greater than or equal to bool operator
            else if(word.equals(">>=")) {
				lexies.add(new Token(">=", 27));
			}
			//equal to bool operator
            else if(word.equals("===")) {
				lexies.add(new Token("==", 28));
			}
			// not equal to bool operator
            else if(word.equals("!==")) {
				lexies.add(new Token("!==", 29));
			}
			// left para
            //breaking order of precedence
            else if(word.equals( "(")) {
				lexies.add(new Token("(", 30));
			}
			//right para
            //breaking order of precendence
            else if(word.equals(")")) {
				
				lexies.add(new Token(")", 31));
			}
			// opening bracket
            //grouping code blocks
            else if(word.equals("{")) {
				lexies.add(new Token("{", 32));
			}
			// closing bracket
            //grouping code blocks
            else if(word.equals("}")) {
				lexies.add(new Token("}", 33));
			}
            //parameter seperator
            else if(word.equals(",")) {
				lexies.add(new Token(",", 34));
			}			
			//checking for variable or function name
            else if(isVar(word)) {
				lexies.add(new Token("variable", 35));
			}
			//debugging some of my lexical errors idk
            else if(word.equals("")) {
				
			}
			//checking for new line characters
            else if(word.equals("\\n")) {
				
			}
			//prints an error and which word is causing it
            else {
				
				lexError(word);
				break;
			}
      		}
            }
        return lexies;
    
    }

}
        