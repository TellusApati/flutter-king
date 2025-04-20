package hackathon.fluttershy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    //плохая практика, пофиксить перд сдачей
    @Query(value = "SELECT COUNT(*) FROM citizens WHERE start_token IS NULL", nativeQuery = true)
    int findCountWithoutStartToken();

    @Query(value = "SELECT citizen_id FROM citizens WHERE start_token IS NULL", nativeQuery = true)
    List<Integer> findIdsWithoutStartToken();

    @Query(value = "SELECT * FROM citizens WHERE start_token IS NULL", nativeQuery = true)
    List<Citizen> findAllWithoutStartToken();

    @Query(value = "SELECT * FROM citizens WHERE citizen_id = :id", nativeQuery = true)
    Citizen findById(@Param("id") int id);

    @Query(value = "SELECT * FROM citizens WHERE start_token = :token", nativeQuery = true)
    Citizen findByToken(@Param("token") String token);

}
