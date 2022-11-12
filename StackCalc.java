import java.util.Stack;

/**
 * Stack-based command-line utility program
 *
 * @author drainey19@georgefox.edu
 * @author blee20@georgefox.edu
 */
public class StackCalc {
    public static void main(String[] args) {
        // TODO implement program
        if (args.length == 0) {
            System.exit(0);
        }
        else {
            if (args[0] != "-p" || args[0] != "--postfix") {
                // Convert args from infix to postfix
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
            }
            // Evaluate postfix(args)
            // Print results
        }
    }
}

