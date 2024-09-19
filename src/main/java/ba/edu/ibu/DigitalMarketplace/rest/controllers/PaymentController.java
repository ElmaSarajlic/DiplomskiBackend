package ba.edu.ibu.DigitalMarketplace.rest.controllers;

import ba.edu.ibu.DigitalMarketplace.core.service.PaymentService;
import ba.edu.ibu.DigitalMarketplace.rest.dto.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public Map<String, Object> createCheckoutSession(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        String successUrl = "http://localhost:5173/successPayment";
        String cancelUrl = "http://localhost:5173/cancelPayment";

        Session session = paymentService.createCheckoutSession(paymentRequest, successUrl, cancelUrl);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", session.getId());

        return responseData;
    }
}
