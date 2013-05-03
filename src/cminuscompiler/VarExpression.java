package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
class VarExpression extends Expression {
    private String id;
    private Expression expression;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void genCode(Function func){
        
    }
    
    @Override
    public int getRegNum() {
        return this.tempReg;
    }
    
    @Override
    public void print() {
        System.out.print(this.id);
        
        if (this.expression != null) {
            System.out.print("[");
            this.expression.print();
            System.out.print("]");
        }
    }
    
    @Override
    public void printFile(BufferedWriter bw) {
        try {
            bw.append(this.id);

            if (this.expression != null) {
                bw.append("[");
                this.expression.printFile(bw);
                bw.append("]");
            }
        } catch (IOException ex) {
            System.err.print("Error in VarExp printFile");
        }
    }
    
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {         
            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }
            
            bw.append(inset + "VarExpression\n");
            bw.append(inset + "\t" + "Variable(" + this.id + ")\n");

            if (this.expression != null) {
                this.expression.printASTFile(bw, level + 2);             
            }
        } catch (IOException ex) {
            System.err.print("Error in VarExp printASTFile");
        }
    }
    
    public VarExpression(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }
    
    public VarExpression(String id) {
        this(id, null);
    }
    
    public VarExpression() {
        this(null, null);
    }
}
