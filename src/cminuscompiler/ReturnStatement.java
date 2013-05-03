package cminuscompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operand.OperandType;
import lowlevel.Operation;

/**
 *
 * @author Paul Marshall
 */
public class ReturnStatement extends Statement {

    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public void genCode(BasicBlock currentBlock, Function func){
        if(expression != null){
            expression.genCode(currentBlock);
        }
        int regNum = expression.getRegNum();
        Operation assignOp = new Operation(Operation.OperationType.ASSIGN, currentBlock);
        assignOp.setSrcOperand(0, new Operand(OperandType.REGISTER, regNum));
        assignOp.setDestOperand(0, new Operand(OperandType.MACRO, "RetReg"));
        currentBlock.appendOper(assignOp);
        int returnBlockNum = func.getLastBlock().getBlockNum();
        Operation jumpOp = new Operation(Operation.OperationType.JMP, currentBlock);
        assignOp.setSrcOperand(0, new Operand(OperandType.BLOCK, returnBlockNum));
        
    }
    
    @Override
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }

        System.out.print("return ");
        if (this.expression != null) {
            this.expression.print();
        }
        System.out.println(";");
    }

    @Override
    public void printFile(BufferedWriter bw, int level) {
        try {

            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }

            bw.append("return ");
            if (this.expression != null) {
                this.expression.printFile(bw);
            }
            bw.append(";");
        } catch (IOException ex) {
            System.err.print("Error in ReturnStmt printFile");
        }
    }

    @Override
    public void printASTFile(BufferedWriter bw, int level) {

        try {

            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }

            bw.append("ReturnStatement\n");
            if (this.expression != null) {
                this.expression.printASTFile(bw, level + 1);
            }

        } catch (IOException ex) {
            System.err.print("Error in ReturnStmt printFile");
        }
    }

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public ReturnStatement() {
        this(null);
    }
}
