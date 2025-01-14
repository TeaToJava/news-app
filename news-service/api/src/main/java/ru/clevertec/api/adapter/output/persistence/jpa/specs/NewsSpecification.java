package ru.clevertec.api.adapter.output.persistence.jpa.specs;

import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;

import java.time.LocalDateTime;
import java.util.Optional;

public class NewsSpecification {
    public static Specification<NewsEntity> hasTitle(Optional<String> title) {
        return (root, query, builder) ->
                title.isEmpty() ?
                        builder.disjunction() :
                        builder.equal(root.get("title"), title.get());
    }

    public static Specification<NewsEntity> hasDateGreaterThan(Optional<LocalDateTime> date) {
        return (root, query, builder) ->
                date.isEmpty() ?
                        builder.disjunction() :
                        builder.greaterThan(root.get("time"), date.get());
    }

}
