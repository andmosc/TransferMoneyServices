package ru.andmosc.transferMoney.integration;

import ru.andmosc.transferMoney.domain.Card;

import java.util.Map;

public interface LoadCards {
    Map<String, Card> loadCards();
}
