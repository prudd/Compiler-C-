package cminuscompiler;

import java.io.*;

/**
 * C- Scanner object.  Takes a given file name and produces tokens on request.
 * 
 * @author Paul Marshall
 * @version 1.0
 * File: CMinusScanner.java
 * Created: Feb 3, 2012
 * Summery of Modifications:
 *      - None
 *
 * Description:
 *  Scans a text file and breaks it down into tokens.
 */
public class CMinusScanner implements Scanner {
    private Token token;
    private BufferedReader reader;
    
    /**
     * Sets the source file and gets the first token for return.
     * 
     * @param source Source file name
     * @throws FileNotFoundException 
     */
    public void setSource(String source) throws FileNotFoundException, IOException {
        this.reader = new BufferedReader(new FileReader(source));
        this.token = findToken();
    }

    /**
     * Gets the current token and then parses for the next one.
     * 
     * @return The current token.
     */
    @Override
    public Token getToken() throws IOException {
        Token oldToken = this.token;
        
        if (this.token.getType() != Token.TokenType.EOF_TOKEN) {
            this.token = findToken();
        }
        
        return oldToken;
    }

    /**
     * Returns the current token.
     * 
     * @return The current token.
     */
    @Override
    public Token viewToken() {
        return this.token;
    }
    
    /**
     * Finds the next valid C- token.
     * 
     * @return Token.
     */
    private Token findToken() throws IOException {
        Token newToken = new Token();
        String data = "";
        String state = "start";
        char next;
        
        /*
         * While we are not in a ending state, we will continue parsing.  This
         * is based on a Mealy FSM model more-so than a Moore FSM model.
         */
        while (!"done".equals(state)) {
            // Try is required due to the buffered reader
            this.reader.mark(1);
            int input = this.reader.read();

            if (input == -1) {
                newToken.setType(Token.TokenType.EOF_TOKEN);
                newToken.setData(null);
                state = "done";
            }

            next = (char) input;

            switch (state) {
                /*
                 * Start state
                 */
                case "start":
                    if (Character.isLetter(next)) {
                        data = data + next;
                        state = "ident";
                    } else if (Character.isDigit(next)) {
                        data = data + next;
                        state = "num";
                    } else if (next == '+') {
                        newToken.setType(Token.TokenType.ADD_TOKEN);
                        newToken.setData("+");
                        state = "done";
                    } else if (next == '-') {
                        newToken.setType(Token.TokenType.SUBTRACT_TOKEN);
                        newToken.setData("-");
                        state = "done";
                    } else if (next == '*') {
                        data = data + next;
                        state = "star";
                    } else if (next == '/') {
                        data = data + next;
                        state = "slash";
                    } else if (next == '<') {
                        data = data + next;
                        state = "le";
                    } else if (next == '>') {
                        data = data + next;
                        state = "ge";
                    } else if (next == '=') {
                        data = data + next;
                        state = "eq";
                    } else if (next == '!') {
                        data = data + next;
                        state = "neq";
                    } else if (next == ';') {
                        newToken.setType(Token.TokenType.END_STATEMENT_TOKEN);
                        newToken.setData(";");
                        state = "done";
                    } else if (next == ',') {
                        newToken.setType(Token.TokenType.SEQUENCING_TOKEN);
                        newToken.setData(",");
                        state = "done";
                    } else if (next == '(') {
                        newToken.setType(Token.TokenType.LPAREN_TOKEN);
                        newToken.setData("(");
                        state = "done";
                    } else if (next == ')') {
                        newToken.setType(Token.TokenType.RPAREN_TOKEN);
                        newToken.setData(")");
                        state = "done";
                    } else if (next == '[') {
                        newToken.setType(Token.TokenType.LBRACKET_TOKEN);
                        newToken.setData("[");
                        state = "done";
                    } else if (next == ']') {
                        newToken.setType(Token.TokenType.RBRACKET_TOKEN);
                        newToken.setData("]");
                        state = "done";
                    } else if (next == '{') {
                        newToken.setType(Token.TokenType.LBRACE_TOKEN);
                        newToken.setData("{");
                        state = "done";
                    } else if (next == '}') {
                        newToken.setType(Token.TokenType.RBRACE_TOKEN);
                        newToken.setData("}");
                        state = "done";
                    } else if (!Character.isWhitespace(next)) {
                        data = data + next;
                        newToken.setType(Token.TokenType.ERROR_TOKEN);
                        newToken.setError(Token.ErrorTokenType.UNKNOWN_SYMBOL);
                        newToken.setData(data);
                        state = "done";
                    }

                    break;
                /*
                 * Letter state
                 * 
                 * Consume characters until the next character is not a
                 * letter.  Then check and see if we have a reserved word.
                 * If so, return the proper token, otherwise return an ID
                 * token.
                 */
                case "ident":
                    if (Character.isLetter(next)) {
                        data = data + next;
                    } else if (Character.isDigit(next)) {
                        newToken.setType(Token.TokenType.ERROR_TOKEN);
                        newToken.setError(Token.ErrorTokenType.INVALID_IDENTIFIER);
                        state = "clear";
                    } else {
                        switch (data) {
                            case "else":
                                newToken.setType(Token.TokenType.ELSE_TOKEN);
                                break;
                            case "if":
                                newToken.setType(Token.TokenType.IF_TOKEN);
                                break;
                            case "int":
                                newToken.setType(Token.TokenType.INT_TOKEN);
                                break;
                            case "return":
                                newToken.setType(Token.TokenType.RETURN_TOKEN);
                                break;
                            case "void":
                                newToken.setType(Token.TokenType.VOID_TOKEN);
                                break;
                            case "while":
                                newToken.setType(Token.TokenType.WHILE_TOKEN);
                                break;
                            default:
                                newToken.setType(Token.TokenType.ID_TOKEN);
                                break;
                        }

                        newToken.setData(data);
                        this.reader.reset();
                        state = "done";
                    }
                    break;
                /*
                 * Number state
                 * 
                 * Consume all characters until the next character is not a
                 * number.  Then return a number token.
                 */
                case "num":
                    if (Character.isDigit(next)) {
                        data = data + next;
                    } else if (Character.isLetter(next)) {
                        newToken.setType(Token.TokenType.ERROR_TOKEN);
                        newToken.setError(Token.ErrorTokenType.INVALID_INTEGER);
                        state = "clear";
                    } else {
                        newToken.setType(Token.TokenType.NUM_TOKEN);
                        newToken.setData(new Integer(Integer.parseInt(data)));
                        this.reader.reset();
                        state = "done";
                    }
                    break;
                case "clear":
                    if (Character.isDigit(next) || Character.isLetter(next)) {
                        data = data + next;
                    } else {
                        newToken.setData(data);
                        this.reader.reset();
                        state = "done";
                    }
                    break;
                /*
                 * "*" state
                 * 
                 * Used to check against nested comments: "/*" is valid if
                 * repeated, but we cannot have a closing comment without a
                 * "/*" before the last comment closing.
                 */
                case "star":
                    if (next == '/') {
                        data = data + next;
                        newToken.setType(Token.TokenType.ERROR_TOKEN);
                        newToken.setError(Token.ErrorTokenType.INVALID_COMMENT_CLOSING);
                        newToken.setData(data);
                    } else {
                        newToken.setType(Token.TokenType.MULTIPLY_TOKEN);
                        newToken.setData(data);
                        this.reader.reset();
                    }
                    state = "done";
                    break;
                /*
                 * "/" state
                 */
                case "slash":
                    if (next == '*') {
                        state = "comment";
                    } else {
                        newToken.setType(Token.TokenType.DIVIDE_TOKEN);
                        newToken.setData(data);
                        this.reader.reset();
                        state = "done";
                    }
                    break;
                /*
                 * "<" state
                 */
                case "le":
                    if (next == '=') {
                        data = data + next;
                        newToken.setType(Token.TokenType.LEQ_TOKEN);
                        newToken.setData(data);
                    } else {
                        newToken.setType(Token.TokenType.LE_TOKEN);
                        newToken.setData(data);
                        this.reader.reset();
                    }
                    state = "done";
                    break;
                /*
                 * ">" state
                 */
                case "ge":
                    if (next == '=') {
                        data = data + next;
                        newToken.setType(Token.TokenType.GEQ_TOKEN);
                        newToken.setData(data);
                    } else {
                        newToken.setType(Token.TokenType.GE_TOKEN);
                        newToken.setData(data);
                        this.reader.reset();
                    }
                    state = "done";
                    break;
                /*
                 * "=" state
                 */
                case "eq":
                    if (next == '=') {
                        data = data + next;
                        newToken.setType(Token.TokenType.EQUAL_TOKEN);
                        newToken.setData(data);
                    } else {
                        newToken.setType(Token.TokenType.ASSIGN_TOKEN);
                        newToken.setData(data);
                        this.reader.reset();
                    }
                    state = "done";
                    break;
                /*
                 * "!" state
                 */
                case "neq":
                    if (next == '=') {
                        data = data + next;
                        newToken.setType(Token.TokenType.NOT_EQUAL_TOKEN);
                        newToken.setData(data);
                    } else {
                        newToken.setType(Token.TokenType.ERROR_TOKEN);
                        newToken.setError(Token.ErrorTokenType.UNKNOWN_SYMBOL);
                        newToken.setData(data);
                        this.reader.reset();
                    }
                    state = "done";
                    break;
                /*
                 * Comment state
                 */
                case "comment":
                    if (next == '*') {
                        state = "end comment";
                    }
                    break;
                /*
                 * End comment state
                 * 
                 * Checks for the end of a comment block.
                 */
                case "end comment":
                    if (next == '*') {
                        // Stay here.
                    } else if (next != '/') {
                        state = "comment";  // Wasn't actually the end
                    } else {
                        data = "";  // Clear "data"
                        state = "start";
                    }
                    break;
            }
        }
        
        return newToken;
    }
    
    public CMinusScanner(String source) throws FileNotFoundException, IOException {
        this.reader = new BufferedReader(new FileReader(source));
        this.token = findToken();
    }
    
    /**
     * Test code.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String[] filename = {"ComprehensiveTest.c", "SelectionSort.c", "CrappyCode.c"};
        PrintWriter outFile = new PrintWriter(new FileWriter("output.txt"));
        CMinusScanner programScan;
        Token printToken;
        
        for (int i = 0; i < filename.length; i++) {
            outFile.println(filename[i]);
            outFile.println();
            
            programScan = new CMinusScanner(filename[i]);
            printToken = programScan.getToken();

            while (printToken.getType() != Token.TokenType.EOF_TOKEN) {
                // Old way:
                // outFile.println(printToken.toString());
                // New way:
                printToken.println();
                printToken.println(outFile);
                printToken = programScan.getToken();
            }
            
            outFile.println();
        }
        
        outFile.close();
    }
}
