package shared.model;

public class DbConfiguration {
    private String defaultURI;
    private String database;
    private String user;
    private String password;

    public String getDefaultURI() {
        return defaultURI;
    }

    public DbConfiguration setDefaultURI(String defaultURI) {
        this.defaultURI = defaultURI;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public DbConfiguration setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getUser() {
        return user;
    }

    public DbConfiguration setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DbConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "DbConfiguration{" +
                "defaultURI='" + defaultURI + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
