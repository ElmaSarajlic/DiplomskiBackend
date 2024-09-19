package ba.edu.ibu.DigitalMarketplace.util;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;

public class CustomerUtil {
    public static Customer findOrCreateCustomer(final String email, final String name) throws StripeException {
        final CustomerSearchParams params =
                CustomerSearchParams
                        .builder()
                        .setQuery("email:'" + email + "'")
                        .build();

        final CustomerSearchResult result = Customer.search(params);

        Customer customer;

        if (result.getData().isEmpty()) {
            final CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(name)
                    .setEmail(email)
                    .build();

            customer = Customer.create(customerCreateParams);
        } else {
            customer = result.getData().get(0);
        }

        return customer;
    }
}
