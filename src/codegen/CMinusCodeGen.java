package codegen;

import cminuscompiler.Program;
import lowlevel.CodeItem;
import lowlevel.Data;
import cminuscompiler.Declaration;
import java.util.List;

public class CMinusCodeGen implements CodeGen {
    private Program parseTree;
    
    public CMinusCodeGen(Program parseTree){
        this.parseTree = parseTree;
    }
    
    public CodeItem generateCode(){
        List<Declaration> declList = parseTree.getDeclarationList();
        CodeItem currentItem = null;
        for(int i = 0; i < declList.size(); i++){
            currentItem = declList.get(i).genCode();
            if(i > 0){
                declList.get(i - 1).genCode().setNextItem(currentItem);
            }
        }
        return currentItem;
    }
}
