package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Paul Marshall
 */
public class AssignmentExpression extends Expression {
    private VarExpression variable;
    private Expression expression;

    public VarExpression getVariable() {
        return variable;
    }

    public void setVariable(VarExpression variable) {
        this.variable = variable;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void print() {
        this.variable.print();
        System.out.print(" = ");
        this.expression.print();
    }

    @Override
    public void printFile(BufferedWriter bw) {
        try {
            this.variable.printFile(bw);
            bw.append(" = ");
            this.expression.printFile(bw);
        } catch (IOException ex) {
            System.err.print("Error in AssignExp printFile");
        }
    }
    
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";

            for (int i = 0; i < level; i++) {
                inset += "\t";
            }
            
            bw.append(inset + "AssignmentExpression\n");
            this.variable.printASTFile(bw, level + 1);
            this.expression.printASTFile(bw, level + 1);
        } catch (IOException ex) {
            System.err.print("Error in AssignExp printFile");
        }
    }
    
    public AssignmentExpression(VarExpression variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }
    
    public AssignmentExpression() {
        this(null, null);
    }
}
