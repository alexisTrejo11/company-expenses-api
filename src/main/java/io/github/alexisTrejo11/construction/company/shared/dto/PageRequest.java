package io.github.alexisTrejo11.construction.company.shared.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PageRequest(
    @Min(value = 0, message = "The page must be above 0")
    int page,
    @Min(value = 1, message = "El size page must be at least 1")
    @Max(value = 100, message = "El size page must be under 100")
    int size,
    String sortBy,

    String sortDirection
) {

  public PageRequest(int page, int size, String sortBy, String sortDirection) {
    this.page = page;
    this.size = size;
    this.sortBy = sortBy == null ? "id" : sortBy;
    this.sortDirection = sortDirection == null ? "ASC" : sortDirection;
  }

  public static PageRequest of(int page, int size, String sortBy, String sortDirection) {
    return new PageRequest(page, size, sortBy, sortDirection);
  }


  public Pageable toPageable() {
    Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection.toUpperCase())
        .orElse(Sort.Direction.ASC);
    return org.springframework.data.domain.PageRequest.of(page, size, Sort.by(direction, sortBy));
  }
}