import java.util.Stack;
import java.lang.StringBuilder;

/**
 * Stack-based command-line utility program
 *
 * Who worked on what:
 * Brody:
 * Conversion from infix to postfix
 *
 * Dylan:
 * Evaluate the expressions
 * JavaDocs
 *
 * @author drainey19@georgefox.edu
 * @author blee20@georgefox.edu
 */
public class StackCalc {
    /**
     *
     * @param ch
     * @return
     */
    private static int getPrecedence(char ch) {
        int returnValue;

        switch (ch) {
            case '*', '/', '%' -> returnValue = 2;
            case '+', '-' -> returnValue = 1;
            default -> returnValue = -1;
        }

        return returnValue;
    }

    /**
     * Converts expression from infix to postfix.
     *
     * @param expression expression to convert
     * @return expression in postfix notation
     */
    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> cStack = new Stack<>();

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            }
            else if (c == '(') {
                cStack.push(c);
            }
            else if (c == ')') {
                while (!cStack.isEmpty() && cStack.peek() != '(') {
                    result.append(cStack.peek());
                    cStack.pop();
                }
                cStack.pop();
            }
            else {
                while (!cStack.isEmpty() && getPrecedence(c) <= getPrecedence(cStack.peek())) {
                    result.append(cStack.peek());
                    cStack.pop();
                }
                cStack.push(c);
            }
        }

        while (!cStack.isEmpty()) {
            if (cStack.peek() == '(') {
                // Status 2: Invalid expression provided
                System.exit(2);
            }
            else {
                result.append(cStack.peek());
                cStack.pop();
            }
        }

        return result.toString();
    }

    /**
     * Checks if expression is valid.
     *
     * @param expression expression to be evaluated
     * @return solved postfix
     */
    private static int evaluateExpression(String expression) {
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(Character.isDigit(c)) {
                stack.push(c - '0');
            }
            else {
                int val1 = stack.pop();
                int val2 = stack.pop();

                // check if decided by 0
                if (val1 == 0 && c == '/') {
                    System.out.println("NaN");
                    System.exit(2);
                }

                switch (c) {
                    case '+' -> stack.push(val2 + val1);
                    case '-' -> stack.push(val2 - val1);
                    case '/' -> stack.push(val2 / val1);
                    case '*' -> stack.push(val2 * val1);
                    case '%' -> stack.push(val2 % val1);
                    default -> System.exit(2);
                }
            }
        }
        return stack.pop();
    }

    /**
     * Main method that takes arguments and solves them.
     *
     * @param args arguments given
     */
    public static void main(String[] args) {
        StringBuilder argString = new StringBuilder();
        String expression;

        if (args.length == 0) {
            // Status 1: No args provided
            System.exit(1);
        }
        else {
            if (!args[0].equals("-p") && !args[0].equals("--postfix")) {
                // Convert args from infix to postfix
                for (String arg : args) {
                    argString.append(arg);
                }
                expression = infixToPostfix(argString.toString());
            }
            else {
                // check if expression is valid
                for (int i = 1; i < args.length; i++) {
                    argString.append(args[i]);
                }
                expression = argString.toString();
            }
            System.out.println(evaluateExpression(expression));
        }
    }
}
