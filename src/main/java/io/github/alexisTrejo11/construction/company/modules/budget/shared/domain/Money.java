package io.github.alexisTrejo11.construction.company.modules.budget.shared.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount, String currency) {

  public Money {
    Objects.requireNonNull(amount, "Amount cannot be null");
    Objects.requireNonNull(currency, "Currency cannot be null");
    if (currency.trim().length() != 3) {
      throw new IllegalArgumentException("Currency must be a 3-letter ISO code");
    }
    amount = amount.setScale(2, RoundingMode.HALF_UP);
  }

  public static Money zero(String currency) {
    return new Money(BigDecimal.ZERO, currency);
  }

  public static Money of(BigDecimal amount, String currency) {
    return new Money(amount, currency);
  }

  public Money add(Money other) {
    ensureSameCurrency(other);
    return new Money(this.amount.add(other.amount), this.currency);
  }

  public Money subtract(Money other) {
    ensureSameCurrency(other);
    return new Money(this.amount.subtract(other.amount), this.currency);
  }

  public boolean isGreaterThan(Money other) {
    ensureSameCurrency(other);
    return this.amount.compareTo(other.amount) > 0;
  }

  private void ensureSameCurrency(Money other) {
    if (!this.currency.equalsIgnoreCase(other.currency)) {
      throw new IllegalArgumentException(
          "Cannot perform operation on different currencies: " + this.currency + " vs " + other.currency
      );
    }
  }
}
