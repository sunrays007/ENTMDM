package org.kiran.entmdm.repository.search;

import org.kiran.entmdm.domain.Enterprisecustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Enterprisecustomer entity.
 */
public interface EnterprisecustomerSearchRepository extends ElasticsearchRepository<Enterprisecustomer, Long> {
}
