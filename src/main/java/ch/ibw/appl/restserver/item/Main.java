package ch.ibw.appl.restserver.item;

import ch.ibw.appl.restserver.item.infra.PersonalItemSQLRepository;
import ch.ibw.appl.restserver.item.shared.infra.RestServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String port = getCLIArgument(args, "server.port", "7777");
        Boolean isTest = Boolean.parseBoolean(getCLIArgument(args, "test", "false"));
isTest = true;
        Logger logger = LoggerFactory.getLogger(PersonalItemSQLRepository.class);
        logger.info("Testmode: {}", isTest);

        new RestServer("7778", isTest).run();
    }

    public static String getCLIArgument(String[] args, String name, String defaultValue) {
        Optional<String> argument = Arrays.stream(Optional.ofNullable(args).orElse(new String[]{})).filter(a -> a.startsWith("--"+name+"=")).findFirst();
        if(argument.isPresent()) {
            return argument.get().split("=")[1];
        } else {
            return defaultValue;
        }
    }
}
