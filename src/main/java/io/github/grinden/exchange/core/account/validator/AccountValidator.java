package io.github.grinden.exchange.core.account.validator;

import io.github.grinden.exchange.configuration.InvalidExchangeArgument;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AccountValidator {

    public static void validateAge(String pesel) {
        LocalDate birthDate = getBirthDate(pesel);
        LocalDate now = LocalDate.now();
        long numberOfYears = ChronoUnit.YEARS.between(birthDate, now);
        if (numberOfYears < 18) {
            throw new InvalidExchangeArgument("Illegal argument - age must be equal or over 18");
        }
    }

    private static LocalDate getBirthDate(String pesel) {
        int year = 10 * Integer.parseInt(pesel.substring(0, 1));
        year += Integer.parseInt(pesel.substring(1, 2));
        int month = 10 * Integer.parseInt(pesel.substring(2, 3));
        month += Integer.parseInt(pesel.substring(3, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month > 80 && month < 93) {
            year += 1800;
            month -= 80;
        } else if (month > 0 && month < 13) {
            year += 1900;
        } else if (month > 20 && month < 33) {
            year += 2000;
            month -= 20;
        } else if (month > 40 && month < 53) {
            year += 2100;
            month -= 40;
        } else if (month > 60 && month < 73) {
            year += 2200;
            month -= 60;
        }

        return LocalDate.of(year, month, day);
    }

}
