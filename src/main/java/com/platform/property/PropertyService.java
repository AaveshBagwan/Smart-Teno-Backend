package com.platform.property;

import com.platform.repository.PropertyRepository;
import com.platform.common.exception.BadRequestException;
import com.platform.common.model.StatusEnum;
import com.platform.entity.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> getProperties(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId should not be null");
        }
        return propertyRepository.findByUserIdAndStatus(userId, StatusEnum.A);
    }

    public Property addProperty(Property property) {
        if (property == null || property.getUserId() == null) {
            throw new IllegalArgumentException("Property and UserId should not be null");
        }
        property.setStatus(StatusEnum.A);
        return propertyRepository.save(property);
    }

    public Property updateProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property should not be null");
        }

        Property existingProperty = propertyRepository.findByPropertyIdAndStatus(property.getPropertyId(), StatusEnum.A)
                .orElseThrow(() -> new BadRequestException("Property not found or inactive"));

        if (!existingProperty.getUserId().equals(property.getUserId())) {
            throw new BadRequestException("User does not have permission to update this property");
        }
        if (StringUtils.hasText(property.getName())) {
            existingProperty.setName(property.getName());
        }
        if (StringUtils.hasText(property.getAddress())) {
            existingProperty.setAddress(property.getAddress());
        }
        if (StringUtils.hasText(property.getPropertyImgUrl())) {
            existingProperty.setPropertyImgUrl(property.getPropertyImgUrl());
        }
        return propertyRepository.save(existingProperty);
    }

}
