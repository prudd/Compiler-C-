package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class NumExpression extends Expression {
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    @Override
    public void genCode(Function func){
        func.getTable().put(tempReg, tempReg);
    }
    
    @Override
    public int getRegNum() {
        return this.tempReg;
    }
    
    @Override
    public void print() {
        System.out.print(this.num);
    }
    
    @Override
    public void printFile(BufferedWriter bw) {
        try {
            bw.append(((Integer) this.num).toString());
        } catch (IOException ex) {
            System.err.print("Error in NumExp printFile");
        }
    }
    
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }
            
            bw.append(inset + "NumExpression(" + this.num + ")\n");
        } catch (IOException ex) {
            System.err.print("Error in NumExp printFile");
        }
    }
    
    public NumExpression(int num) {
        this.num = num;
    }
    
    public NumExpression() {
        this(0);
    }
}
