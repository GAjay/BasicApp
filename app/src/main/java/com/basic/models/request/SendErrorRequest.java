package com.basic.models.request;

/**
 * Model class to hold the detail of error objec.t
 */
public class SendErrorRequest {
    String content;
    String to;
    String subject;

    public SendErrorRequest(String content, String to, String subject) {
        this.content = content;
        this.to = to;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SendErrorRequest{" +
                "content='" + content + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }


}
