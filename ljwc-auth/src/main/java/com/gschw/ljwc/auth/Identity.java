package com.gschw.ljwc.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * An unique identifier.
 */
public class Identity {
    /**
     * ID
     */
    @NotBlank
    private String id;

    /**
     * Initializes an object with an unique id.
     *
     * @param id ID.
     */
    @JsonCreator
    public Identity(@JsonProperty("id") String id) {
        this.id = id;
    }

    /**
     * Returns an ID.
     *
     * @return ID.
     */
    @JsonProperty
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;

        Identity identity = (Identity) o;

        return !(id != null ? !id.equals(identity.id) : identity.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
