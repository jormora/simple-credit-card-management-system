package com.example.bank.statements.payload.request;

import java.util.List;

public class CreditCards {

    private List<String> numbers;

    public CreditCards(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

}
