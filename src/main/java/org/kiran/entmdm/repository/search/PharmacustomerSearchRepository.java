package org.kiran.entmdm.repository.search;

import org.kiran.entmdm.domain.Pharmacustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pharmacustomer entity.
 */
public interface PharmacustomerSearchRepository extends ElasticsearchRepository<Pharmacustomer, Long> {
}
