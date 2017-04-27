package org.kiran.entmdm.repository;

import org.kiran.entmdm.domain.Enterprisecustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enterprisecustomer entity.
 */
@SuppressWarnings("unused")
public interface EnterprisecustomerRepository extends JpaRepository<Enterprisecustomer,Long> {

}
