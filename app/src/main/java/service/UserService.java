package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import persistence.dtos.User;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;

public interface UserService {

    long saveUser(User user) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException, InterruptedException;

    void delete(List<User> users) throws PersistenceException;

    List<User> getAllUsers() throws PersistenceException, InterruptedException;

    boolean update(User user) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException;

    User loginUser(String username, String password) throws InvalidValueException, MandatoryValueException, IOException, PersistenceException, NoSuchAlgorithmException, InterruptedException;

}
