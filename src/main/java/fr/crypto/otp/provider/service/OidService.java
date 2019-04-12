package fr.crypto.otp.provider.service;

import fr.crypto.otp.provider.domain.Oid;
import fr.crypto.otp.provider.repository.OidRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

@Service
public class OidService {

    private static final Logger log = LoggerFactory.getLogger(OidService.class);

    @Autowired
    private OidRepository   oidRepository;

    public Oid  createUser(String username, String password) {
        try {
            long    oid = generateOid();
            String  hashPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            return this.oidRepository.save(new Oid(oid, username,
                    hashPassword));
        } catch (DataIntegrityViolationException e) {
            log.error("Error sql caught {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.SqlError");
        }
    }

    public long generateOid() {
        Random  rand = new Random();
        StringBuilder oidStr = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int n = rand.nextInt(9);
            oidStr.append(n);
        }
        return Integer.parseInt(oidStr.toString());
    }
}
