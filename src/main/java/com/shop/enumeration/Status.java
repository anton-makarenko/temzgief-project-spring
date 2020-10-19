package com.shop.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Status {
    REGISTERED, PAID, CANCELLED, CREATED;

    public List<Status> allowedTransactions() {
        switch (this) {
            case REGISTERED:
                return Collections.unmodifiableList(Arrays.asList(PAID, CANCELLED));
            case CREATED:
                return Collections.singletonList(REGISTERED);
            default:
                return Collections.emptyList();
        }
    }
}
