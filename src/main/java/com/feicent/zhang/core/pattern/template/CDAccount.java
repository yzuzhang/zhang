package com.feicent.zhang.core.pattern.template;

public class CDAccount extends Account {
	
    @Override
    protected String doCalculateAccountType() {
        return "Certificate of Deposite";
    }
    
    @Override
    protected double doCalculateInterestRate() {
        return 0.06;
    }
    
}
