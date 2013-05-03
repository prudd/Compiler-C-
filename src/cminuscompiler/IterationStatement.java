package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class IterationStatement extends Statement {
    private Expression expression;
    private Statement statement;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    @Override
    public void genCode(Function func){
        //iterateBlock
        //postBlock
        //genCode expr
        //branch to post
        //append whileBlock to current
        //set current block to whileBlock
        //genCode statement
        //reverse polarity branch to iterateBlock
        //append postBlock
        //set current block to postBlock
    }

    @Override
    public void print(int level) {
        String inset = "";
        
        for (int i = 0; i < level; i++) {
            inset += "\t";
        }
        
        System.out.print(inset + "while (");
        this.expression.print();
        System.out.println(")");
        this.statement.print(level + 1);
    }
    
    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {
            String inset = "";

            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "while (");
            this.expression.printFile(bw);
            bw.append(")\n");
            this.statement.printFile(bw, level + 1);
        } catch (IOException ex) {
            System.err.print("Error in IternationStmt printFile");
        }
    }
    
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";

            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "IterationStatment\n");
            bw.append(inset + "\t" + "Conditional\n");
            this.expression.printASTFile(bw, level + 2);
            bw.append(inset + "\t" + "Body\n");
            this.statement.printASTFile(bw, level + 2);
        } catch (IOException ex) {
            System.err.print("Error in IternationStmt printASTFile");
        }
    }
    
    public IterationStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }
    
    public IterationStatement() {
        this(null, null);
    }
}
