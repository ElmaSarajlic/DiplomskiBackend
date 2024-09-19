package ba.edu.ibu.DigitalMarketplace.rest.dto;

public class PasswordRequestDTO {
    private String oldPassword;
    private String newPassword;

    public PasswordRequestDTO(String oldPassword, String newPassword) {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
