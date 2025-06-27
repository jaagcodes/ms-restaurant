package com.plazoleta.msrestaurant.infrastructure.security.adapter;

import com.plazoleta.msrestaurant.domain.api.ISecurityServicePort;
import com.plazoleta.msrestaurant.infrastructure.security.util.SecurityDetails;

public class SecurityServiceAdapter implements ISecurityServicePort {
    @Override
    public Long getCurrentUserId() {
        return SecurityDetails.getCurrentUser().id();
    }
}