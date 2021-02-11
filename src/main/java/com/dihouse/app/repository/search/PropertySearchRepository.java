package com.dihouse.app.repository.search;

import com.dihouse.app.domain.Property;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Property} entity.
 */
public interface PropertySearchRepository extends ElasticsearchRepository<Property, Long> {
}
