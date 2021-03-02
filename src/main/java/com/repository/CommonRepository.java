package com.repository;

import com.models.Site;
import com.models.section.type.Common;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonRepository extends MongoRepository<Common, String> {

    Optional<Common> findBySiteAndId(Site site, String id);

    List<Common> findAllBySite(Site site);
};
