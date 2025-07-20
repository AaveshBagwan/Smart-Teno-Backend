package com.platform.repository;

import com.platform.common.model.StatusEnum;
import com.platform.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByPropertyIdAndStatus(Long propertyId, StatusEnum statusEnum);

    List<Property> findByUserIdAndStatus(Long userId, StatusEnum statusEnum);

}
