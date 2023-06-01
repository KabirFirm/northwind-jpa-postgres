package com.egen.northwind.api_response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


//@ApiModel(description = "General api response")
@JsonPropertyOrder({"statusCode", "apiCode", "message", "developerMessage", "moreInfo"})
public class RestApiResponse {
    private static final String MESSAGE_NOT_AVAILABLE = "N/A";
    private final String message;
    private final Integer statusCode;
    private final ApiResponseCode apiCode;
    private final String developerMessage;

    private RestApiResponse(String message, HttpStatus statusCode, ApiResponseCode apiCode, String developerMessage) {
        this.message = defaultIfNull(message, MESSAGE_NOT_AVAILABLE);
        this.statusCode = requireNonNull(statusCode).value();
        this.apiCode = defaultIfNull(apiCode, ApiResponseCode.NONE);
        this.developerMessage = developerMessage;
    }

    private RestApiResponse(Builder builder) {
        this(builder.message, builder.statusCode, builder.errorCode, builder.developerMessage);
    }

  
    //@ApiParam(value = "message", example = "Missing multipart 'file' parameter")
    public String getMessage() {
        return message;
    }

   
    //@ApiParam(example = "C001", required = true)
    public ApiResponseCode getApiCode() {
        return apiCode;
    }

   
    //@ApiParam(example = "400", required = true)
    public Integer getStatusCode() {
        return statusCode;
    }

   
    //@ApiParam(example = "More info link")
    public String getMoreInfo() {
        return "";
    }

   
    //@ApiParam(example = "ASDSDA32434323432")
    public String getDeveloperMessage() {
        return isNotBlank(developerMessage) ? developerMessage : MESSAGE_NOT_AVAILABLE;
    }
    public static RestApiResponse internalServerError(String message, UUID uuid) {
        return builder(INTERNAL_SERVER_ERROR)
                .withErrorCode(ApiResponseCode.ERROR)
                .withMessage(message)
                .withDeveloperMessage(uuid.toString())
                .build();
    }

    public static Builder builder(HttpStatus internalServerError) {
        return new Builder(internalServerError);
    }

    public static class Builder {
        private String message;
        private HttpStatus statusCode;
        private ApiResponseCode errorCode;
        private String developerMessage;

        public Builder(HttpStatus status) {
            this.statusCode = status;
        }

        public RestApiResponse build() {
            return new RestApiResponse(this);
        }

        public Builder withMessage(String value) {
            this.message = value;
            return this;
        }

        public Builder withDeveloperMessage(String value) {
            this.developerMessage = value;
            return this;
        }

        public Builder withErrorCode(ApiResponseCode value) {
            this.errorCode = value;
            return this;
        }
    }
}
