package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.CodeItem;
import lowlevel.Data;

/**
 *
 * @author Paul Marshall
 */
public class ExpressionStatement extends Statement {
    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public CodeItem genCode(){
    }
    
    @Override
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        
        if (this.expression != null) {
            this.expression.print();
        }
        
        System.out.print(";");
    }
    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }

            if (this.expression != null) {
                this.expression.printFile(bw);
            }

            bw.append(";");
        } catch (IOException ex) {
            System.err.print("Error in ExpStmt printFile");
        }

    }
    
       @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }
            bw.append("ExpressionStatement\n");
            if (this.expression != null) {
                this.expression.printASTFile(bw, level + 1);
            }

        } catch (IOException ex) {
            System.err.print("Error in ExpStmt prinASTFile");
        }

    }
    
    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }
    
    public ExpressionStatement() {
        this(null);
    }
}
