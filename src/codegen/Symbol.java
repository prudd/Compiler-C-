/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package codegen;

/**
 *
 * @author prudd
 */
public class Symbol {

    private Type type;
    private int RegNum;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getRegNum() {
        return RegNum;
    }

    public void setRegNum(int RegNum) {
        this.RegNum = RegNum;
    }

    public Symbol(Type t) {
        type = t;
    }
}
