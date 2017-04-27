package org.kiran.entmdm.repository;

import org.kiran.entmdm.domain.Pharmacustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pharmacustomer entity.
 */
@SuppressWarnings("unused")
public interface PharmacustomerRepository extends JpaRepository<Pharmacustomer,Long> {

}
