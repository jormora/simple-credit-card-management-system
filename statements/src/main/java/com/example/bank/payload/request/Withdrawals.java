package com.example.bank.payload.request;

import java.util.List;

public class Withdrawals {
    private List<Withdrawal> withdrawals;

    public Withdrawals(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }
}
