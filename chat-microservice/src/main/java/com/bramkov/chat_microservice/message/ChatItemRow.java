package com.bramkov.chat_microservice.message;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_item_row")
public class ChatItemRow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_from")
    private Long idFrom;

    @Column(name = "id_to")
    private Long idTo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_message", referencedColumnName = "id")
    private Message message;

    public ChatItemRow() {}

    public ChatItemRow(Long id, Long idFrom, Long idTo, Message message) {
        this.id = id;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}