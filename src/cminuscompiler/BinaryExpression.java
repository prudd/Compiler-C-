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
        tempReg = func.getNewRegNum();
        func.getTable().put(tempReg, tempReg);
        leftExpression.genCode(func);
        rightExpression.genCode(func);
        
        Operation.OperationType opType = null;
        if (operator.equals("<")) {
            opType = Operation.OperationType.LT;
        } else if (operator.equals("<=")) {
            opType = Operation.OperationType.LTE;
        } else if (operator.equals(">")) {
            opType = Operation.OperationType.GT;
        } else if (operator.equals(">=")) {
            opType = Operation.OperationType.GTE;
        } else if (operator.equals("==")) {
            opType = Operation.OperationType.EQUAL;
        } else if (operator.equals("!=")) {
            opType = Operation.OperationType.NOT_EQUAL;
        } else if (operator.equals("+")) {
            opType = Operation.OperationType.ADD_I;
        } else if (operator.equals("-")) {
            opType = Operation.OperationType.SUB_I;
        } else if (operator.equals("*")) {
            opType = Operation.OperationType.MUL_I;
        } else if (operator.equals("/")) {
            opType = Operation.OperationType.DIV_I;
        }
        Operation op = new Operation(opType, currentBlock);
        op.setDestOperand(0, new Operand(Operand.OperandType.REGISTER,
                                    tempReg));
        op.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, 
                                    leftExpression.getRegNum()));
        op.setSrcOperand(1, new Operand(Operand.OperandType.REGISTER,
                                    rightExpression.getRegNum()));
        currentBlock.appendOper(op);
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
