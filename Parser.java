import java.util.ArrayList;

public class Parser {
	
	
	public int pointer;
	public ArrayList<Token> parsing; //= new ArrayList<Token>();
	public ArrayList<String> parseTree; //= new ArrayList<String>();

	 Parser (ArrayList<Token> tokens) {
		 parsing = tokens;
		 pointer = 0;
		 parseTree = new ArrayList<String>();
		 
		 Token value = parsing.get(pointer);
		 //program starter -> “begin”
		 if(value.lexeme.equals("begin")) {
			 getNextToken();
			 parseTree.add("check_statement");
			 checkStatement();
		 	}else {syntaxError(currentToken(),"line 22");}
		
		 }

	 public  String getNextToken() {
		 if(pointer<parsing.size()-1) {
			 ++pointer;
			 return parsing.get(pointer).lexeme; 
			}
		 else return "end of code";
	 }
	 
	 public String currentToken() {
		 return parsing.get(pointer).lexeme;
	 }
	 
	 public void checkStatement() {
		String current = currentToken(); 
		if(current.equals("string_dec")||current.equals("nat_dec")||current.equals("real_dec")||current.equals("bool_dec")||current.equals("char_dec" )) {
			getNextToken();
			parseTree.add("check_var");
			checkVar();
		}else if(current.equals("variable")) {
			parseTree.add("check_var");
			checkVar();
		}else if(current.equals("{")){
			parseTree.add("check_block");
			checkBlock();
		}else if(current.equals("while_loop")) {
			parseTree.add("check_while");
			checkWhile();
		}else if(current.equals("if")) {
			parseTree.add("check_switch");
			checkSwitch();
		}else if(current.equals("function_dec")) {
			parseTree.add("check_function");
			checkFunction();
		}else {syntaxError(current,"line 58");}
	 }
	 
	 
	 public void checkVar() {
		 if(currentToken().equals("variable")) {
			 getNextToken();
			 if(currentToken().equals(";")) {
				 getNextToken();
				 checkStatement();
			 }else if(currentToken().equals("=")) {
				 getNextToken();
				 parseTree.add("check_expression");
				 checkExpression();
			 }else syntaxError(currentToken(),"line 72");
		 }
	 }
	 
	 public void checkFunction() {
		 if(currentToken().equals("function_dec")) {
			 getNextToken();
			 
			 if(currentToken().equals("variable")) {
				 getNextToken();
				 if(currentToken().equals("(")) {
					 getNextToken();
					 parseTree.add("check_parameter");
					 checkParameter();
					 if(currentToken().equals(")")) {
						 getNextToken();
						 parseTree.add("check_block");
						 checkBlock();
					 
				 	}else if( currentToken().equals(",")) {
				 			getNextToken();
				 			checkParameter();
				 		
			 		}else syntaxError(currentToken(),"line 90");
			 	}else syntaxError(currentToken(),"line 92");
		 	}else syntaxError(currentToken(),"line 93");
		 }
	 } 
	 
	 public void checkBlock() {
		 if(currentToken().equals("{")) {
			String current =  getNextToken();
			
			if(current.equals("string_dec")||current.equals("nat_dec")||current.equals("real_dec")||current.equals("bool_dec")||current.equals("char_dec")) {
				getNextToken();
				parseTree.add("check_var");
				checkVar(); 
			
			if(current.equals("while_loop")|| current.equals("variable") || current.equals("if")) {
				 parseTree.add("check_statement");
				 checkStatement();
				 if(currentToken().equals(";")) {
					 getNextToken();
					 parseTree.add("check_statement");
					 checkStatement();
				 } 
				 if (currentToken().equals("}")) {
					 getNextToken();
				 } else syntaxError(currentToken(),"line 116");
			 }
		 }
		 }
		 }	
	 
	
	 public void checkWhile() {
		if(currentToken().equals("while_loop")) {
			getNextToken();
			if( currentToken().equals("(")) {
				getNextToken();
				parseTree.add("check_bool");
				checkBool();
				if(currentToken().equals(")")) {
					getNextToken();
					parseTree.add("check_block");
					checkBlock();
				} else { syntaxError(currentToken(),"line 133");}
			} else {syntaxError(currentToken(),"line 134");}
		}else {syntaxError(currentToken(),"line 135");} 
	 }
	 
