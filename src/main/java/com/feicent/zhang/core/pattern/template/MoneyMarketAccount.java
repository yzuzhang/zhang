package com.feicent.zhang.core.pattern.template;

public class MoneyMarketAccount extends Account {
	
    @Override
    protected String doCalculateAccountType() {
        return AccountType.Money_Market;//货币市场账号
    }
    
    @Override
    protected double doCalculateInterestRate() {
        return 0.045;//利息
    }
}
