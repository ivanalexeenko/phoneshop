package com.es.core;

public class IdWrapper {
    private Long id;

    public IdWrapper() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public IdWrapper(Long id) {
        this.id = id;
    }
}
