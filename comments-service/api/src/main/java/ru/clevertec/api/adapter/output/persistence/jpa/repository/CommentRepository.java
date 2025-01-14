package ru.clevertec.api.adapter.output.persistence.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.CommentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID>,
        JpaSpecificationExecutor<CommentEntity>, PagingAndSortingRepository<CommentEntity, UUID> {

    List<CommentEntity> findAll(@Nullable Specification<CommentEntity> spec);

    List<CommentEntity> findByNewsId(UUID newsId);

    Page<CommentEntity> findAllByNewsId(UUID newsId, Pageable pageable);


    interface Specs {

        static Specification<CommentEntity> byDateGreaterThan(Optional<LocalDateTime> date) {
            return (root, query, builder) ->
                    date.isEmpty() ?
                            builder.disjunction() :
                            builder.greaterThan(root.get("time"), date.get());
        }

        static Specification<CommentEntity> byUsername(Optional<String> username) {
            return (root, query, builder) ->
                    username.isEmpty() ?
                            builder.disjunction() :
                            builder.equal(root.get("username"), username.get());
        }

        static Specification<CommentEntity> byNewsId(UUID newsId) {
            return (root, query, builder) ->
                    builder.equal(root.get("newsId"), newsId);
        }

    }
}