package com.dihouse.app.repository.search;

import com.dihouse.app.domain.Appuser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Appuser} entity.
 */
public interface AppuserSearchRepository extends ElasticsearchRepository<Appuser, Long> {
}
