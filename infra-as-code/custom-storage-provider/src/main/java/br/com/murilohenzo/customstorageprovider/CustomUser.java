package br.com.murilohenzo.customstorageprovider;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUser extends AbstractUserAdapterFederatedStorage {

    private static final Logger log = LoggerFactory.getLogger(CustomUser.class);

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public CustomUser(
            KeycloakSession session,
            RealmModel realm,
            ComponentModel storageProviderModel,
            String username,
            String email,
            String firstName,
            String lastName,
            Date birthDate) {
        super(session, realm, storageProviderModel);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setBirthDate(birthDate);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        setSingleAttribute(UserModel.USERNAME, username);
    }

    @Override
    public String getFirstName() {
        return getAttributeOrDefault(UserModel.FIRST_NAME, firstName);
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setSingleAttribute(UserModel.FIRST_NAME, firstName);
    }

    @Override
    public String getLastName() {
        return getAttributeOrDefault(UserModel.LAST_NAME, lastName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
        setSingleAttribute(UserModel.LAST_NAME, lastName);
    }

    @Override
    public String getEmail() {
        return getAttributeOrDefault(UserModel.EMAIL, email);
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
        setSingleAttribute(UserModel.EMAIL, email);
    }

    public Date getBirthDate() {
        String birthDateString = getFirstAttribute("birthDate");
        if (birthDateString != null) {
            return new Date(Long.parseLong(birthDateString));
        }
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        setSingleAttribute("birthDate", String.valueOf(birthDate.getTime()));
    }

    private String getAttributeOrDefault(String attributeName, String defaultValue) {
        String attributeValue = getFirstAttribute(attributeName);
        return attributeValue != null ? attributeValue : defaultValue;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put(UserModel.USERNAME, List.of(getUsername()));
        attributes.put(UserModel.EMAIL, List.of(getEmail()));
        attributes.put(UserModel.FIRST_NAME, List.of(getFirstName()));
        attributes.put(UserModel.LAST_NAME, List.of(getLastName()));
        attributes.put("birthDate", List.of(String.valueOf(getBirthDate().getTime())));
        return attributes;
    }

    public static class Builder {
        private final KeycloakSession session;
        private final RealmModel realm;
        private final ComponentModel storageProviderModel;
        private final String username;
        private String email;
        private String firstName;
        private String lastName;
        private Date birthDate;

        public Builder(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, String username) {
            this.session = session;
            this.realm = realm;
            this.storageProviderModel = storageProviderModel;
            this.username = username;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public CustomUser build() {
            return new CustomUser(session, realm, storageProviderModel, username, email, firstName, lastName, birthDate);
        }
    }
}
