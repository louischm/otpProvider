package fr.crypto.otp.provider.repository;

import fr.crypto.otp.provider.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    public Otp  findOtpByValueAndService_NameAndService_Usernameservice(String value, String serviceName, String usernameService);
}
