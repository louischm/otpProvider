package fr.crypto.otp.provider.data;

public class OtpBody {

    private String  usernameService;

    private String  serviceName;

    private String  otp;

    public OtpBody(String usernameService, String serviceName, String otp) {
        this.usernameService = usernameService;
        this.serviceName = serviceName;
        this.otp = otp;
    }

    public String getUsernameService() {
        return usernameService;
    }

    public void setUsernameService(String usernameService) {
        this.usernameService = usernameService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
