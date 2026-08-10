package io.github.alexisTrejo11.construction.company.modules.project.infrastructure.web.controller.dto.response;

import java.math.BigDecimal;

public record SiteLocationResponse(
    String addressLine,
    String city,
    String state,
    String postalCode,
    String country,
    BigDecimal latitude,
    BigDecimal longitude
) {}