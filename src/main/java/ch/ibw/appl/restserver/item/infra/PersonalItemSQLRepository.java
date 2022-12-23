package ch.ibw.appl.restserver.item.infra;

import ch.ibw.appl.restserver.item.model.PersonalItem;
import ch.ibw.appl.restserver.item.service.PersonalItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.ibw.appl.restserver.item.shared.model.DbConfiguration;
import ch.ibw.appl.restserver.item.shared.service.ReadJsonFile;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalItemSQLRepository implements PersonalItemRepository<PersonalItem> {
    public static final String COULD_NOT_CONNECT_TO_DATABASE = "Could not Connect to Database";
    public static final String SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT = "SQLException while doing a preparedStatement.";
    private Connection connection;
    private Logger logger;
    private DbConfiguration dbConfig;

    public PersonalItemSQLRepository(boolean isTest) {
        logger = LoggerFactory.getLogger(PersonalItemSQLRepository.class);
        getDbConfigurationOffJsonFile();

        if(Boolean.TRUE.equals(isTest)) {
            logger.info("MySQL läuft nicht im Testmode!!!");
        } else {
            setupMySqlConnection();
        }
    }

    private void getDbConfigurationOffJsonFile() {
        String path = new File("target/classes/META-INF/dbConfiguration.json").getAbsolutePath();
        ReadJsonFile readJsonFile = new ReadJsonFile();
        this.dbConfig = readJsonFile.getConfig(path);
    }

    private void setupMySqlConnection() {
        String defaultURI = dbConfig.getDefaultURI();
        String jdbcURI = System.getenv("JDBC_URI");
        if (jdbcURI == null || jdbcURI.isEmpty()) {
            jdbcURI = defaultURI;
        }
        try {
            this.connection = DriverManager.getConnection(String.format("%s/%s", jdbcURI, dbConfig.getDatabase()), dbConfig.getUser(), dbConfig.getPassword());
        } catch (SQLException e) {
            logger.error("SQLException while trying open Database-Connection", e);
        }
    }

    public boolean isConnected() throws SQLException {
        return this.connection.isValid(10);
    }

    private void connectDbifOffline() {
        try {
            if(!isConnected()) {
                setupMySqlConnection();
            }
        } catch (SQLException e) {
            logger.error("SQLException while checking if DB is offline.", e);
        }
    }

    private ArrayList<PersonalItem> getPersonalItemsOfDb(PreparedStatement preparedStatement) {
        connectDbifOffline();
        try(ResultSet resultSet = preparedStatement.executeQuery()) {
            return getPersonalItemFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error("SQLException while running a preparedStatement.", e);
        }
        throw new NullPointerException(COULD_NOT_CONNECT_TO_DATABASE);
    }

    private ArrayList<PersonalItem> getPersonalItemFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<PersonalItem> personalItems = new ArrayList<>();
        while (resultSet.next()) {
            PersonalItem item = new PersonalItem();
            item.setId(resultSet.getLong("id"));
            item.setNachname(resultSet.getString("name"));
            item.setVorname(resultSet.getString("vorname"));
            item.setGeburtsdatum(resultSet.getDate("geburtsdatum"));
            item.setStatus(resultSet.getBoolean("status"));
            item.setAnzahlEintritte(resultSet.getInt("count"));
            personalItems.add(item);
        }
        return personalItems;
    }


    @Override
    public List all() {
        connectDbifOffline();
        String query = "SELECT * FROM tblPerson";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query);) {
            return this.getPersonalItemsOfDb(preparedStatement);
        } catch (SQLException e) {
            logger.error(SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT, e);
        }
        throw new NullPointerException(COULD_NOT_CONNECT_TO_DATABASE);
    }

    @Override
    public PersonalItem add(PersonalItem personalItem) {
        connectDbifOffline();
        String query = "INSERT INTO tblPerson (name, vorname, geburtsdatum,status) VALUES (?,?,?,?)";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, personalItem.getNachname());
            preparedStatement.setString(2, personalItem.getVorname());
            preparedStatement.setDate(3, personalItem.getGeburtsdatum());
            preparedStatement.setBoolean(4,personalItem.isStatus());
            logger.info("{}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                personalItem.setId(resultSet.getLong(1));
                return personalItem;
            }
        } catch (SQLException e) {
            logger.error(SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT, e);
        }
        logger.info("{}", personalItem.toString());
        throw new NullPointerException(COULD_NOT_CONNECT_TO_DATABASE);
    }


    @Override
    public PersonalItem get(Long id) {
        connectDbifOffline();
        String query = "SELECT * FROM tblPerson WHERE id=?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return this.getPersonalItemsOfDb(preparedStatement).get(0);
        } catch (SQLException e) {
            logger.error(SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT, e);
        }
        throw new NullPointerException(COULD_NOT_CONNECT_TO_DATABASE);
    }


    @Override
    public Boolean update(PersonalItem personalItem) {
        connectDbifOffline();
        String query = "UPDATE tblPerson SET name=?, vorname=?, geburtsdatum=?, status=?, count=? WHERE id=?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, personalItem.getNachname());
            preparedStatement.setString(2, personalItem.getVorname());
            preparedStatement.setDate(3, personalItem.getGeburtsdatum());
            preparedStatement.setBoolean(4, personalItem.isStatus());
            preparedStatement.setInt(5,personalItem.getAnzahlEintritte());
            preparedStatement.setLong(6, personalItem.getId());
            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (SQLException e) {
            logger.error(SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT, e);
        }
        throw new NullPointerException(COULD_NOT_CONNECT_TO_DATABASE);
    }

    @Override
    public Boolean delete(Long id) {
        connectDbifOffline();
        String query = "DELETE FROM tblPerson WHERE id=?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            if(preparedStatement.executeUpdate()==1) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(SQLEXCEPTION_WHILE_DOING_A_PREPARED_STATEMENT, e);
        }
        return false;
    }
}
