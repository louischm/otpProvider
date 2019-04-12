package fr.crypto.otp.provider.repository;

import fr.crypto.otp.provider.domain.Oid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OidRepository extends JpaRepository<Oid, Long> {
    public Oid findOidByOidAndUsername(long Oid, String username);
}
