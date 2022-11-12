import java.util.Stack;
import java.lang.StringBuilder;

/**
 * Stack-based command-line utility program
 *
 * @author drainey19@georgefox.edu
 * @author blee20@georgefox.edu
 */
public class StackCalc {
    private static int getPrecedence(char ch) {
        int returnValue;

        switch (ch) {
            case '^' -> returnValue = 3;
            case '*', '/', '%' -> returnValue = 2;
            case '+', '-' -> returnValue = 1;
            default -> returnValue = -1;
        }

        return returnValue;
    }


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
                // Status 2 means that an invalid expression was provided
                System.exit(2);
            }
            else {
                result.append(cStack.peek());
                cStack.pop();
            }
        }

        return result.toString();
    }


    private static int evaluateExpression(String expression) {
        return -1;
    }


    public static void main(String[] args) {
        StringBuilder argString = new StringBuilder();

        if (args.length == 0) {
            // Status 1 means there were no args provided
            System.exit(1);
        }
        else {
            if (!args[0].equals("-p") && !args[0].equals("--postfix")) {
                // Convert args from infix to postfix
                for (int i = 0; i < args.length - 1; i++) {
                    argString.append(args[i + 1]);
                }

                String expression = infixToPostfix(argString.toString());
                System.out.println(expression);
                // System.out.println(evaluateExpression(expression));
            }
        }
    }
}
/*
                for each token:
                    if token is operand
                        append token to output queue
                    else: # token is an operator
                        if operator is ")":
                            pop operator stack → <op>
                            while <op> is not "(":
                                append <op> to output queue
                                pop operator stack → <op>
                        else:
                            # pop operators until find operator w/ lower priority ->(pemdas)
                            # push <op> to operator stack
                    while operator stack not empty:
                        pop operator, append it to output queue
                 */