package codegen;

import cminuscompiler.Program;
import lowlevel.CodeItem;

public interface CodeGen {
    public CodeItem generateLLCode(Program ast);
}
