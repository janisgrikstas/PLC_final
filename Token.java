class Token {
  
  String lexeme;

  int tokenCode;

  
  Token(String lexeme, int tokenCode) {
    this.lexeme = lexeme;
    this.tokenCode = tokenCode;
  }

  public int getCode() {
	return this.tokenCode;
  }
  
  public String getLexeme() {
	  return this.lexeme;
  }
public String toString(){
	return this.lexeme;
	
	}
}

// int val = 1 ;