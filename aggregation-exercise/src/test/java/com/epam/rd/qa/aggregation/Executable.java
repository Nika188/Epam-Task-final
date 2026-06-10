package com.epam.rd.qa.aggregation;

import java.math.BigDecimal;

interface Executable {
    Deposit execute(BigDecimal amount, int period);
}
