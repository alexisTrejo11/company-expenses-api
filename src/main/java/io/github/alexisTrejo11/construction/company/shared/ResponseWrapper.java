package io.github.alexisTrejo11.construction.company.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private T data;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private int code;

    @JsonProperty("time_stamp")
    private LocalDateTime timestamp;


    public static <T> ResponseWrapper<T> success(T data, String message) {
        return new ResponseWrapper<>(
                true,
                data,
                message,
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> deleted(String entity) {
        String message = entity  +" successfully created";
        return new ResponseWrapper<>(
                true,
                null,
                message,
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> created(T data, String entity) {
        String message = entity + " with successfully created";
        return new ResponseWrapper<>(
                true,
                data,
                message,
                HttpStatus.CREATED.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> success(String message) {
        return new ResponseWrapper<>(
                true,
                null,
                message,
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> created(String entity) {
        String message = entity + " with successfully created";
        return new ResponseWrapper<>(
                true,
                null,
                message,
                HttpStatus.CREATED.value(),
                LocalDateTime.now()
        );
    }


    public static <T> ResponseWrapper<T> found(T data, String entity, Object parameter, Object value) {
        String message = entity + " with " + parameter + " [" + value + "] successfully fetched";
        return new ResponseWrapper<>(
                true,
                data,
                message,
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> found(T data, String entity) {
        String message = entity + " with successfully fetched";
        return new ResponseWrapper<>(
            true,
            data,
            message,
            HttpStatus.OK.value(),
            LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> error(String message, int code) {
        return new ResponseWrapper<>(
                false,
                null,
                message,
                code,
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> notFound(String message) {
        return new ResponseWrapper<>(
                false,
                null,
                message,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> notFound(String entity, Object parameter, Object value) {
        String message = entity + " with " + parameter + " [" + value + "] not found";
        return new ResponseWrapper<>(
                false,
                null,
                message,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> badRequest(String message) {
        return new ResponseWrapper<>(
                false,
                null,
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> applicationError(String message) {
        return new ResponseWrapper<>(
            false,
            null,
            message,
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return new ResponseWrapper<>(
                false,
                null,
                message,
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> conflict(String message) {
        return new ResponseWrapper<>(
            false,
            null,
            message,
            HttpStatus.CONFLICT.value(),
            LocalDateTime.now()
        );
    }

    public static <T> ResponseWrapper<T> unprocessable(String message) {
        return new ResponseWrapper<>(
            false,
            null,
            message,
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            LocalDateTime.now()
        );
    }


    public static <T> ResponseWrapper<T> applicationError(Result<T> result) {
        if (result.getErrorType() == null) {
            return ResponseWrapper.unprocessable(result.getErrorMessage());
        }

        return switch (result.getErrorType()) {
          case UNKNOWN, VALIDATION, BUSINESS_RULE -> ResponseWrapper.unprocessable(result.getErrorMessage());
          case NOT_FOUND ->  ResponseWrapper.notFound(result.getErrorMessage());
          case CONFLICT -> ResponseWrapper.conflict(result.getErrorMessage());
          case null ->  ResponseWrapper.unprocessable(result.getErrorMessage());
        };
    }

}

