package com.repository;

import com.models.Page;
import com.models.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends MongoRepository<Page, String> {

    @Query(fields = "{ 'id' : 1, 'path' : 1, 'metadata' : 1, 'sections': 1, 'createdDate' : 1}")
    Optional<Page> findBySiteAndPath(Site site, String path);

    Optional<Page> findBySiteAndPathAndIdNot(Site site, String path, String id);

    @Query(value = "{}", fields = "{ 'id' : 1, 'path' : 1}")
    List<Page> findAllBasicBySite(Site site);

    List<Page> findAllBySite(Site site);

};
