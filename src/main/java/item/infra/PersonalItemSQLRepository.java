package item.infra;

import item.model.PersonalItem;
import item.service.PersonalItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalItemSQLRepository implements PersonalItemRepository{
    private Connection connection;
    private Logger logger;
    public PersonalItemSQLRepository(boolean isTest) throws SQLException {
        if(Boolean.TRUE.equals(isTest)) {
            System.out.println("MySQL läuft nicht im Testmode!!!");
        } else {
            setupMySqlConnection();
        }
        logger = LoggerFactory.getLogger(PersonalItemSQLRepository.class);
    }

    private void setupMySqlConnection() {
        String defaultURI = "jdbc:mysql://localhost:3306";
        String jdbcURI = System.getenv("JDBC_URI");
        if (jdbcURI == null || jdbcURI.isEmpty()) {
            jdbcURI = defaultURI;
        }
        System.out.println("JDBC URI:" + jdbcURI);
        try {
            this.connection = DriverManager.getConnection(jdbcURI + "/personal", "personal", "123456");
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
            e.printStackTrace();
        }
        throw new NullPointerException("Could not Connect to Database");
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
            logger.error("SQLException while doing a preparedStatement.", e);
        }
        throw new NullPointerException("Could not Connect to Database");
    }

    @Override
    public Object add(Object object) {
        return null;
    }

    @Override
    public PersonalItem add(PersonalItem personalItem) {
        connectDbifOffline();
        String query = "INSERT INTO tblPerson (name, vorname, geburtsdatum) VALUES (?,?,?)";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, personalItem.getNachname());
            preparedStatement.setString(2, personalItem.getVorname());
            preparedStatement.setDate(3, personalItem.getGeburtsdatum());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                personalItem.setId(resultSet.getLong(1));
                return personalItem;
            }
        } catch (SQLException e) {
            logger.error("SQLException while doing a preparedStatement.", e);
        }
        throw new NullPointerException("Could not Connect to Database");
    }


    @Override
    public PersonalItem get(Long id) {
        connectDbifOffline();
        String query = "SELECT * FROM tblPerson WHERE id=?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return this.getPersonalItemsOfDb(preparedStatement).get(0);
        } catch (SQLException e) {
            logger.error("SQLException while doing a preparedStatement.", e);
        }
        throw new NullPointerException("Could not Connect to Database");
    }

    @Override
    public Boolean update(Object object) {
        return false;
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
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException while doing a preparedStatement.", e);
        }
        throw new NullPointerException("Could not Connect to Database");
    }

    @Override
    public Boolean delete(Long id) {
        connectDbifOffline();
        String query = "DELETE FROM tblPerson WHERE id=?";
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            if(preparedStatement.executeUpdate()==1) {
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException while doing a preparedStatement.", e);
        }
        return false;
    }
}
