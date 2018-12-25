package com.es.core.model.phone;

import java.io.Serializable;

public class Color implements Serializable {
    private Long id;
    private String code;

    public Color() {
    }

    public Color(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(Color.class)) {
            return false;
        }
        Color color = (Color) obj;
        if (color.getId() != null && color.getCode() != null && this.getCode() != null && color.getCode() != null) {
            return color.getId().equals(this.id) && color.getCode().equals(this.code);
        }
        return (color.getId() == null || this.getId() != null)
                && (color.getId() != null || this.getId() == null)
                && (color.getCode() == null || this.getCode() != null)
                && (color.getCode() != null || this.getCode() == null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
