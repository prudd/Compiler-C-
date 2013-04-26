package cminuscompiler;

import java.io.IOException;
import java.io.Writer;

/**
 * Token object.
 * 
 * @author Paul Marshall
 * @version 1.0
 * File: Token.java
 * Created: Feb 3, 2012
 * Summery of Modifications:
 *      - 02/12/2013: Added error type enum and appropriate variables/methods
 *          tracking the error type.
 *      - 02/12/2013: Added print and toString methods.
 *
 * Description:
 *  Used to provide a token object.
 */
public class Token {
    private final int LENGTH_OF_TAB = 16;
    
    public enum TokenType {
        EOF_TOKEN,
        ERROR_TOKEN,
        ELSE_TOKEN,
        IF_TOKEN,
        INT_TOKEN,
        RETURN_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        ADD_TOKEN,
        SUBTRACT_TOKEN,
        MULTIPLY_TOKEN,
        DIVIDE_TOKEN,
        LE_TOKEN,
        LEQ_TOKEN,
        GE_TOKEN,
        GEQ_TOKEN,
        EQUAL_TOKEN,
        NOT_EQUAL_TOKEN,
        ASSIGN_TOKEN,
        END_STATEMENT_TOKEN,
        SEQUENCING_TOKEN,
        LPAREN_TOKEN,
        RPAREN_TOKEN,
        LBRACKET_TOKEN,
        RBRACKET_TOKEN,
        LBRACE_TOKEN,
        RBRACE_TOKEN,
        ID_TOKEN,
        NUM_TOKEN
    }
    
    public enum ErrorTokenType {
        INVALID_IDENTIFIER,
        INVALID_INTEGER,
        INVALID_COMMENT_CLOSING,
        UNKNOWN_SYMBOL,
        UNSPECIFIED_ERROR
    }

    private TokenType type;
    private ErrorTokenType error;
    private Object data;

    /**
     * Returns the type of this token.
     * 
     * @return Token type.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Sets the token type.
     * 
     * @param type 
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * If this is an error token, returns the type of error
     * 
     * @return <code>ErrorTokenType</code> if <code>TokenType</code> is
     *      <code>ERROR_TOKEN</code>, else <code>null</code>.
     */
    public ErrorTokenType getError() {
        if (this.type == TokenType.ERROR_TOKEN) {
            return error;
        } else {
            return null;
        }
    }

    /**
     * Sets the error type.
     * 
     * @param error
     */
    public void setError(ErrorTokenType error) {
        this.error = error;
    }

    /**
     * Gets the data held by this token
     * 
     * @return Token data.
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets the token data.
     * 
     * @param data 
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Presents the object data as a string.
     * 
     * @return String containing Token information (Token type, data)
     */
    @Override
    public String toString() {
        if (this.type != TokenType.ERROR_TOKEN) {
            return this.type + "; " + this.data;
        } else {
            return this.type + "; " + this.error;
        }
    }
    
    /**
     * Prints the token values to screen
     */
    public void print() {
        if (this.type != TokenType.ERROR_TOKEN) {
            System.out.print(this.type + "; " + this.data);
        } else {
            System.out.print(this.type + "; " + this.error);
        }
    }
    
    /**
     * Prints the token values to screen and advances to the next line
     */
    public void println() {
        if (this.type != TokenType.ERROR_TOKEN) {
            if (this.type.toString().length() < LENGTH_OF_TAB) {
                System.out.println(this.type + "\t\t" + this.data);
            } else {
                System.out.println(this.type + "\t" + this.data);
            }
        } else {
            System.out.println(this.type + "\t\t" + this.error);
        }
    }
    
    /**
     * Prints the token to the provided Writer object
     * 
     * @param out Writer to write to.
     * @throws IOException 
     */
    public void print(Writer out) throws IOException {
        if (this.type != TokenType.ERROR_TOKEN) {
            out.write(this.type + "; " + this.data);
        } else {
            out.write(this.type + "; " + this.error);
        }
    }
    
    /**
     * Prints the token to the provided Writer object with end line characters
     * 
     * @param out Writer to write to
     * @throws IOException 
     */
    public void println(Writer out) throws IOException {
        if (this.type != TokenType.ERROR_TOKEN) {
            if (this.type.toString().length() < LENGTH_OF_TAB) {
                out.write(this.type + "\t\t" + this.data + "\r\n");
            } else {
                out.write(this.type + "\t" + this.data + "\r\n");
            }
        } else {
            out.write(this.type + "\t\t" + this.error + "\r\n");
        }
    }
}
