package com.puc.compiladores.infrastructure;

/**
 * @author Matheus
 */
public enum EnumInstrucoes {
    LDC, LDV, // loads
    ADD, SUB, MULT, DIVI, // operations
    INV, AND, OR, NEG, // logic operations
    CME, CMA, CEQ, CDIF, CMEQ, CMAQ, // compares
    START, HLT, // start and halt
    STR, // store
    JMP, JMPF, NULL, // jumps
    RD, PRN, // read and print
    ALLOC, DALLOC, // memory allocation
    CALL, RETURN; // call procs and funcs


    EnumInstrucoes() {}

    public static boolean isValidCommand (String command) {
        EnumInstrucoes[] allInst = EnumInstrucoes.values();
        for (EnumInstrucoes inst : allInst) {
            if (inst.equals(command)) {
                return true;
            }
        }
        return false;
    }



}
