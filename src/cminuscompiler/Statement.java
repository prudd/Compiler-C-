package cminuscompiler;

import java.io.BufferedWriter;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;

/**
 *
 * @author Paul Marshall
 */
abstract public class Statement {
    abstract public CodeItem genCode(BasicBlock currentBlock);
    abstract public void print(int level);
    abstract public void printFile(BufferedWriter bw, int level);
    abstract public void printASTFile(BufferedWriter bw, int level);
}
