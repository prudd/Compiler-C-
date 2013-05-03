package cminuscompiler;

import compiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import lowlevel.CodeItem;
import lowlevel.Data;

/**
 *
 * @author Paul Marshall
 */
public class VariableDeclaration extends Declaration {
    private String id;
    private int arraySize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
    
    @Override
    public CodeItem genCode( ){
        HashMap symbolTable = CMinusCompiler.globalHash;
        symbolTable.put(id, symbolTable.size());
        return new Data(Data.TYPE_INT, id);
    }
    
    @Override
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        
        if (this.arraySize > -1) {
            System.out.println("int " + this.id + "[" + this.arraySize + "];");
        } else {
            System.out.println("int " + this.id + ";");
        }
    }
    
    @Override
    public void printFile(BufferedWriter bw, int level) {
        try{
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }

            if (this.arraySize > -1) {
                bw.append("int " + this.id + "[" + this.arraySize + "];\n");
            } else {
                bw.append("int " + this.id + ";\n");
            }
        }
        catch(IOException ex){
            System.err.print("Error in VarDecl printFile");
        }
    }
    
    @Override
    public void printASTFile(BufferedWriter bw, int level) {
        try{
            for (int i = 0; i < level; i++) {
                bw.append("\t");
            }
            bw.append("VariableDeclaration(int " + id);
            
            if (this.arraySize > -1) {
                bw.append("[" + this.arraySize + "])\n");
            } else {
                bw.append(")\n");
            }
        }
        catch(IOException ex){
            System.err.print("Error in VarDecl printASTFile");
        }
    }
    
    public VariableDeclaration(String id, int arraySize) {
        this.id = id;
        this.arraySize = arraySize;
    }
    
    public VariableDeclaration(String id) {
        this(id, -1);
    }
    
    public VariableDeclaration() {
        this(null, -1);
    }
}
