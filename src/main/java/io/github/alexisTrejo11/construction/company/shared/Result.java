package io.github.alexisTrejo11.construction.company.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private T data;

    @JsonProperty("error")
    private String errorMessage;

    @JsonProperty("error_type")
    private ErrorType errorType;

    public enum ErrorType {
        CONFLICT,
        VALIDATION,
        BUSINESS_RULE,
        NOT_FOUND,
        UNKNOWN,
    }


    @JsonCreator
    public Result(@JsonProperty("success") boolean success,
                  @JsonProperty("data") T data,
                  @JsonProperty("error") String errorMessage,
                  @JsonProperty("error_type")  ErrorType errorType
                  ) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, null, null);
    }

    public static <T> Result<T> error(String errorMessage) {
        return new Result<>(false, null, errorMessage, ErrorType.UNKNOWN);
    }

    public static <T> Result<T> error(ErrorType errorType, String errorMessage) {
        return new Result<>(false, null, errorMessage, errorType);
    }

    public static <T> Result<T> conflict(String errorMessage) {
        return new Result<>(false, null, errorMessage, ErrorType.CONFLICT);
    }
    public static <T> Result<T> validation(String errorMessage) {
        return new Result<>(false, null, errorMessage, ErrorType.VALIDATION);
    }

    public static <T> Result<T> business(String errorMessage) {
        return new Result<>(false, null, errorMessage, ErrorType.BUSINESS_RULE);
    }

    public static <T> Result<T> notFound(String errorMessage) {
        return new Result<>(false, null, errorMessage, ErrorType.NOT_FOUND);
    }

    public static Result<Void> success() {
        return new Result<>(true, null, null, null);
    }

}
