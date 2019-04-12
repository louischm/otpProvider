package fr.crypto.otp.provider.rest;

import fr.crypto.otp.provider.domain.Oid;
import fr.crypto.otp.provider.service.OidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/otp/user")
public class OidController {

    private static final Logger log = LoggerFactory.getLogger(OidController.class);

    @Autowired
    private OidService  oidService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Oid>  createUser(@RequestParam(name = "username")String username,
                                           @RequestParam(name = "password")String password) {
        log.info("REST post request to create an user");
        return new ResponseEntity<>(this.oidService.createUser(username, password), HttpStatus.OK);
    }
}
