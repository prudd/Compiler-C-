package cminuscompiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import lowlevel.CodeItem;
import lowlevel.Data;
import codegen.CMinusCodeGen;
import codegen.CodeGen;

/**
 *
 * @author Paul Marshall
 */
public class Program {
    private LinkedList<Declaration> declarationList = new LinkedList<>();
    
    public void addDeclaration(Declaration decl) {
        this.declarationList.add(decl);
    }

    public LinkedList<Declaration> getDeclarationList() {
        return this.declarationList;
    }

    public void setDeclarationList(LinkedList<Declaration> declarationList) {
        this.declarationList = declarationList;
    }
    
    public void print(int level) {
        Iterator<Declaration> iterator = this.declarationList.iterator();
        
        while (iterator.hasNext()) {
            iterator.next().print(level);
        }
    }
    
    public CodeItem genCode(){
        CodeItem currentItem = null;
        for(int i = 0; i < declarationList.size(); i++){
            currentItem = declarationList.get(i).genCode();
            if(i > 0){
                declarationList.get(i - 1).genCode().setNextItem(currentItem);
            }
        }
        return currentItem;
    }
    
    public void printFile(String source, int level) {
        String delims = "[.]";
        String[] sourceParse = source.split(delims);
        File file = new File(sourceParse[0] + ".c");
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            Iterator<Declaration> iterator = this.declarationList.iterator();
            while (iterator.hasNext()) {
                iterator.next().printFile(bw, level);
            }
            bw.close();
        } catch (IOException ex) {
            System.err.print("Error opening FileWriter in printFile");
        }
    }

    public void printASTFile(String source, int level) {
        String delims = "[.]";
        String[] sourceParse = source.split(delims);
        File file = new File(sourceParse[0] + ".ast");
        FileWriter fw;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("Program\n");
            
            Iterator<Declaration> iterator = this.declarationList.iterator();
            while (iterator.hasNext()) {
                iterator.next().printASTFile(bw, level+1);
            }
            bw.close();
        } catch (IOException ex) {
            System.err.print("Error opening FileWriter in Program printASTFile");
        }
    }
}