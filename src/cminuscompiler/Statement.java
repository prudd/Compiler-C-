package cminuscompiler;

import java.io.BufferedWriter;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
abstract public class Statement {
    abstract public void genCode(BasicBlock currentBlock, Function func);
    abstract public void print(int level);
    abstract public void printFile(BufferedWriter bw, int level);
    abstract public void printASTFile(BufferedWriter bw, int level);
}
