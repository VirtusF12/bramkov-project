package com.bramkov.user_microservice.message;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "item_messages")
public class ItemMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "")
    private StatusMessage statusMessage;

    @Column(name = "")
    private String message;

    @Column(name = "")
    private List<String> images;

    @Column(name = "")
    private List<String> videos;

    public ItemMessage() {}

    public ItemMessage(StatusMessage statusMessage, String message, List<String> images, List<String> videos) {
        this.statusMessage = statusMessage;
        this.message = message;
        this.images = images;
        this.videos = videos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "ItemMessage{" +
                "id=" + id +
                ", statusMessage=" + statusMessage +
                ", message='" + message + '\'' +
                ", images=" + images +
                ", videos=" + videos +
                '}';
    }
}
