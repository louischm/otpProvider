package fr.crypto.otp.provider.service;

import fr.crypto.otp.provider.data.OtpBody;
import fr.crypto.otp.provider.domain.Otp;
import fr.crypto.otp.provider.repository.OtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OtpService {

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private OtpRepository   otpRepository;

    public Boolean  isValid(OtpBody otp) {
        try {
            Otp result = this.otpRepository.findOtpByValueAndService_NameAndService_Usernameservice(otp.getOtp(), otp.getServiceName(), otp.getUsernameService());
            if (result == null) {
                return Boolean.FALSE;
            }
            this.otpRepository.delete(result);
            return Boolean.TRUE;
        } catch (DataIntegrityViolationException e) {
            log.error("Error sql catch {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error.SqlError");
        }
    }

    @Async
    @Transactional
    public void deleteOtp(Otp otp) {
        try {
            Thread.sleep(30000L);
        } catch (InterruptedException e) {
            log.error("Error catch during sleep {}", e.getMessage());
        }
        log.info("Deleting otp id: {}", otp.getId());
        this.otpRepository.delete(otp);
    }
}
