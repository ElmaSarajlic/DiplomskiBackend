package ba.edu.ibu.DigitalMarketplace.rest.dto;

public class PaymentRequest {
    private String[] artworkIds;
    private String customerId;

    public String[] getArtworkIds() {
        return artworkIds;
    }

    public void setArtworkIds(String[] artworkIds) {
        this.artworkIds = artworkIds;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
