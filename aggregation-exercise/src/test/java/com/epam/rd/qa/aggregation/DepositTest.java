package com.epam.rd.qa.aggregation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.epam.rd.qa.aggregation.Util.executableMap;
import static com.epam.rd.qa.aggregation.Util.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepositTest {

    @ParameterizedTest
    @MethodSource("casesConstructorShouldThrow")
    void testConstructorShouldThrow(Class<Deposit> depositClass, BigDecimal amount, int period) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    try {
                        depositClass.getDeclaredConstructor(BigDecimal.class, Integer.TYPE).newInstance(amount, period);
                    } catch (ReflectiveOperationException e) {
                        if (e.getCause() instanceof IllegalArgumentException) {
                            throw e.getCause();
                        }
                        throw new RuntimeException(e);
                    }
                });
    }

    static Stream<Arguments> casesConstructorShouldThrow() {
        return Stream.of(
                Arguments.of(SpecialDeposit.class, new BigDecimal("0."), 1),
                Arguments.of(SpecialDeposit.class, new BigDecimal("-1000"), 75),
                Arguments.of(LongDeposit.class, new BigDecimal("10.00001"), 0),
                Arguments.of(LongDeposit.class, new BigDecimal("1000"), -3)
        );
    }

    @ParameterizedTest
    @MethodSource("casesDeposit")
    void testGetAmount(Deposit deposit, BigDecimal expectedAmount, int expectedPeriod) {
        assertEquals(expectedAmount, deposit.getAmount());
        assertEquals(expectedPeriod, deposit.getPeriod());
    }

    static Stream<Arguments> casesDeposit() {
        return Stream.of(
                Arguments.of(new SpecialDeposit(new BigDecimal("0.00001"), 1), new BigDecimal("0.00001"), 1),
                Arguments.of(new SpecialDeposit(new BigDecimal("1000"), 75), new BigDecimal("1000"), 75),
                Arguments.of(new LongDeposit(new BigDecimal("0.00001"), 1), new BigDecimal("0.00001"), 1),
                Arguments.of(new LongDeposit(new BigDecimal("1000"), 75), new BigDecimal("1000"), 75)
        );
    }

    @ParameterizedTest
    @MethodSource("casesDepositIncome")
    void testDepositIncome(Deposit deposit, String expectedIncome) {
        assertEquals(new BigDecimal(expectedIncome), deposit.income());
    }

    static Stream<Arguments> casesDepositIncome() {
        Random r = new Random(321);
        Collection<String> values = List.of("551.32", "276.28", "215.50", "477.45", "50.00", "340.09", "477.45", "551.32", "340.09", "551.32", "228.25", "228.25", "61.10", "547.11", "61.10", "10.00", "10.00", "314.22", "103.55", "419.36", "0.00", "0.00", "322.50", "0.00", "322.50", "0.00", "0.00", "0.00", "150.00", "0.00");
        Iterator<String> it = values.iterator();
        Stream<Arguments> baseDeposits = Stream.generate(() -> Arguments.of(executableMap.get(0)
                        .execute(new BigDecimal("1000"), nextInt(r, 1, 10)), it.next()))
                .limit(10);
        Stream<Arguments> specialDeposits = Stream.generate(() -> Arguments.of(executableMap.get(1)
                        .execute(new BigDecimal("1000"), nextInt(r, 1, 10)), it.next()))
                .limit(10);
        Stream<Arguments> longDeposits = Stream.generate(() -> Arguments.of(executableMap.get(2)
                        .execute(new BigDecimal("1000"), nextInt(r, 1, 10)), it.next()))
                .limit(10);
        Stream<Arguments> concat = Stream.concat(baseDeposits, specialDeposits);
        return Stream.concat(concat, longDeposits);
    }
}
