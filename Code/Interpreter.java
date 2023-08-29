import java.util.HashMap;

public class Interpreter {
	
	private HashMap<String, Boolean> map;
	
	public boolean process(Parser.ParseResult parse, boolean[] values) {
		if(map == null)
			map = new HashMap<>();
		else map.clear();
		
		for(int i = 0; i < parse.variables.size(); i++) {
			Token token = parse.variables.get(i);
			map.put(token.value, values[i]);
		}
		
		return interpret(parse.node);
	}

public boolean interpret(Object node) {
    if (node instanceof Parser.NotNode) return !interpret(((Parser.NotNode) node).node);
    if (node instanceof Parser.AndNode) return interpret(((Parser.AndNode) node).a) && interpret(((Parser.AndNode) node).b);
    if (node instanceof Parser.OrNode) return interpret(((Parser.OrNode) node).a) || interpret(((Parser.OrNode) node).b);
    if (node instanceof Parser.XorNode) return interpret(((Parser.XorNode) node).a) != interpret(((Parser.XorNode) node).b);
    if (node instanceof Parser.VarNode) return map.get(((Parser.VarNode) node).token.value);

    if (node instanceof Parser.ImplicationNode) {
        boolean a = interpret(((Parser.ImplicationNode) node).a);
        if (!a) return true;
        boolean b = interpret(((Parser.ImplicationNode) node).b);
        return b;
    }
    if (node instanceof Parser.BiconditionalNode) {
        boolean a = interpret(((Parser.BiconditionalNode) node).a);
        boolean b = interpret(((Parser.BiconditionalNode) node).b);

        return a == b;
    }
    if (node instanceof Parser.ParContentNode) {
        boolean a = interpret(((Parser.ParContentNode) node).node);
        return a;
    }

    return false;
}

}
