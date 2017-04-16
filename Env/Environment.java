public class Environment {

    // Creates a new lexeme with the type, left child, and right child spec.
    public Lexeme join(Type type, Lexeme left, Lexeme right) {
        Lexeme joinedLex = new Lexeme(type);
        joinedLex.leftChild = left;
        joinedLex.rightChild = right;
        return joinedLex;
    }

    // Returns the left child of the lexeme
    public Lexeme head(Lexeme lexeme) {
        return lexeme.leftChild;
    }

    // Returns the right child of the lexeme
    public Lexeme tail(Lexeme lexeme) {
        return lexeme.rightChild;
    }

    // Returns the left child of the right child of the lexeme
    public Lexeme htail(Lexeme lexeme) {
        return lexeme.rightChild.leftChild;
    }

    // Sets the left child of the lexeme
    public void setHead(Lexeme lexeme, Lexeme leftChild) {
        lexeme.leftChild = leftChild;
    }

    // Sets the right child of the lexeme
    public void setTail(Lexeme lexeme, Lexeme rightChild) {
        lexeme.rightChild = rightChild;
    }

    /*
     * Creates a new lexeme with type ENV, left child being the list of
     * variables (currently null since no variables exist), and the right
     * child being the values
     *
     * The values will be stored in the left child of the VALUES lexeme
     *
     * The right child will be for the environment in the nearest outer scope
     */
    public Lexeme create() {
        return join(Type.ENV, null, join(Type.VALUES, null, null));
    }

    public boolean sameVariable(Lexeme lexeme1, Lexeme lexeme2) {
        if(lexeme1.ch.equals(lexeme2.ch)) {
            return true;
        }
        return false;
    }

    public Lexeme lookup(Lexeme variable, Lexeme env) {
        Lexeme vars;
        Lexeme vals;

        // As long as another environment exists...
        while(Type.ENV != null) {
            // Variables are stored as the left child of the lexeme
            vars = head(env);
            // Values are stored as the left child of the VALUES lexeme
            vals = htail(env);

            // As long as more variables exist in the current scope...
            while(vars != null) {
                // Check if we find the variable we are looking for
                if(sameVariable(variable,head(vars))) {
                    // If so, return its value
                    return head(vals);
                }
                // Otherwise, move to the next variable
                vars = tail(vars);
                // Move to the value corresponding to the next variable
                vals = tail(vals);
            }
            // Move to the next environment
            env = tail(tail(env));
        }
        // If no environment contains the variable, it is undefined
        System.out.println("Variable " + variable.ch + " is undefined.");

        return null;
    }

    // The new variable and its value are put on the front of the parallel lists
    public Lexeme insert(Lexeme variable, Lexeme value, Lexeme env) {
        setHead(env, join(Type.JOIN, variable, head(env)));
        setHead(tail(env), join(Type.JOIN, value, htail(env)));
        return value;
    }

    /*
     * A new environment is created, populated with the local parameters
     * and values, and finally pointed to the defining environment
     */
    public Lexeme extend(Lexeme variables, Lexeme values, Lexeme env) {
        return join(Type.ENV, variables, join(Type.VALUES, values, env));
    }

}
