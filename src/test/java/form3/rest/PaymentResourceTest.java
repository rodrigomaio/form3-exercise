package form3.rest;

import form3.Application;
import form3.model.Attributes;
import form3.model.Payment;
import form3.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class PaymentResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    private String contentType = "application/hal+json;charset=UTF-8";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.paymentRepository.deleteAll();
        this.payment = new Payment();
        Attributes attributes = new Attributes();
        attributes.setCurrency("GBP");
        attributes.setProcessingDate(new Date());
        payment.setAttributes(attributes);
        payment.setType("Payment");
        this.paymentRepository.save(payment);
    }

    @Test
    public void getPayment() throws Exception {
        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].type", is("Payment")))
                .andExpect(jsonPath("$.data[0].attributes.currency", is("GBP")));
    }

    @Test
    public void getPaymentNotFound() throws Exception {
        mockMvc.perform(get("/payments/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePayment() throws Exception {
        String organisationId = "743d5b63";
        this.payment.setOrganisationId(organisationId);
        mockMvc.perform(put("/payments/1").content(json(payment)).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].type", is("Payment")))
                .andExpect(jsonPath("$.data[0].attributes.currency", is("GBP")))
                .andExpect(jsonPath("$.data[0].organisation_id", is(organisationId)));
    }

    @Test
    public void updatePaymentNotFound() throws Exception {
        mockMvc.perform(put("/payments/2").content(json(payment)).contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletePayment() throws Exception {
        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deletePaymentNotFound() throws Exception {
        mockMvc.perform(delete("/payments/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createPayment() throws Exception {
        mockMvc.perform(post("/payments").content(json(payment)).contentType(contentType))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].type", is("Payment")))
                .andExpect(jsonPath("$.data[0].attributes.currency", is("GBP")));
    }

    @Test
    public void getAllPayments() throws Exception {
        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].type", is("Payment")))
                .andExpect(jsonPath("$.data[0].attributes.currency", is("GBP")));
    }

    /**
     *  When an HTTP request comes in that specifies an Accept header, Spring MVC loops through the configured HttpMessageConverter
     *  until it finds one that can convert from the POJO domain model types into the content-type specified in the Accept header,
     *  if so configured. Spring Boot automatically wires up an HttpMessageConverter that can convert generic Object s to JSON,
     *  absent any more specific converter. - https://spring.io/guides/tutorials/bookmarks/
     */
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}