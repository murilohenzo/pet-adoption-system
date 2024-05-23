package br.com.murilohenzo.customstorageprovider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.*;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
//        CredentialInputUpdater,
//        UserRegistrationProvider,
        UserQueryProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);
    public static final String UNSET_PASSWORD="#$!-UNSET-PASSWORD";

    private KeycloakSession ksession;
    private ComponentModel model;


    public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model) {
        this.ksession = ksession;
        this.model = model;
    }

    @Override
    public void close() {
        log.info("[I30] close()");
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        log.info("BUSCAR USARIO PELO ID - ({})",id);
        StorageId sid = new StorageId(id);
        return getUserByUsername(realm, sid.getExternalId());
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        log.info("BUSCAR USUARIO PELO NOME DO USUARIO - ({})",username);
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select username, firstName,lastName, email, birthDate from users where username = ?");
            st.setString(1, username);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                return mapUser(realm,rs);
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        log.info("BUSCAR USARIO PELO EMAIL - ({})",email);
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select username, firstName,lastName, email, birthDate from users where email = ?");
            st.setString(1, email);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                return mapUser(realm,rs);
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        log.info("SUPORTE AO TIPO DE CREDENCIAL ({})",credentialType);
        return PasswordCredentialModel.TYPE.endsWith(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        log.info("VALIDANDO CONFIGURACAO DO TIPO DE CREDENCIAL - (realm={},user={},credentialType={})",realm.getName(), user.getUsername(), credentialType);
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        log.info("CREDENCIAIS VALIDAS (realm={},user={},credentialInput.type={})",realm.getName(), user.getUsername(), credentialInput.getType());
        if( !this.supportsCredentialType(credentialInput.getType())) {
            return false;
        }
        StorageId sid = new StorageId(user.getId());
        String username = sid.getExternalId();

        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select password from users where username = ?");
            st.setString(1, username);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                String pwd = rs.getString(1);
                return pwd.equals(credentialInput.getChallengeResponse());
            }
            else {
                return false;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    // Implementacao UserQueryProvider

    @Override
    public int getUsersCount(RealmModel realm) {
        log.info("[I93] getUsersCount: realm={}", realm.getName() );
        try ( Connection c = DbUtil.getConnection(this.model)) {
            Statement st = c.createStatement();
            st.execute("select count(*) from users");
            ResultSet rs = st.getResultSet();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        log.info("[I113] getUsers: realm={}", realm.getName());

        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select username, firstName,lastName, email, birthDate from users order by username limit ? offset ?");
            st.setInt(1, maxResults);
            st.setInt(2, firstResult);
            st.execute();
            ResultSet rs = st.getResultSet();
            List<UserModel> users = new ArrayList<>();
            while(rs.next()) {
                users.add(mapUser(realm,rs));
            }
            return users.stream();
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        log.info("BUSCANDO USUARIO: realm={}", realm.getName());
        try (Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select username, firstName,lastName, email, birthDate from users where username like ? order by username limit ? offset ?");
            st.setString(1, search);
            st.setInt(2, maxResults);
            st.setInt(3, firstResult);
            st.execute();
            ResultSet rs = st.getResultSet();
            List<UserModel> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapUser(realm, rs));
            }
            return users.stream();
        } catch (SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(), ex);
        }
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return getGroupMembersStream(realm, null, firstResult, maxResults);
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.empty();
    }

    private UserModel mapUser(RealmModel realm, ResultSet rs) throws SQLException {
        CustomUser user = new CustomUser.Builder(ksession, realm, model, rs.getString("username"))
                .email(rs.getString("email"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .birthDate(rs.getDate("birthDate"))
                .build();

        return user;
    }

    // Implementação do CredentialInputUpdater


//    @Override
//    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput credentialInput) {
//        log.info("ATUALIZANDO CREDENCIAL:{}", credentialInput.getType());
//        if (credentialInput.getType().equals(CredentialModel.PASSWORD)) {
//            log.info("TIPO DE VALIDACAO: {}", credentialInput.getType());
//            try (Connection c = DbUtil.getConnection(this.model)) {
//                PreparedStatement st = c.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
//                st.setString(1, credentialInput.getChallengeResponse());
//                st.setString(2, user.getUsername());
//                log.info("NOVA SENHA: {}", credentialInput.getChallengeResponse());
//                log.info("USUARIO:{}", user.getUsername());
//                int rowsUpdated = st.executeUpdate();
//                CredentialProvider passwordProvider = ksession.getProvider(CredentialProvider.class, PasswordCredentialProviderFactory.PROVIDER_ID);
//                if (passwordProvider instanceof CredentialInputUpdater) {
//                    ((CredentialInputUpdater) passwordProvider).updateCredential(realm, user, credentialInput);
//                }
//                return rowsUpdated > 0;
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erro no banco de dados: " + ex.getMessage(), ex);
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
//        if (credentialType.equals(CredentialModel.PASSWORD)) {
//            try (Connection c = DbUtil.getConnection(this.model)) {
//                PreparedStatement st = c.prepareStatement("update users set password = null where username = ?");
//                st.setString(1, user.getUsername());
//                st.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RuntimeException("Database error: " + ex.getMessage(), ex);
//            }
//        }
//    }
//
//    @Override
//    public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
//        return Stream.of(CredentialModel.PASSWORD);
//    }

    // Implementação do UserRegistrationProvider

//    @Override
//    public UserModel addUser(RealmModel realm, String username) {
//        log.info("ADICIONANDO NOVO USUARIO: {}", username);
//
//        log.info("ATRIBUTOS: {}", realm.getAttributes());
//
//        // Adicionar o usuário ao banco de dados externo
//        try (Connection c = DbUtil.getConnection(this.model)) {
//            log.info("GRAVANDO USUARIO NO BANCO EXTERNO");
//            PreparedStatement st = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
//            st.setString(1, username);
//            st.setString(2, UNSET_PASSWORD);
//            st.executeUpdate();
//        } catch (SQLException ex) {
//            throw new RuntimeException("Database error:" + ex.getMessage(), ex);
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean removeUser(RealmModel realm, UserModel user) {
//        log.info("REMOVENDO USUSARIO: {}", user.getUsername());
//        try (Connection c = DbUtil.getConnection(this.model)) {
//            PreparedStatement st = c.prepareStatement("DELETE FROM users WHERE username = ?");
//            st.setString(1, user.getUsername());
//            int rowsDeleted = st.executeUpdate();
//
//            if (rowsDeleted > 0) {
//                return true;
//            }
//            return false;
//        } catch (SQLException ex) {
//            throw new RuntimeException("Database error:" + ex.getMessage(), ex);
//        }
//    }


}