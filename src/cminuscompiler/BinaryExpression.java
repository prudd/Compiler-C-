package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class BinaryExpression extends Expression {

    private Expression leftExpression;
    private Expression rightExpression;
    private String operator;

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression(Expression leftExpression) {
        this.leftExpression = leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression(Expression rightExpression) {
        this.rightExpression = rightExpression;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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
        if (this.leftExpression != null) {
            this.leftExpression.print();
        }
        System.out.print(this.operator);
        if (this.rightExpression != null) {
            this.rightExpression.print();
        }
    }

    @Override
    public void printFile(BufferedWriter bw) {
        try {
            if (this.leftExpression != null) {
                this.leftExpression.printFile(bw);
            }
            bw.append(this.operator);
            if (this.rightExpression != null) {
                this.rightExpression.printFile(bw);
            }
        } catch (IOException ex) {
            System.err.print("Error in BinExp printFile");
        }
    }

    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "BinaryExpression\n");
            if (this.leftExpression != null) {
                this.leftExpression.printASTFile(bw, level + 1);
            }
            bw.append(inset + "\t" + this.operator + "\n");
            if (this.rightExpression != null) {
                this.rightExpression.printASTFile(bw, level + 1);
            }
        } catch (IOException ex) {
            System.err.print("Error in BinExp printFile");
        }
    }

    public BinaryExpression(Expression leftExpression,
            Expression rightExpression, String operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    public BinaryExpression() {
        this(null, null, null);
    }
}
