package compiler;

import cminuscompiler.*;
import codegen.CMinusCodeGen;
import codegen.CodeGen;
import dataflow.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import lowlevel.*;
import optimizer.*;
import x64codegen.*;
import x86codegen.*;

public class CMinusCompiler implements Compiler {

    public static HashMap<String, Integer> globalHash = new HashMap<>();
    private static boolean genX64Code = false;

    public CMinusCompiler() {
    }

    public static void setGenX64Code(boolean useX64) {
        genX64Code = useX64;
    }
    
    public static boolean getGenX64Code() {
        return genX64Code;
    }

    @Override
    public void compile(String filePrefix) throws ParseException {
        String fileName = filePrefix + ".c";
        try {
            Parser myParser = new CMinusParser(fileName);
            Program ast = myParser.parse();
            myParser.printTree(filePrefix);
            
            CodeGen codeGenerator = new CMinusCodeGen(ast);
            CodeItem lowLevelCode = codeGenerator.generateLLCode(ast);

            fileName = filePrefix + ".ll";
            PrintWriter outFile =
                    new PrintWriter(new BufferedWriter(
                    new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            int optiLevel = 2;
            LowLevelCodeOptimizer lowLevelOpti =
                    new LowLevelCodeOptimizer(lowLevelCode, optiLevel);
            lowLevelOpti.optimize();

            fileName = filePrefix + ".opti";
            outFile =
                    new PrintWriter(new BufferedWriter(
                    new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            if (genX64Code) {
                X64CodeGenerator x64gen = new X64CodeGenerator(lowLevelCode);
                x64gen.convertToX64();
            }
            else {
                X86CodeGenerator x86gen = new X86CodeGenerator(lowLevelCode);
                x86gen.convertToX86();
            }
            fileName = filePrefix + ".x86";
            outFile =
                    new PrintWriter(new BufferedWriter(
                    new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();
            
            ControlFlowAnalysis cf = new ControlFlowAnalysis(lowLevelCode);
            cf.performAnalysis();

            LivenessAnalysis liveness = new LivenessAnalysis(lowLevelCode);
            liveness.performAnalysis();
            liveness.printAnalysis();

            if (genX64Code) {
                int numRegs = 15;
                X64RegisterAllocator regAlloc =
                        new X64RegisterAllocator(lowLevelCode, numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(
                        new FileWriter(fileName)));
                X64AssemblyGenerator assembler =
                        new X64AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateX64Assembly();
                outFile.close();
            }
            else {
                int numRegs = 7;
                X86RegisterAllocator regAlloc = new X86RegisterAllocator(
                        lowLevelCode, numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(
                        new FileWriter(fileName)));
                X86AssemblyGenerator assembler =
                        new X86AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateAssembly();
                outFile.close();
            }

        } 
        catch (IOException ioe) {
        }
    }

    public static void main(String[] args) throws ParseException {
        String filePrefix = "ComprehensiveTest";
        Compiler myCompiler = new CMinusCompiler();
        setGenX64Code(true);
        myCompiler.compile(filePrefix);
    }
}
