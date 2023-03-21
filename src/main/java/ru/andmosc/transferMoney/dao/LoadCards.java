package ru.andmosc.transferMoney.dao;

import ru.andmosc.transferMoney.domain.Card;

import java.util.Map;

public interface LoadCards {
    Map<String, Card> loadCards();
}
