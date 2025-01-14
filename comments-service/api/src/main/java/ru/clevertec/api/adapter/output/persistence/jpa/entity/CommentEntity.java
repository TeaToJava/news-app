package ru.clevertec.api.adapter.output.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Cущность для работы с комментарием
 */
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    /**
     * UUID комментария
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Дата и время создания комментария
     */
    @Column(name = "time")
    private LocalDateTime time;

    /**
     * Текст комментария
     */
    @Column(name = "text")
    private String text;


    @Column(name = "username")
    private String username;

    /**
     * UUID новости
     */
    @Column(name = "news_id")
    private UUID newsId;

}
