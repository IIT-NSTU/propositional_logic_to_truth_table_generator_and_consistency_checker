import java.util.ArrayList;

public class Lexer {
		
	public static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static ArrayList<Token> process(String str) {
		char[] chars = str.toCharArray(); // string theke character of array te convert korbe 
		ArrayList<Token> tokens = new ArrayList<>(); // tokens name er ekta new object create korchi. karon ata te operation golo rakhbo
		
		for(int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if(c == ' ') continue;
			else if(c == '!' || c == '¬') tokens.add(new Token(TokenType.NOT));
			else if(c == '&' || c == '∧' || c == '^') tokens.add(new Token(TokenType.AND));
			else if(c == '|' || c == '∨' || c == 'v') tokens.add(new Token(TokenType.OR));
			else if(c == '+') tokens.add(new Token(TokenType.XOR));
			else if(c == '(') tokens.add(new Token(TokenType.LPAR));
			else if(c == ')') tokens.add(new Token(TokenType.RPAR));
			else if(c == '→') tokens.add(new Token(TokenType.IMPLICATION));
			else if(c == '↔') tokens.add(new Token(TokenType.BICONDITIONAL));
			else if(c == '-') { // Handling ->
				i++;
				tokens.add(new Token(TokenType.IMPLICATION));
			}
			else if(c == '<') { // Handling <->
				i+=2;
				tokens.add(new Token(TokenType.BICONDITIONAL));
			}
			else if(ALPHABET.contains(c+"")) {
				tokens.add(new Token(c+"", TokenType.VAR)); 
			}
			
		}
		
		tokens.add(new Token(TokenType.END));
		
		return tokens;
	}
	
}