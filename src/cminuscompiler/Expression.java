package cminuscompiler;

import java.io.BufferedWriter;
import lowlevel.BasicBlock;

/**
 *
 * @author Paul Marshall
 */
abstract public class Expression {
    protected int tempReg;
    abstract public void genCode(BasicBlock compoundBlock);
    abstract public int getRegNum();
    abstract public void print();
    abstract public void printFile(BufferedWriter bw);
    abstract public void printASTFile(BufferedWriter bw, int level);
}
