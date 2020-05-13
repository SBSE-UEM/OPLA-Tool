package br.ufpr.dinf.gres.api;

import br.ufpr.dinf.gres.architecture.io.FileUtils;
import br.ufpr.dinf.gres.architecture.config.ApplicationFile;
import br.ufpr.dinf.gres.architecture.config.ManagerApplicationConfig;
import br.ufpr.dinf.gres.architecture.util.Constants;
import br.ufpr.dinf.gres.architecture.util.UserHome;
import br.ufpr.dinf.gres.architecture.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
//@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan(basePackages = {
        "br.ufpr.dinf.gres.domain.entity",
        "br.ufpr.dinf.gres.domain.entity.objectivefunctions"
})
@EnableJpaRepositories(basePackages = {
        "br.ufpr.dinf.gres.persistence.repository"
})
@ComponentScan(basePackages = {
        "br.ufpr.dinf.gres.api.resource",
        "br.ufpr.dinf.gres.api.gateway",
        "br.ufpr.dinf.gres.persistence.service",
        "br.ufpr.dinf.gres.core.jmetal4.experiments",
        "br.ufpr.dinf.gres.core.persistence",
        "br.ufpr.dinf.gres.core.jmetal4.metrics"
})
@EnableAsync
public class OplaApiApplication {

    private final Environment env;

    public OplaApiApplication(Environment env) {
        this.env = env;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    public static void main(String[] args) {
        try {
            if (OplaApiApplication.class.getResource("").openConnection() instanceof JarURLConnection) {
                Constants.BASE_RESOURCES = "BOOT-INF/classes/";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.createPathsOplaTool();
        initialConfiguration();
        ManagerApplicationConfig instance = ApplicationFile.getInstance();
        try {
            instance.configureDefaultLocaleToExportModels();
            instance.updateDefaultPathToSaveModels();
            instance.updateDefaultPathToTemplateFiles();
            copyTemplates();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        SpringApplication.run(OplaApiApplication.class, args);
    }

    private static void initialConfiguration() {
        createPathOplaTool();
        configureApplicationFile();
        configureDb();
    }

    public static void copyTemplates() throws URISyntaxException {
        URI uriTemplatesDir = ClassLoader.getSystemResource(Constants.BASE_RESOURCES + Constants.TEMPLATES_DIR).toURI();
        String simplesUmlPath = Constants.SIMPLES_UML_NAME;
        String simplesDiPath = Constants.SIMPLES_DI_NAME;
        String simplesNotationPath = Constants.SIMPLES_NOTATION_NAME;

        Path externalPathSimplesUml = Paths.get(UserHome.getPathToTemplates() + simplesUmlPath);
        Path externalPathSimplesDi = Paths.get(UserHome.getPathToTemplates() + simplesDiPath);
        Path externalPathSimplesNotation = Paths.get(UserHome.getPathToTemplates() + simplesNotationPath);

        FileUtils.copy(Paths.get(uriTemplatesDir.getSchemeSpecificPart()).resolve(simplesUmlPath), externalPathSimplesUml);
        FileUtils.copy(Paths.get(uriTemplatesDir.getSchemeSpecificPart()).resolve(simplesDiPath), externalPathSimplesDi);
        FileUtils.copy(Paths.get(uriTemplatesDir.getSchemeSpecificPart()).resolve(simplesNotationPath), externalPathSimplesNotation);
    }

    /**
     * Cria diretório raiz da ferramentas
     */
    private static void createPathOplaTool() {
        UserHome.createDefaultOplaPathIfDontExists();
    }

    private static void configureApplicationFile() {
        ApplicationFile.getInstance();
    }

    /**
     * Somente faz uma copia do banco de dados vazio para a pasta da oplatool no
     * diretorio do usuario se o mesmo nao existir.
     *
     * @throws Exception
     */
    private static void configureDb() {
        Utils.createDataBaseIfNotExists();
    }


}