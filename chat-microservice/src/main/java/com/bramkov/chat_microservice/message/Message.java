package com.bramkov.chat_microservice.message;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "text")
    private String text;

    @Column(name = "images")
    private List<String> images = new ArrayList<>(); // пути изображений на микросервисе file-microservice

    @Column(name = "videos")
    private List<String> videos = new ArrayList<>(); // пути видео файлов на микросервисе file-microservice

    @OneToOne(mappedBy = "message")
    private ChatItemRow chatItemRow;

    public Message() {}

    public Message(Long id, Status status, String text, List<String> images, List<String> videos, ChatItemRow chatItemRow) {
        this.id = id;
        this.status = status;
        this.text = text;
        this.images = images;
        this.videos = videos;
        this.chatItemRow = chatItemRow;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public ChatItemRow getChatItemRow() {
        return chatItemRow;
    }

    public void setChatItemRow(ChatItemRow chatItemRow) {
        this.chatItemRow = chatItemRow;
    }
}