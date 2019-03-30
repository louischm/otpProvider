package fr.crypto.otp.provider.rest;

import fr.crypto.otp.provider.data.ActivationBody;
import fr.crypto.otp.provider.service.ActivationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/otp/activation")
public class ActivationController {

    private static final Logger log = LoggerFactory.getLogger(ActivationController.class);

    @Autowired
    private ActivationService   activationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String>  addService(@RequestBody ActivationBody body) {
        log.info("REST post request to get an OTP for user {}", body.getOid());
        return new ResponseEntity<>(this.activationService.getOtp(body), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/service")
    public ResponseEntity activateService(@RequestBody ActivationBody body) {
        log.info("REST post request to activate the service fully {}", body.getOid());
        this.activationService.activateService(body);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity   login(@RequestBody ActivationBody body) {
        log.info("REST post request to log in an user to a service");
        this.activationService.login(body);
        return new ResponseEntity(HttpStatus.OK);
    }
}
