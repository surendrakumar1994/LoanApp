package com.bank.loneapplication.enums;


public enum LoanStatus {
    ACTIVE, INACTIVE;
    public static LoanStatus get(int index){
        return LoanStatus.values()[index];
    }
}
