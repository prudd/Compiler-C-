package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

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
    public void genCode(Function func){
        //Get Current Block
        BasicBlock currentBlock = func.getCurrBlock();
        
        //Create 3 Blocks
        BasicBlock thenBlock = new BasicBlock(func);
        BasicBlock elseBlock = new BasicBlock(func);
        BasicBlock postBlock = new BasicBlock(func);
        
        //genCode if-expr
        expression.genCode(func);
        
        //make branch
        Operation branchOp = new Operation(Operation.OperationType.BNE, currentBlock);
        branchOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, expression.getRegNum()));
        branchOp.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 1));
        if (elseStatement != null) {
            branchOp.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, elseBlock.getBlockNum()));
        } else {
            branchOp.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()));
        }
        currentBlock.appendOper(branchOp);
        
        //append then
        func.appendToCurrentBlock(thenBlock);
        
        //set current block to thenBlock
        func.setCurrBlock(thenBlock);
        
        //genCode then
        statement.genCode(func);
        
        //append post
        func.appendToCurrentBlock(postBlock);
        
        if (elseStatement != null) {
            //set current block to elseBlock
            func.setCurrBlock(elseBlock);
            currentBlock = elseBlock;

            //genCode elseStatement
            elseStatement.genCode(func);

            //create jump to post
            Operation jumpOp = new Operation(Operation.OperationType.JMP,
                                        currentBlock);
            jumpOp.setSrcOperand(0, new Operand(Operand.OperandType.BLOCK,
                                        postBlock.getBlockNum()));
            currentBlock.appendOper(jumpOp);

            //Append elseBlock to unconnectedBlock
            func.appendUnconnectedBlock(elseBlock);
        }
        
        //Set current block to postBlock
        func.setCurrBlock(postBlock);
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
