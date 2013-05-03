package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import lowlevel.Attribute;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Paul Marshall
 */
public class CallExpression extends Expression {

    private String id;
    private LinkedList<Expression> args = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Expression> getArgs() {
        return args;
    }

    public void setArgs(LinkedList<Expression> args) {
        this.args = args;
    }

    @Override
    public void genCode(Function func) {
        // Start a new basic block to try and help with register allocation
        // later.
        BasicBlock currentBlock = func.getCurrBlock();
        BasicBlock newBlock = new BasicBlock(func);

        currentBlock.setNextBlock(newBlock);
        newBlock.setPrevBlock(currentBlock);

        func.setCurrBlock(newBlock);
        currentBlock = newBlock;

        // Call genCode on args in reverse order:
        // Add Operation to move each param to register:
        Iterator<Expression> intArgs = args.descendingIterator();
        while (intArgs.hasNext()) {
            Expression exp = intArgs.next();
            exp.genCode(func);
            Operation passOp = new Operation(Operation.OperationType.PASS, currentBlock);
            passOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, exp.getRegNum()));
            currentBlock.appendOper(passOp);
        }

        // Add call operation:
        Operation callOp = new Operation(Operation.OperationType.CALL, currentBlock);
        callOp.setSrcOperand(0, new Operand(Operand.OperandType.REGISTER, id));
        callOp.addAttribute(new Attribute("numParams", Integer.toString(args.size())));
        currentBlock.appendOper(callOp);
        
        //Assign retReg to a new register
        HashMap symbolTable = CMinusCompiler.globalHash;
        tempReg = symbolTable.size();
        symbolTable.put(tempReg, tempReg);
        Operation assignOp = new Operation(Operation.OperationType.ASSIGN, currentBlock);
        assignOp.setSrcOperand(0, new Operand(Operand.OperandType.MACRO, "retReg"));
        assignOp.setDestOperand(0, new Operand(Operand.OperandType.REGISTER, tempReg));
        currentBlock.appendOper(assignOp);
    }

    @Override
    public int getRegNum() {
        return this.tempReg;
    }

    @Override
    public void print() {
        Iterator<Expression> expression = this.args.iterator();
        System.out.print(id + "(");

        while (expression.hasNext()) {
            expression.next().print();

            if (expression.hasNext()) {
                System.out.print(", ");
            }
        }

        System.out.print(")");
    }

    @Override
    public void printFile(BufferedWriter bw) {
        try {
            Iterator<Expression> expression = this.args.iterator();
            bw.append(id + "(");

            while (expression.hasNext()) {
                expression.next().printFile(bw);

                if (expression.hasNext()) {
                    bw.append(", ");
                }
            }

            bw.append(")");
        } catch (IOException ex) {
            System.err.print("Error in CallExp printFile");
        }
    }

    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level; i++) {
                inset += "\t";
            }

            bw.append(inset + "CallExpression(" + id + ")\n");
            bw.append(inset + "\t" + "Arguments\n");

            Iterator<Expression> expression = this.args.iterator();
            while (expression.hasNext()) {
                expression.next().printASTFile(bw, level);
            }
        } catch (IOException ex) {
            System.err.print("Error in CallExp printFile");
        }
    }

    public CallExpression(String id, LinkedList<Expression> args) {
        this.id = id;
        this.args = args;
    }

    public CallExpression(String id) {
        this.id = id;
    }

    public CallExpression() {
        this(null);
    }
}
