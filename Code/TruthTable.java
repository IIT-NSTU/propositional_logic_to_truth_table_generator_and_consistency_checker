import java.util.ArrayList;
import java.util.Scanner;

public class TruthTable {
    public static void process(String sequence, Parser parser, Interpreter interpreter) {
        ArrayList<Token> tokens = Lexer.process(sequence);

        Parser.ParseResult result = parser.process(tokens);

        Display.header(result.variables);
        Display.display(interpreter, result);
    }

    public static boolean isConsistent(String expression, Parser parser, Interpreter interpreter) {
        ArrayList<Token> tokens = Lexer.process(expression);
        Parser.ParseResult result = parser.process(tokens);

        int numVariables = result.variables.size();
        boolean[] values = new boolean[numVariables];

        return checkConsistency(interpreter, result, values, 0);
    }

    private static boolean checkConsistency(Interpreter interpreter, Parser.ParseResult result, boolean[] values, int index) {
        if (index == values.length) {
            return interpreter.process(result, values);
        }

        values[index] = true;
        boolean consistentWithTrue = checkConsistency(interpreter, result, values, index + 1);

        values[index] = false;
        boolean consistentWithFalse = checkConsistency(interpreter, result, values, index + 1);

        return consistentWithTrue || consistentWithFalse;
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        Interpreter interpreter = new Interpreter();

        if (args.length != 0) {
            String sequence = String.join(" ", args);
            process(sequence, parser, interpreter);
            boolean isConsistent = isConsistent(sequence, parser, interpreter);
            if (isConsistent) {
                System.out.println("The expression is consistent.");
            } else {
                System.out.println("The expression is inconsistent.");
            }
        } else {
            System.out.print("Enter an Expression: ");
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                String sequence = scanner.nextLine();
                if (sequence.equals("stop")) {
                    running = false;
                    continue;
                }

                // Display the truth table
                process(sequence, parser, interpreter);

                // Check consistency of the expression
                boolean isConsistent = isConsistent(sequence, parser, interpreter);
                if (isConsistent) {
                    System.out.println("The expression is consistent.");
                } else {
                    System.out.println("The expression is inconsistent.");
                }
            }

            scanner.close();
        }
    }
}
