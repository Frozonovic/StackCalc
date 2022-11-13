import java.util.Stack;
import java.lang.StringBuilder;


/**
 * Stack-based command-line utility program
 * <p>
 * Who worked on what:
 * <p>
 * Brody:
 * Conversion from infix to postfix
 * JavaDocs
 * <p>
 * Dylan:
 * Evaluate the expressions
 * JavaDocs
 *
 * @author drainey19@georgefox.edu
 * @author blee20@georgefox.edu
 */
public class StackCalc {
    /**
     * Fetches the precedence level for the given operator
     *
     * @param ch Selected order of operation
     * @return Number based on operator priority (higher numbers are higher priority)
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

            if (Character.isLetterOrDigit(c) || c == ' ') {
                result.append(c);
            }
            else if (c == '(') {
                cStack.push(c);
            }
            else if (c == ')') {
                result.append(' ');
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
            result.append(cStack.peek());
            cStack.pop();
        }

        return result.toString();
    }


    /**
     * Checks if expression is valid and solves the equation.
     * If the expression results in division by 0, prints "NaN" and exit with code 2
     *
     * @param expression expression to be evaluated
     * @return solved postfix
     */
    private static int evaluateExpression(String expression) {
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(c == ' ') {
                continue;
            }
            else if(Character.isDigit(c)) {
                int num = 0;
                while(Character.isDigit(c)) {
                    num = num * 10 + (c - '0');
                    i++;
                    c = expression.charAt(i);
                    stack.push(num);
                }

            }
            else {
                int val1 = stack.pop();
                int val2 = stack.pop();

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
                    argString.append(' ');
                }
                expression = infixToPostfix(argString.toString());
            }
            else {
                // check if expression is valid
                for (int i = 1; i < args.length; i++) {
                    argString.append(args[i]);
                    argString.append(' ');
                }
                expression = argString.toString();
            }
            System.out.println(evaluateExpression(expression));
        }
    }
}
