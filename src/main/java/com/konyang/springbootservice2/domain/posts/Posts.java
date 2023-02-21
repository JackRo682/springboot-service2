package com.konyang.springbootservice2.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "POSTS")

@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE", length = 500, nullable = false)
    private String title;

    @Column(name = "CONTENT", length = 500, nullable = false)
    private String content;

    @Column(name="AUTHOR")
    private String author;

    @Column(name="FILENAME")
    private String filename;

    @Column(name="FILEPATH")
    private String filepath;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author=author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
