package shared.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import shared.model.DbConfiguration;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReadJsonFile {

    public DbConfiguration getConfig(String filepath) {
        try {
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();

            // convert JSON array to list of books
            List<DbConfiguration> dbConfigurations = Arrays.asList(mapper.readValue(Paths.get(filepath).toFile(), DbConfiguration[].class));

            // print books
            return dbConfigurations.get(0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}