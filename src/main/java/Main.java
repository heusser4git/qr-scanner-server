import shared.infra.RestServer;
import java.util.Arrays;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        String port = getCLIArgument(args, "server.port", "7777");
        Boolean isTest = Boolean.parseBoolean(getCLIArgument(args, "test", "false"));
        System.out.println("Testmode: "+isTest);
        new RestServer(port, isTest).run();
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
