package com.vhs.rental.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="vhs")
public class VHS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotBlank(message = "field is required")
    @Column(unique = true, name="title")
    private String title;
    @NotBlank(message = "field is required")
    @Column(name="genre")
    private String genre;
    @Column(name="is_available")
    private boolean available;

    public VHS(){}

    public VHS(String title, String genre, boolean isAvailable) {
        this.title = title;
        this.genre = genre;
        this.available = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "VHS{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", isAvailable=" + available +
                '}';
    }
}
