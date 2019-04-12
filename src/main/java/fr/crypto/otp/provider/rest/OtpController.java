package fr.crypto.otp.provider.rest;

import fr.crypto.otp.provider.data.OtpBody;
import fr.crypto.otp.provider.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/otp")
public class OtpController {

    private static final Logger log = LoggerFactory.getLogger(OtpController.class);

    @Autowired
    private OtpService  otpService;

    @RequestMapping(method = POST, value = "/check-otp")
    public ResponseEntity<Boolean>  checkOtp(@RequestBody OtpBody body) {
        log.info("REST get request to check if the otp is valid");
        return new ResponseEntity<>(this.otpService.isValid(body), HttpStatus.OK);
    }
}
