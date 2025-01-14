package ru.clevertec.api.adapter.output.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Класс сущности, описывающий создание новости.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "news")
public class NewsEntity {
    /**
     * Уникальный идентификатор новости
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Дата и время создания новости
     */
    @Column(name = "time")
    private LocalDateTime time;

    /**
     * Заголовок новости
     */
    @Column(name = "title")
    private String title;

    /**
     * Текст новости
     */
    @Column(name = "text")
    private String text;

}
