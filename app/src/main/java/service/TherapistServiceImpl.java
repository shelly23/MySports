package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.TherapistDAO;
import persistence.daos.UserDAO;
import persistence.dtos.Therapist;
import persistence.validators.TextValidator;

public class TherapistServiceImpl implements TherapistService {

    private static final Logger LOG = LoggerFactory.getLogger(TherapistServiceImpl.class);

    private final TherapistDAO FBpersistenceTherapist;

    public TherapistServiceImpl(TherapistDAO FBpersistenceTherapist) {
        this.FBpersistenceTherapist = FBpersistenceTherapist;
    }

    @Override
    public List<Therapist> getAlLTherapists() throws InterruptedException {
        LOG.debug("Read all therapists");
        return FBpersistenceTherapist.read();
    }

    @Override
    public Therapist getTherapist(long id) throws InterruptedException {
        LOG.debug("Read therapist " + id);
        return FBpersistenceTherapist.getTherapist(id);
    }
}
