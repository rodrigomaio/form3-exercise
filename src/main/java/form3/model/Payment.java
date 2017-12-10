package form3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Entity
@JsonInclude(ALWAYS)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String type;

    private Long version;

    @JsonProperty("organisation_id")
    private String organisationId;

    private Attributes attributes;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(ID, payment.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

}
