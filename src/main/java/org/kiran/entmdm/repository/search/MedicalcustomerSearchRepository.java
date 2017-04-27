package org.kiran.entmdm.repository.search;

import org.kiran.entmdm.domain.Medicalcustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medicalcustomer entity.
 */
public interface MedicalcustomerSearchRepository extends ElasticsearchRepository<Medicalcustomer, Long> {
}
