package io.github.alexisTrejo11.construction.company.shared;

import org.springframework.http.ResponseEntity;

public class AppErrorResolver {
  public static ResponseEntity<ResponseWrapper<?>> handleResult(Result<?> result) {
    var responseError = ResponseWrapper.applicationError(result);
    return ResponseEntity.status(responseError.getCode()).body(responseError);
  }
}
