package fpt.toeic.web.rest.errors;

import java.util.List;

public class MessageConsumeException extends Exception{
    public MessageConsumeException() {
        super();
    }

    public MessageConsumeException(List<String> message) {
        super(String.valueOf(message));
    }
}
