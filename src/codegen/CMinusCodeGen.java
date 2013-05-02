package codegen;

import cminuscompiler.Program;
import lowlevel.CodeItem;

public class CMinusCodeGen implements CodeGen {
    private Program ast;
    
    public CMinusCodeGen(Program ast){
        this.ast = ast;
    }
    
    public CodeItem generateLLCode(Program parseTree){
        return parseTree.genCode();
    }
}
