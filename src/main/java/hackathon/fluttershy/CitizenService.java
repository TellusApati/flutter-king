package hackathon.fluttershy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;
    public static CitizenService citizenService;


    public CitizenService(CitizenRepository citizenRepository) {
        citizenService = this;
        this.citizenRepository = citizenRepository;
    }

    public long getCount() {
        return  citizenRepository.findCountWithoutStartToken();
    }

    public List<Integer> getIdsWithoutToken() {
        return citizenRepository.findIdsWithoutStartToken();
    }

    public List<Citizen> getAllWithoutStartToken() {
        return citizenRepository.findAllWithoutStartToken();
    }

    public Citizen getById(int id) {
        return citizenRepository.findById(id);
    }

    public Citizen getByToken(String token) {
        return citizenRepository.findByToken(token);
    }

}
