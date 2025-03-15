package com.bramkov.user_microservice.message;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_from")
    private Long idFrom;

    @Column(name = "id_to")
    private Long idTo;

    @Column(name = "message")
    private String message;

    public Message() {}

    public Message(Long idFrom, Long idTo, String message) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
    }

    public Long getIdTo() {
        return idTo;
    }

    public void setIdTo(Long idTo) {
        this.idTo = idTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", idFrom=" + idFrom +
                ", idTo=" + idTo +
                ", message='" + message + '\'' +
                '}';
    }
}
