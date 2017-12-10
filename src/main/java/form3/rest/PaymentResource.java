package form3.rest;

import form3.model.Payload;
import form3.model.Payment;
import form3.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * REST controller for managing Payments.
 *
 */
@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    @Autowired
    PaymentRepository paymentRepository;

    /**
     * GET /payments/{id} : get the Payment with the specified id.
     *
     * @param id the id of the payment to retrieve
     * @return the payment body with status 200 (OK), or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Payload> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment with id : {}", id);
        Payment payment = paymentRepository.findOne(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Payload payload = new Payload(payment);
        payload.add(linkTo(methodOn(PaymentResource.class).getPayment(id)).withSelfRel());
        return ResponseEntity.ok().body(payload);
    }

    /**
     * PUT /payments/{id} : update the Payment with the specified id.
     *
     * @param id the payment id
     * @param payment the payment body
     * @return the payment body with status 200 (OK), or status 404 (Not Found)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Payload> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        log.debug("REST request to update Payment with id : {}", id);
        if(!paymentRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        payment.setID(id);
        Payment saved = paymentRepository.save(payment);
        Payload payload = new Payload(saved);
        payload.add(linkTo(methodOn(PaymentResource.class).updatePayment(id, saved)).withSelfRel());
        return ResponseEntity.ok().body(payload);
    }

    /**
     * DELETE /payments/{id} : delete the Payment with the specified id.
     *
     * @param id the payment id
     * @return 204 (NO CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long  id) {
        log.debug("REST request to delete Payment with id : {}", id);
        if(!paymentRepository.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /payments : create a new Payment.
     *
     * @param payment the payment to be created
     * @return the payment body with status 201 (CREATED)
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Payload> createPayment(@RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to create Payment : {}", payment);
        Payment saved = paymentRepository.save(payment);
        Payload payload = new Payload(saved);
        Link link = linkTo(methodOn(PaymentResource.class).createPayment(saved)).slash(saved.getID()).withSelfRel();
        payload.add(link);
        return ResponseEntity.created(new URI(link.getHref())).body(payload);
    }

    /**
     * GET /payments : get all Payments.
     *
     * @return the list of payments in body with status 200 (OK)
     */
    @GetMapping()
    public ResponseEntity<Payload> getAllPayments() {
        log.debug("REST request all Payments");
        Payload payload = new Payload(paymentRepository.findAll());
        payload.add(linkTo(methodOn(PaymentResource.class).getAllPayments()).withSelfRel());
        return ResponseEntity.ok().body(payload);
    }

}
