package fr.crypto.otp.provider.service;

import fr.crypto.otp.provider.data.ActivationBody;
import fr.crypto.otp.provider.domain.Oid;
import fr.crypto.otp.provider.domain.Otp;
import fr.crypto.otp.provider.repository.OidRepository;
import fr.crypto.otp.provider.repository.OtpRepository;
import fr.crypto.otp.provider.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ActivationService {

    private static final Logger log = LoggerFactory.getLogger(ActivationService.class);

    final String HMAC_SHA512 = "HmacSHA512";

    @Autowired
    private OtpRepository   otpRepository;

    @Autowired
    private ServiceRepository   serviceRepository;

    @Autowired
    private OidRepository   oidRepository;

    @Autowired
    private OtpService  otpService;

    @Value("${secretkey}")
    private String  secretKey;

    public String  getOtp(ActivationBody body) {
        try {

            fr.crypto.otp.provider.domain.Service service =
                    this.serviceRepository.findServiceByNameAndOid_OidAndOid_Username(body.getServiceName(), body.getOid(), body.getUsername());
            if (service == null) {
                Oid oid = this.oidRepository.findOidByOidAndUsername(body.getOid(), body.getUsername());
                service = this.serviceRepository.save(new fr.crypto.otp.provider.domain.Service(body.getServiceName(), body.getUsernameService(), oid));
            }
            Otp otp = this.otpRepository.save(new Otp(generateOtp(body), service));
            this.otpService.deleteOtp(otp);
            return otp.getValue();
        } catch (DataIntegrityViolationException e) {
            log.error("Error while trying to generate OTP {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.SqlError");
        }
    }

    public String generateOtp(ActivationBody body)
    {
        Mac sha512_HMAC = null;
        String result = null;
        String bodyConcat = body.getUsernameService() + body.getServiceName() + body.getOid() + secretKey + otpRepository.findAll().size();

        try {
            byte[]  byteKey = secretKey.getBytes("UTF-8");
            sha512_HMAC = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512_HMAC.init(keySpec);
            byte [] mac_data = sha512_HMAC.
                    doFinal(bodyConcat.getBytes("UTF-8"));
            result = bytesToHex(mac_data);
            return result.substring(result.length() - 6);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Error while creating OTP {}", e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.OtpError");
    }

    public void activateService(ActivationBody body) {
        try {
            fr.crypto.otp.provider.domain.Service service =
                    this.serviceRepository.findServiceByNameAndOid_OidAndOid_Username(body.getServiceName(), body.getOid(), body.getUsername());
            service.setActivate(true);
            this.serviceRepository.save(service);
        } catch (DataIntegrityViolationException e) {
            log.error("Error sql caught {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.SqlError");
        }
    }

    public void login(ActivationBody body) {
        try {
            fr.crypto.otp.provider.domain.Service service =
                    this.serviceRepository.findServiceByNameAndOid_OidAndOid_Username(body.getServiceName(), body.getOid(), body.getUsername());
            service.setLogin(true);
            this.serviceRepository.save(service);
        } catch (DataIntegrityViolationException e) {
            log.error("Error sql caught {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.SqlError");
        }
    }

    public static String    bytesToHex(byte[] bytes) {
        final  char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
