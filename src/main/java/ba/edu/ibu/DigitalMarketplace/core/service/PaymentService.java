package ba.edu.ibu.DigitalMarketplace.core.service;

import ba.edu.ibu.DigitalMarketplace.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.DigitalMarketplace.core.model.Artwork;
import ba.edu.ibu.DigitalMarketplace.core.model.User;
import ba.edu.ibu.DigitalMarketplace.core.repository.UserRepository;
import ba.edu.ibu.DigitalMarketplace.rest.dto.PaymentRequest;
import ba.edu.ibu.DigitalMarketplace.core.repository.ArtworkRepository;
import ba.edu.ibu.DigitalMarketplace.util.CustomerUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public Session createCheckoutSession(PaymentRequest request, String successUrl, String cancelUrl) throws StripeException {
        User user = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Customer customer = CustomerUtil.findOrCreateCustomer(user.getEmail(), user.getUsername());

        // Build the checkout session parameters
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomer(customer.getId())
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl);

        // Add items to the session
        for (String artworkId : request.getArtworkIds()) {
            Artwork artwork = artworkRepository.findById(artworkId)
                    .orElseThrow(() -> new ResourceNotFoundException("Artwork not found"));

            paramsBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount((long) (artwork.getPrice() * 100))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(artwork.getTitle())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        // Create the session
        Session session = Session.create(paramsBuilder.build());

        return session;
    }
}
