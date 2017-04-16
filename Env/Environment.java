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

    // Sets the left child of the lexeme
    public void setHead(Lexeme lexeme) {

    }

    // Returns the right child of the lexeme
    public Lexeme tail(Lexeme lexeme) {

    }

    // Sets the right child of the lexeme
    public void setTail(Lexeme lexeme) {

    }

    // Returns the left child of the right child of the lexeme
    public Lexeme htail(Lexeme lexeme) {

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
        return join(ENV, null, join(VALUES, null, null));
    }

    public Lexeme lookup(VARIABLE, ENV) {

    }

}
