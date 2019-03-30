package fr.crypto.otp.provider.data;

public class ActivationBody {

    private String  username;

    private String  usernameService;

    private String  serviceName;

    private long    oid;

    public ActivationBody() {}

    public ActivationBody(String username, String serviceName, long oid) {
        this.username = username;
        this.serviceName = serviceName;
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getUsernameService() {
        return usernameService;
    }

    public void setUsernameService(String usernameService) {
        this.usernameService = usernameService;
    }
}
