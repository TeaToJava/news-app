package ru.clevertec.api.adapter.output.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.api.adapter.output.persistence.jpa.entity.NewsEntity;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID>,
        PagingAndSortingRepository<NewsEntity, UUID>,
        JpaSpecificationExecutor<NewsEntity> {

}