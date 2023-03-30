package ru.andmosc.transferMoney.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ru.andmosc.transferMoney.domain.Card;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDatabaseCards {
    private DatabaseCards instance;

    @BeforeAll
    void setUp() {
        instance = DatabaseCards.getInstance();
    }

    @Test
    public void instance_notNull() {
        assertNotNull(instance);
        assertNotNull(instance.getCards());
        assertNotNull(instance.getConfirmationCode());
    }
}