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
    public void genCode(Function func) {
        //get current block
        BasicBlock currentBlock = func.getCurrBlock();

        //create 2 blocks
        BasicBlock whileBlock = new BasicBlock(func);
        BasicBlock postBlock = new BasicBlock(func);

        //genCode expr
        expression.genCode(func);

        //branch to post
        Operation branchOp = new Operation(Operation.OperationType.BNE, currentBlock);
        branchOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, expression.getRegNum()));
        branchOp.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 1));
        branchOp.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()));

        //append whileBlock to current
        currentBlock.setNextBlock(whileBlock);
        whileBlock.setPrevBlock(currentBlock);

        //set current block to whileBlock
        func.setCurrBlock(whileBlock);
        currentBlock = whileBlock;

        //genCode statement
        statement.genCode(func);

        //reverse polarity branch to whileBlock
        Operation branchOp2 = new Operation(Operation.OperationType.BEQ, currentBlock);
        branchOp2.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, expression.getRegNum()));
        branchOp2.setSrcOperand(1, new Operand(Operand.OperandType.INTEGER, 1));
        branchOp2.setSrcOperand(2, new Operand(Operand.OperandType.BLOCK, postBlock.getBlockNum()));
        //append postBlock
        currentBlock.setNextBlock(postBlock);
        whileBlock.setPrevBlock(currentBlock);

        //set current block to postBlock
        func.setCurrBlock(postBlock);
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
