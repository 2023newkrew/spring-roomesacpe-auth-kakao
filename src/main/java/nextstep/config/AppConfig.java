package nextstep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
    private static Environment env;

    public AppConfig(Environment env) {
        AppConfig.env = env;
    }

    public static String getSecretKey(){
        return env.getProperty("jwt.secret");
    }

    public static String getValidTokenTime(){
        return env.getProperty("jwt.validateMilliSeconds");
    }

}
