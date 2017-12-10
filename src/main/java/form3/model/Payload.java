package form3.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.Arrays;

public class Payload extends ResourceSupport {

    private Iterable<Payment> data;

    public Payload(Iterable<Payment> data) {
        this.data = data;
    }

    public Payload(Payment data) {
        this.data = Arrays.asList(data);
    }

    public Iterable<Payment> getData() {
        return data;
    }

    public void setData(Iterable<Payment> data) {
        this.data = data;
    }

}
