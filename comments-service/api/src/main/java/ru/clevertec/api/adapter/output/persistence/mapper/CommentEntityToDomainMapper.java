package ru.clevertec.api.adapter.output.persistence.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.CommentEntity;
import ru.clevertec.core.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentEntityToDomainMapper {
    CommentEntity toEntity(Comment comment);

    Comment toDomain(CommentEntity commentEntity);

    List<CommentEntity> toEntities(List<Comment> comments);

    List<Comment> toDomains(List<CommentEntity> commentEntities);
}