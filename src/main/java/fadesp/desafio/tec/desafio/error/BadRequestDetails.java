package fadesp.desafio.tec.desafio.error;

public class BadRequestDetails extends ErrorDetails{

    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public BadRequestDetails build() {
            BadRequestDetails badRequestDetails = new BadRequestDetails();
            badRequestDetails.setTitle(title);
            badRequestDetails.setStatus(status);
            badRequestDetails.setDetail(detail);
            badRequestDetails.setTimestamp(timestamp);
            badRequestDetails.setDeveloperMessage(developerMessage);
            return badRequestDetails;
        }
    }
}