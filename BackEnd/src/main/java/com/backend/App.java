package com.backend;

import com.backend.database.MongoDBRepository;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class App {

    private static final Logger logger = LogManager.getLogger();
    private static final String PORTA_SERVIDOR = "porta.servidor";
    private static final String CONNECTION_STRING = "mongodb.connectionString";

    private final Properties properties;
    private MongoDBRepository mongoDBRepository;

    public App() {this.properties = getProperties();}

    public void iniciar() {
        Javalin app = iniciarJavalin();
        configurarRotas(app);

        app.exception(Exception.class, (e, ctx) ->{
            logger.error("Erro não tratado {}",e);
            ctx.status(500);
        });
    }

    private void registrarServicos(JavalinConfig config, MongoDBRepository mongoDBRepository) {

    }

    private Javalin iniciarJavalin() {
        int porta = getPortaServidor();
        logger.info("Iniciando aplicação na porta {}", porta);
        Consumer<JavalinConfig> configConsumer = this::configureJavalin;
        return Javalin.create(configConsumer).start(porta);
    }

    private void configureJavalin(JavalinConfig config) {
        TemplateEngine templateEngine = configurarThymeleaf();

        config.events(event -> {
           event.serverStarted(() -> {
               mongoDBRepository = iniciarMongoRepository();
               config.appData(Keys.MONGO_DB.key(), mongoDBRepository);
           });
           event.serverStopped(() -> {
               if (mongoDBRepository == null) {
                   logger.error("A conexão não devia estar nula ao encerrar a aplicação");
               } else {
                   mongoDBRepository.close();
                   logger.info("Conexão encerrada com sucesso");
               }
           });
        });
        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        config.fileRenderer(new JavalinThymeleaf(templateEngine));
    }

    private MongoDBRepository iniciarMongoRepository() {
        String connectionString = properties.getProperty(CONNECTION_STRING);
        logger.info("Recuperando String de acesso ao mongo");
        if (connectionString == null) {
            logger.error("String de conexão não foi definida no arquivo application.properties");
            System.exit(1);
        }
        logger.info("Conectando ao mongo");
        MongoDBRepository mongoDBRepository = new MongoDBRepository(connectionString);
        if (mongoDBRepository.conectado("config")) {
            logger.info("Conexão realizada com sucesso");
        } else {
            logger.error("Falha ao realizar a conexão com o mongo");
            System.exit(1);
        }
        return mongoDBRepository;
    }

    private TemplateEngine configurarThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private int getPortaServidor() {
        int porta = 8000;
        if (properties.containsKey(PORTA_SERVIDOR)) {
            try {
                return Integer.parseInt(properties.getProperty(PORTA_SERVIDOR));
            } catch (NumberFormatException exception) {
                logger.error("A porta do servidor não é um valor válido ", exception);
                System.exit(1);
            }
        } else {
            logger.error("Porta não definida no arquivo application.properties");
        }
        return porta;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Arquivo application.properties não encontrado");
                System.exit(1);
            }
            props.load(input);
        } catch (IOException exception) {
            logger.error("Erro ao carregar o arquivo de propriedades /src/main/resources/application.properties", exception);
            System.exit(1);
        }
        return props;
    }

    private void configurarRotas(Javalin app) {
        app.get("/", ctx -> {
            ctx.result("Estou funcionando");
        });
    }

    public static void main(String[] args) {
        try {
            new App().iniciar();
        } catch (Exception exception) {
            logger.error("Erro ao iniciar a aplicação ", exception);
            System.exit(1);
        }
    }
}