	 public void checkSwitch() {
		 if(currentToken().equals("if")) {
			 getNextToken();
			 if(currentToken().equals("(")) {
				 getNextToken();
				 parseTree.add("check_bool");
				 checkBool();
				 if(currentToken().equals(")")) {
					 getNextToken();
					 parseTree.add("check_block");
					 checkBlock();
					 if(currentToken().equals("else")) {
						 getNextToken();
						 parseTree.add("check_block");
						 checkBlock();
					 }
				 }else {syntaxError(currentToken(),"line 154");}
			 }else {syntaxError(currentToken(),"line 155");}
		 
		 
		 
		 }else {syntaxError(currentToken(),"line 159");}
		 
	 }
	 
	 
	 public void checkExpression() {
		 parseTree.add("check_term");
		 checkTerm();
		 if(currentToken().equals("*")||currentToken().equals("/")||currentToken().equals("%")) {
			 getNextToken();
			 parseTree.add("check_term");
			 checkTerm();
		 }
	 }
	 
	 public void checkTerm() {
		 parseTree.add("check_factor");
		 checkFactor();
		 if(currentToken().equals("+")||currentToken().equals("-")) {
			 getNextToken();
			 parseTree.add("check_factor");
			 checkFactor();
		 }
	 }
	 
	 public void checkFactor() {
		 if(currentToken().equals("variable")|| currentToken().equals("real_lit") 
					||currentToken().equals("nat_lit")||currentToken().equals("bool_lit")
					|| currentToken().equals("char_lit")||currentToken().equals("string_lit")) {
				getNextToken();
			
			 
		 }else if (currentToken().equals("(")){
			 getNextToken();
			 parseTree.add("check_expression");
			 checkExpression();
			 if(currentToken().equals(")")) {
				 getNextToken();
			 } else syntaxError(currentToken(),"line 194");
		 } else syntaxError(currentToken(),"line 195");
	 }
	 
	 public  void checkBool() {
		 parseTree.add("check_or");
		 checkOr();
		 if (currentToken().equals("&&")) {
			 getNextToken();
			 parseTree.add("check_or");
			 checkOr();
		 }
	 }
	
	 public  void checkOr() {
		 parseTree.add("check_equal");
		 checkEqual();
		if(currentToken().equals("||")) {
			getNextToken();
			parseTree.add("check_equal");
			checkEqual();
		}
	 }
	 
	 public  void checkEqual() {
		 parseTree.add("check_bool_value");
		 checkBoolValue();
		 
		 if(currentToken().equals("==")|| currentToken().equals("!==")) {
			 getNextToken();
			 parseTree.add("check_bool_value");
			 checkBoolValue();
		 }else if(currentToken().equals("=")) {
			 syntaxError(currentToken(), "line 227");
		 }
	 }
	 
	public  void checkBoolValue() {
		parseTree.add("check_bool_exp");
		checkBoolExp();
		if(currentToken().equals(">=") || currentToken().equals(">") || currentToken().equals("<=")
		|| currentToken().equals("<")) {
			getNextToken();
			parseTree.add("check_bool_exp");
			checkBoolExp();
		} 
			
	}
	
	public  void checkBoolExp() {
		parseTree.add("check_bool_term");
		checkBoolTerm();
		if(currentToken().equals("*")|| currentToken().equals("/")||currentToken().equals("%")) {
			getNextToken();
			parseTree.add("check_bool_term");
			checkBoolTerm();
		} 
	}
	
	public  void checkBoolTerm() {
		parseTree.add("check_bool_factor");
		checkBoolFactor();
		if (currentToken().equals("+")||currentToken().equals("-")) {
			getNextToken();
			parseTree.add("check_bool_factor");
			checkBoolFactor();
		}
	}
	
	public  void checkBoolFactor() {
		if(currentToken().equals("variable")|| currentToken().equals("real_lit") 
				||currentToken().equals("nat_lit")||currentToken().equals("bool_lit")
				|| currentToken().equals("char_lit")||currentToken().equals("string_lit")) {
			getNextToken();
		}
		else if(currentToken().equals("(")) {
			parseTree.add("check_bool_exp");	
			checkBoolExp();
				if(currentToken().equals(")")) {
					getNextToken();
				}else syntaxError(currentToken(), "line 274");
			}else syntaxError(currentToken(), "line 275");
		}
	
	public  void checkParameter() {
		String current = currentToken(); 
		if(current.equals("string_dec")||current.equals("nat_dec")||
				current.equals("real_dec")||current.equals("bool_dec")||
				current.equals("char_dec")) {
			getNextToken();
			if(currentToken().equals("variable")) {
				getNextToken();
				
			
		}else syntaxError(currentToken(),"line 282");	
	}

	}
	 public  void syntaxError(String token, String line) {
		 System.out.println("oh noooo syntax error at: " +token + 
				 " {pointer: " + pointer+"}" + "{line: " +line+ "}");
	 }


	 public  ArrayList<String> getTree() {
		 return parseTree;
	 }

}
