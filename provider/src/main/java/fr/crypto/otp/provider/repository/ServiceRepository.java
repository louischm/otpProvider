package fr.crypto.otp.provider.repository;

import fr.crypto.otp.provider.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findServiceByNameAndOid_OidAndOid_Username(String name, long oid, String username);
}
