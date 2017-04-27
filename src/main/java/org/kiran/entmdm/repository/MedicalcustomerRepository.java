package org.kiran.entmdm.repository;

import org.kiran.entmdm.domain.Medicalcustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Medicalcustomer entity.
 */
@SuppressWarnings("unused")
public interface MedicalcustomerRepository extends JpaRepository<Medicalcustomer,Long> {

}
