package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

/**
 *
 * @author Paul Marshall
 */
public class Parameter {
    private String id;
    private boolean isArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }
    
    public void genCode(Function func){
        HashMap symbolTable = CMinusCompiler.globalHash;
        symbolTable.put(id, symbolTable.size());
        if(func.getfirstParam() == null){
            func.setFirstParam(new FuncParam(Data.TYPE_INT, id));
        }
        else{
            func.getfirstParam().setNextParam(new FuncParam(Data.TYPE_INT, id));
        }
    }

    public void print() {
        System.out.print("int " + this.id);

        if (this.isArray) {
            System.out.print("[]");
        }
    }

    public void printFile(BufferedWriter bw) {
        try {
            bw.append("int " + this.id);

            if (this.isArray) {
                bw.append("[]");
            }
        } catch (IOException ex) {
            System.err.print("Error in Parameter printFile");
        }
    }

    public void printASTFile(BufferedWriter bw, int level) {
        try {
            String inset = "";
            for (int i = 0; i < level - 1; i++) {
                inset += "\t";
            }

            bw.append(inset + "\t" + "int " + this.id);

            if (this.isArray) {
                bw.append("[]");
            }
            bw.append("\n");
        } catch (IOException ex) {
            System.err.print("Error in Parameter printASTFile");
        }
    }

    public Parameter(String id, boolean isArray) {
        this.id = id;
        this.isArray = isArray;
    }

    public Parameter(String id) {
        this(id, false);
    }

    public Parameter() {
        this(null, false);
    }
}