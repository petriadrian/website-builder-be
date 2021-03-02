package com.repository;

import com.models.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends MongoRepository<Site, String> {
    
    Optional<Site> findByOrigin(String origin);
};
