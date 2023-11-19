package com.app.pract4;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Message {

    private Date createdAt;

    private String content;

    public Message(Long createdAtInMills, String content) {
        this.createdAt = new Date(createdAtInMills);
        this.content = content;
    }
}
