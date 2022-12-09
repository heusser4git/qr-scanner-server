package item.infra;

import item.model.PersonalItem;
import item.service.PersonalItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalItemSQLRepository implements PersonalItemRepository{
    private Connection connection;
    public PersonalItemSQLRepository(boolean isTest) throws SQLException {
        if(isTest) {
            System.out.println("MySQL läuft nicht im Testmode!!!");
        } else {
            System.out.println("MySQL läuft!");
            String defaultURI = "jdbc:mysql://localhost:3306";
            String jdbcURI = System.getenv("JDBC_URI");
            if (jdbcURI == null || jdbcURI.isEmpty()) {
                jdbcURI = defaultURI;
            }
            System.out.println("JDBC URI:" + jdbcURI);

            this.connection = DriverManager.getConnection(jdbcURI + "/personal", "personal", "123456");
        }
    }
    public boolean isConnected() throws SQLException {
        return this.connection.isValid(10);
    }

    private ArrayList<PersonalItem> getPersonalItemsOfDb(PreparedStatement preparedStatement) {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getPersonalItemFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        String query = "SELECT * FROM tblPerson";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            return this.getPersonalItemsOfDb(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object add(Object object) {
        return null;
    }

    @Override
    public PersonalItem add(PersonalItem personalItem) {
        System.out.println("was kunnt do: " + personalItem);
        String query = "INSERT INTO tblPerson (name, vorname, geburtsdatum) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, personalItem.getNachname());
            preparedStatement.setString(2, personalItem.getVorname());
            preparedStatement.setDate(3, personalItem.getGeburtsdatum());
            System.out.println(preparedStatement.getWarnings());
            System.out.println(preparedStatement.executeUpdate());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                System.out.println("genID: " +resultSet.getLong(1));
                personalItem.setId(resultSet.getLong(1));
                return personalItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public PersonalItem get(Long id) {
        String query = "SELECT * FROM tblPerson WHERE id=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return this.getPersonalItemsOfDb(preparedStatement).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean update(Object object) {
        return null;
    }

    @Override
    public Boolean update(PersonalItem personalItem) {
        String query = "UPDATE tblPerson SET name=?, vorname=?, geburtsdatum=?, status=?, count=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, personalItem.getNachname());
            preparedStatement.setString(2, personalItem.getVorname());
            preparedStatement.setDate(3, personalItem.getGeburtsdatum());
            preparedStatement.setBoolean(4, personalItem.isStatus());
            preparedStatement.setInt(5,personalItem.getAnzahlEintritte());
            preparedStatement.setLong(6, personalItem.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        String query = "DELETE FROM tblPerson WHERE id=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            if(preparedStatement.executeUpdate()==1) {
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
