package configuration;

import configuration.common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Singleton
@Getter
@Log4j2
public class Configuration {

    private String url;
    private String user;
    private String password;
    private String driver;

    public Configuration() {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> propertiesMap = yaml.load(getClass().getClassLoader().getResourceAsStream(Constantes.CONFIG_YAML_PATH));
            this.url = (String) propertiesMap.get(Constantes.URL);
            this.password = (String) propertiesMap.get(Constantes.PASSWORD);
            this.user = (String) propertiesMap.get(Constantes.USER);
            this.driver = (String) propertiesMap.get(Constantes.DRIVER);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
