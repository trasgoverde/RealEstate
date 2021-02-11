package com.dihouse.app.repository;

import com.dihouse.app.domain.Property;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
