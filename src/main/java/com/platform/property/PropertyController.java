package com.platform.property;

import com.platform.common.exception.BadRequestException;
import com.platform.common.model.ResponseDTO;
import com.platform.common.model.UserSession;
import com.platform.common.utils.ResponseUtils;
import com.platform.common.utils.SessionUtils;
import com.platform.entity.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getProperties() {
        Long userId = Optional.ofNullable(SessionUtils.getUserSession())
                .map(UserSession::getUserId)
                .orElseThrow(() -> new BadRequestException("userId not found"));

        return ResponseUtils.sendResponse(
                propertyService.getProperties(userId),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addProperty(
            @RequestBody @Validated(Property.AddPropertyValidatorGrp.class) Property property) {
        Long userId = Optional.ofNullable(SessionUtils.getUserSession())
                .map(UserSession::getUserId)
                .orElseThrow(() -> new BadRequestException("userId not found"));

        property.setUserId(userId);

        return ResponseUtils.sendResponse(
                propertyService.addProperty(property),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateProperty(
            @RequestBody @Validated(Property.UpdatePropertyValidatorGrp.class) Property property) {

        Long userId = Optional.ofNullable(SessionUtils.getUserSession())
                .map(UserSession::getUserId)
                .orElseThrow(() -> new BadRequestException("userId not found"));

        property.setUserId(userId);

        return ResponseUtils.sendResponse(
                propertyService.updateProperty(property),
                HttpStatus.OK
        );
    }


}
