package com.epam.rd.qa.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Stream;

import static com.epam.rd.qa.aggregation.Util.generateDeposits;
import static com.epam.rd.qa.aggregation.Util.nextInt;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    static final Deposit[] DEPOSITS = new Deposit[]{
            new BaseDeposit(new BigDecimal("10.5"), 1),
            new BaseDeposit(new BigDecimal("1000.1"), 2),
            new BaseDeposit(new BigDecimal("2000.1"), 5),
            new SpecialDeposit(new BigDecimal("10.5"), 1),
            new SpecialDeposit(new BigDecimal("1000.1"), 3),
            new SpecialDeposit(new BigDecimal("1000.1"), 6),
            new LongDeposit(new BigDecimal("1"), 1),
            new LongDeposit(new BigDecimal("3000"), 6),
            new LongDeposit(new BigDecimal("2999.99"), 7),
            new LongDeposit(new BigDecimal("2999.99"), 9),
    };

    @Test
    void testDefaultConstructor() throws ReflectiveOperationException {
        Class<Client> clientClass = Client.class;
        Field depositsField = clientClass.getDeclaredField("deposits");
        Client client = new Client();
        depositsField.setAccessible(true);
        int length = Array.getLength(depositsField.get(client));
        assertEquals(10, length, "The length of 'deposits' field " +
                "must be 10");
    }

    @Test
    void testAddDeposit() {
        Client client = new Client();
        for (Deposit deposit : DEPOSITS) {
            assertTrue(client.addDeposit(deposit));

        }
        assertFalse(client.addDeposit(DEPOSITS[0]));
        assertFalse(client.addDeposit(DEPOSITS[0]));
    }

    @ParameterizedTest
    @MethodSource("casesTotalIncome")
    void testTotalMaxIncome(Client client, String expectedTotalIncome, String expectedMaxIncome,
                            int number, String expectedIncome) {
        assertEquals(new BigDecimal(expectedTotalIncome), client.totalIncome(),
                "totalIncome must be " + expectedTotalIncome);
        assertEquals(new BigDecimal(expectedMaxIncome), client.maxIncome(),
                "maxIncome must be " + expectedMaxIncome);
        assertEquals(new BigDecimal(expectedIncome), client.getIncomeByNumber(number),
                "income must be " + expectedIncome);
    }

    static Stream<Arguments> casesTotalIncome() {
        Random r = new Random(13);
        return Stream.of(
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(7, 5).toArray(Deposit[]::new)), "3829.21", "1875.05",
                        nextInt(r, 0, 10), "511.74"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 2347).toArray(Deposit[]::new)), "2040.26", "1505.91",
                        nextInt(r, 0, 10), "1505.91"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 748).toArray(Deposit[]::new)), "2072.88", "610.85",
                        nextInt(r, 0, 10), "0.00"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(5, 12).toArray(Deposit[]::new)), "2070.36", "790.95",
                        nextInt(r, 0, 10), "0.00"),
                Arguments.of(ClientFactory.newInstance(
                        generateDeposits(12, 15).toArray(Deposit[]::new)), "2576.04", "728.88",
                        nextInt(r, 0, 10), "728.88")
        );
    }

    private static class ClientFactory {
        public static Client newInstance(Deposit[] toArray) {
            Client client = new Client();
            for (Deposit deposit : toArray) {
                client.addDeposit(deposit);
            }
            return client;
        }
    }
}