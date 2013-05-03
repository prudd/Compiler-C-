package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

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
    public void genCode(Function func) {
        BasicBlock currentBlock = func.getCurrBlock();
        HashMap symbolTable = CMinusCompiler.globalHash;
        tempReg = symbolTable.size();
        symbolTable.put(tempReg, tempReg);
        leftExpression.genCode(func);
        rightExpression.genCode(func);
        if (operator.equals("<")) {
            Operation ltOp = new Operation(Operation.OperationType.LT, currentBlock);
            ltOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            ltOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            ltOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(ltOp);
        } else if (operator.equals("<=")) {
            Operation lteOp = new Operation(Operation.OperationType.LTE, currentBlock);
            lteOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            lteOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            lteOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(lteOp);
        } else if (operator.equals(">")) {
            Operation gtOp = new Operation(Operation.OperationType.GT, currentBlock);
            gtOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            gtOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            gtOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(gtOp);
        } else if (operator.equals(">=")) {
            Operation gteOp = new Operation(Operation.OperationType.GTE, currentBlock);
            gteOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            gteOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            gteOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(gteOp);
        } else if (operator.equals("==")) {
            Operation equalOp = new Operation(Operation.OperationType.EQUAL, currentBlock);
            equalOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            equalOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            equalOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(equalOp);
        } else if (operator.equals("!=")) {
            Operation notEQOp = new Operation(Operation.OperationType.NOT_EQUAL, currentBlock);
            notEQOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            notEQOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            notEQOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(notEQOp);
        } else if (operator.equals("+")) {
            Operation addOp = new Operation(Operation.OperationType.ADD_I, currentBlock);
            addOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            addOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            addOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(addOp);
        } else if (operator.equals("-")) {
            Operation subOp = new Operation(Operation.OperationType.SUB_I, currentBlock);
            subOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            subOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            subOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(subOp);
        } else if (operator.equals("*")) {
            Operation mulOp = new Operation(Operation.OperationType.MUL_I, currentBlock);
            mulOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            mulOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            mulOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(mulOp);
        } else if (operator.equals("/")) {
            Operation divOp = new Operation(Operation.OperationType.DIV_I, currentBlock);
            divOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
            divOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, leftExpression.getRegNum()));
            divOp.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER, rightExpression.getRegNum()));
            currentBlock.appendOper(divOp);
        }
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
