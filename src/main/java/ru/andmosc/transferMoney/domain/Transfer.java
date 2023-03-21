package ru.andmosc.transferMoney.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transfer {
    private String cardFromNumber;
    private String cardToNumber;
    private String cardFromCVV;
    private String cardFromValidTill;
    private Amount amount;

    @Override
    public String toString() {
        return "cardFromNumber: " + cardFromNumber +
                ", cardToNumber: " + cardToNumber;
    }
}
