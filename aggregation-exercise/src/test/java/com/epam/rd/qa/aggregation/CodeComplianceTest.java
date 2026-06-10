package com.epam.rd.qa.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CodeComplianceTest {
    @Test
    void testCodeComplianceDepositClass() throws NoSuchMethodException {
        Class<Deposit> depositClass = Deposit.class;
        assertTrue(Modifier.isAbstract(depositClass.getModifiers()),
                "Deposit class should be 'abstract'");

        Constructor<?>[] declaredConstructors = depositClass.getDeclaredConstructors();
        assertEquals(1, declaredConstructors.length,
                "'Deposit' class should declare only one constructor " +
                        "to receive amount and period");

        assertDoesNotThrow(() -> depositClass.getDeclaredConstructor(BigDecimal.class, Integer.TYPE),
                "'Deposit' class should declare only one constructor " +
                        "to receive amount and period");

        Constructor<Deposit> constructor = depositClass.getDeclaredConstructor(BigDecimal.class, Integer.TYPE);
        assertTrue(Modifier.isProtected(constructor.getModifiers()),
                "The constructor should be protected");

        Field[] declaredFields = depositClass.getDeclaredFields();
        assertEquals(2, declaredFields.length);

        Optional<Field> amount = Arrays.stream(declaredFields)
                .filter(field -> field.getName().equals("amount")
                        && Modifier.isProtected(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && field.getType() == BigDecimal.class
                        && !Modifier.isStatic(field.getModifiers()))
                .findAny();
        assertTrue(amount.isPresent(),
                "'amount' should be 'protected final BigDecimal' " +
                        "and should not be static");

        Optional<Field> period = Arrays.stream(declaredFields)
                .filter(field -> field.getName().equals("period")
                        && Modifier.isProtected(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && field.getType() == Integer.TYPE
                        && !Modifier.isStatic(field.getModifiers()))
                .findAny();
        assertTrue(period.isPresent(),
                "'period' should be 'protected final int' " +
                        "and should not be static");
    }

    @ParameterizedTest
    @MethodSource("casesCodeComplianceChildDepositClass")
    void testCodeComplianceChildDepositClasses(Class<Deposit> depositClass) throws NoSuchMethodException {
        assertFalse(Modifier.isAbstract(depositClass.getModifiers()),
                "Child of 'Deposit' class should not be 'abstract");

        assertEquals(Deposit.class, depositClass.getSuperclass(),
                "The " + depositClass.getSimpleName() + "must directlt inherit 'Deposit' class");

        Constructor<?>[] declaredConstructors = depositClass.getDeclaredConstructors();
        assertEquals(1, declaredConstructors.length,
                "Child of 'Deposit' class should declare only one constructor " +
                        "to receive amount and period");

        assertDoesNotThrow(() -> depositClass.getDeclaredConstructor(BigDecimal.class, Integer.TYPE),
                "Child of 'Deposit' class should declare only one constructor " +
                        "to receive amount and period");

        Constructor<Deposit> constructor = depositClass.getDeclaredConstructor(BigDecimal.class, Integer.TYPE);
        assertTrue(Modifier.isPublic(constructor.getModifiers()),
                "The constructor should be public");

        Field[] declaredFields = depositClass.getDeclaredFields();

        assertEquals(declaredFields.length, Arrays.stream(declaredFields)
                        .filter(field -> Modifier.isPrivate(field.getModifiers())
                                && Modifier.isStatic(field.getModifiers())
                                && Modifier.isFinal(field.getModifiers()))
                        .toArray().length,
                "'"+ depositClass.getName() + "' can declare only 'private static final' fields, " +
                        "which do not hide 'Deposit' fields");
    }

    static Stream<Arguments> casesCodeComplianceChildDepositClass() {
        return Stream.of(
                Arguments.of(BaseDeposit.class),
                Arguments.of(SpecialDeposit.class),
                Arguments.of(LongDeposit.class)
        );
    }
}