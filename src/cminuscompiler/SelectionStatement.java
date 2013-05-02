package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.CodeItem;
import lowlevel.Data;

/**
 *
 * @author Paul Marshall
 */
public class SelectionStatement extends Statement {
    private Expression expression;
    private Statement statement;
    private Statement elseStatement;

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

    public Statement getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(Statement elseStatement) {
        this.elseStatement = elseStatement;
    }
    
    @Override
    public CodeItem genCode(){
    }
    
    @Override
    public void print(int level) {
        String inset = "";
        
        for (int i = 0; i < level; i++) {
            inset += "\t";
        }
        
        System.out.print(inset + "if (");
        this.expression.print();
        System.out.println(")");
        this.statement.print(level + 1);
        
        if (elseStatement != null) {
            System.out.println(inset + "else");
            this.elseStatement.print(level + 1);
        }
    }
    
    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {
            String inset = "";

            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "if (");
            this.expression.printFile(bw);
            bw.append(")\n");
            this.statement.printFile(bw, level + 1);

            if (elseStatement != null) {
                bw.append(inset + "else\n");
                this.elseStatement.printFile(bw, level + 1);
            }
        } catch (IOException ex) {
            System.err.print("Error in SelectStmt printFile");
        }
    }
     
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {

            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "SelectionStatment\n");
            bw.append(inset + "\t" + "Conditional\n");
            this.expression.printASTFile(bw, level + 2);
            bw.append(inset + "\t" + "ifBody\n");
            this.statement.printASTFile(bw, level + 2);

            if (elseStatement != null) {
                bw.append(inset + "\t" + "elseBody\n");
                this.elseStatement.printASTFile(bw, level + 2);
            }
        } catch (IOException ex) {
            System.err.print("Error in SelectStmt printASTFile");
        }
    }
    
    public SelectionStatement(Expression expression, Statement statement,
            Statement elseStatement) {
        this.expression = expression;
        this.statement = statement;
        this.elseStatement = elseStatement;
    }
    
    public SelectionStatement(Expression expression, Statement statement) {
        this(expression, statement, null);
    }
    
    public SelectionStatement() {
        this(null, null, null);
    }
}
