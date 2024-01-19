package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.ConnectionDAO;
import persistence.daos.SettingsDAO;
import persistence.dtos.Connection;
import persistence.dtos.Settings;
import persistence.exceptions.PersistenceException;
import persistence.validators.TextValidator;

public class SettingsServiceImpl implements SettingsService {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final SettingsDAO FBpersistenceSettings;

    public SettingsServiceImpl(SettingsDAO FBpersistenceSettings) {
        this.FBpersistenceSettings = FBpersistenceSettings;
    }

    @Override
    public void createSettings(long user) throws PersistenceException, InterruptedException {
        this.FBpersistenceSettings.create(user);
        LOG.debug("Saved settings for user {}", user);
    }

    @Override
    public Settings getUsersSettings(long userId) throws InterruptedException {
        LOG.debug("Read all settings");
        return FBpersistenceSettings.getForUser(userId);
    }

    public void delete(long id) throws InterruptedException {
        LOG.debug("Delete settings " + id);
        FBpersistenceSettings.delete(id);
    }
}
