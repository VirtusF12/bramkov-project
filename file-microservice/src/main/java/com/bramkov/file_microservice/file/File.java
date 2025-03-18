package com.bramkov.file_microservice.file;

import jakarta.persistence.*;

@Entity
@Table(name = "Files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "idUserFrom")
    private String idUserFrom;

    @Column(name = "idUserTo")
    private String idUserTo;

    @Column(name = "pathToFile")
    private String pathToFile;

    public File() {}

    public File(Long id, String idUserFrom, String idUserTo, String pathToFile) {
        this.id = id;
        this.idUserFrom = idUserFrom;
        this.idUserTo = idUserTo;
        this.pathToFile = pathToFile;
    }

    public Long getId() {
        return id;
    }

    public String getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(String idUserFrom) {
        this.idUserFrom = idUserFrom;
    }

    public String getIdUserTo() {
        return idUserTo;
    }

    public void setIdUserTo(String idUserTo) {
        this.idUserTo = idUserTo;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", idUserFrom='" + idUserFrom + '\'' +
                ", idUserTo='" + idUserTo + '\'' +
                ", pathToFile='" + pathToFile + '\'' +
                '}';
    }
}