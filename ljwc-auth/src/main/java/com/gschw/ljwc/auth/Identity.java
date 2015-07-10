package com.gschw.ljwc.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by nop on 7/6/15.
 */
public class Identity {
    @NotBlank
    private String id;

    // this is required by JAX-RS
    @JsonCreator
    public Identity(@JsonProperty("id") String id) {
        this.id = id;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "id='" + id + '\'' +
                '}';
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
