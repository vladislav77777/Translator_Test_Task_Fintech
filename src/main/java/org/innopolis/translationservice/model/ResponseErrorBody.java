package org.innopolis.translationservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseErrorBody {
    private Error error;

    @Setter
    @Getter
    public static class Error {
        private int code;
        private String message;
        private List<ErrorDetail> errors;
        private List<Detail> details;

        @Setter
        @Getter
        public static class ErrorDetail {
            private String message;
            private String domain;
            private String reason;

        }

        @Setter
        @Getter
        public static class Detail {
            private String type;
            private List<FieldViolation> fieldViolations;

            @Setter
            @Getter
            public static class FieldViolation {
                private String field;
                private String description;

            }
        }
    }
}
