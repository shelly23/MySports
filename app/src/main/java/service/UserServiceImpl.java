package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import persistence.daos.UserDAO;
import persistence.dtos.User;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;
import persistence.validators.TextValidator;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO FBpersistenceUser;
    private final TextValidator textValidator;

    public UserServiceImpl(UserDAO FBpersistenceUser, TextValidator textValidator) {
        this.FBpersistenceUser = FBpersistenceUser;
        this.textValidator = textValidator;
    }

    @Override
    public long saveUser(User user) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException, InterruptedException {
        if (checkUser(user)) {
            long id = this.FBpersistenceUser.create(user);
            LOG.debug("Saved user {}", user.getUsername());
            return id;
        } else {
            LOG.error("Failed to create user {}, cause: invalid user", user.getUsername());
            return -1;
        }
    }

    @Override
    public User loginUser(String username, String password) throws InvalidValueException, MandatoryValueException, IOException, PersistenceException, NoSuchAlgorithmException, InterruptedException {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            boolean result = this.FBpersistenceUser.canLogin(username, password);
            if (result) {
                LOG.debug("Logged in user {}", username);
                return this.FBpersistenceUser.getUser(username);
            } else {
                LOG.error("Failed to login user {}", username);
                return null;
            }
        } else {
            LOG.error("Failed to login user {}, cause: invalid user", username);
            return null;
        }
    }

    @Override
    public void delete(List<User> users) throws PersistenceException {
        this.FBpersistenceUser.delete(users);
        LOG.debug("Deleted vehicles");
    }

    @Override
    public List<User> getAllUsers() throws PersistenceException, InterruptedException {
        LOG.debug("Read all users");
        return FBpersistenceUser.read();
    }

    @Override
    public boolean update(User user) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException {
        if (checkUser(user)) {
            FBpersistenceUser.update(user);
            return true;
        }
        LOG.error("Failed to update user {}", user.getUsername());
        return false;
    }

    private boolean checkUser(User user) throws MandatoryValueException, InvalidValueException, IOException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            LOG.error("Mandatory value in user: username");
            throw new MandatoryValueException("Username");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            LOG.error("Mandatory value in user: password");
            throw new MandatoryValueException("Password");
        }
        if (user.getPrename() == null || user.getPrename().isEmpty()) {
            LOG.error("Mandatory value in user: prename");
            throw new MandatoryValueException("Prename");
        }
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            LOG.error("Mandatory value in user: surname");
            throw new MandatoryValueException("Surname");
        }
        textValidator.validText(255, user.getPrename(), "Prename");
        textValidator.validText(255, user.getSurname(), "Surname");
        textValidator.validText(255, user.getUsername(), "Username");
        textValidator.validText(255, user.getPassword(), "Password");

        LOG.debug("User {} valid", user.getUsername());
        return true;
    }

    /**   @Override public Double[] getMinMaxPrice() throws PersistenceException {
    Long[] tmp = this.DBpersistence.getMinMaxPrice();
    Double[] result = new Double[2];
    for (int i = 0; i < result.length; i++) {
    double help = tmp[i];
    help /= 100;
    result[i] = help;
    }
    LOG.debug("Got minimum and maximum price ({}, {})", result[0], result[1]);
    return result;
    }

     @Override public List<Vehicle> getSearchedVehicles(VehicleSearchFilter vehicleSearchFilter) throws PersistenceException, InvalidValueException {
     if ((vehicleSearchFilter.getEnddate() != null && vehicleSearchFilter.getStartdate() == null) || (vehicleSearchFilter.getStartdate() != null && vehicleSearchFilter.getEnddate() == null)) {
     throw new InvalidValueException("Start- or enddate: Either both or none must be given");
     }
     return DBpersistence.search(vehicleSearchFilter);
     }**/
}
