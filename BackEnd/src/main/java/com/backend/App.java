package com.backend;

import com.backend.controllers.*;
import com.backend.services.*;
import com.backend.database.SQLDBRepository;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.function.Consumer;

public class App {

    private static final Logger logger = LogManager.getLogger();

    private static final String PORTA_SERVIDOR = "porta.servidor";
    private static final String SQL_URL = "sqldb.url";
    private static final String USERNAME = "sqldb.username";
    private static final String PASSWORD = "sqldb.password";

    private final Properties properties;
    private Connection connection;

    public App() {this.properties = getProperties();}

    public void iniciar() {
        Javalin app = iniciarJavalin();
        configurarRotas(app);

        app.exception(Exception.class, (e, ctx) ->{
            logger.error("Erro não tratado {}",e);
            ctx.status(500);
        });
    }

    private void registrarServicos(JavalinConfig config, Connection connection) {
        config.appData(Keys.USUARIO_SERVICE.key(), new UsuarioService(connection));
        config.appData(Keys.AMIZADE_SERVICE.key(), new RelacaoService(connection));
        config.appData(Keys.POSTAGEM_SERVICE.key(), new PostagemService(connection));
        config.appData(Keys.CURTIDA_SERVICE.key(), new CurtidaService(connection));
        config.appData(Keys.NOTIFICATION_SERVICE.key(), new NotificationService(connection));
    }

    private Javalin iniciarJavalin() {
        int porta = getPortaServidor();
        logger.info("Iniciando aplicação na porta {}", porta);
        Consumer<JavalinConfig> configConsumer = this::configureJavalin;
        return Javalin.create(configConsumer).start(porta);
    }

    private void configureJavalin(JavalinConfig config) {

        config.events(event -> {
           event.serverStarted(() -> {
               connection = iniciarSQLRepository();
               config.appData(Keys.SQL_DB.key(), connection);
               registrarServicos(config, connection);
           });
           event.serverStopped(() -> {
               if (connection == null) {
                   logger.error("A conexão não devia estar nula ao encerrar a aplicação");
               } else {
                   connection.close();
                   logger.info("Conexão encerrada com sucesso");
               }
           });
        });
        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
    }

    private Connection iniciarSQLRepository() {
        String sqlURL = properties.getProperty(SQL_URL);
        String username = properties.getProperty(USERNAME);
        String password = properties.getProperty(PASSWORD);
        if (sqlURL == null || username == null || password == null) {
            logger.error("Defina os parâmetros para conexão no application.properties");
            System.exit(1);
        }
        logger.info("Conectando ao banco de dados SQL");
        Connection connection = SQLDBRepository.getConnection(sqlURL, username, password);
        if (connection == null) {
            logger.error("Não foi possível recuperar a conexão com o banco de dados");
            System.exit(1);
        }
        return connection;
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


        //USUARIOS
        app.get("/usuarios", UsuarioController::buscarUsuario);
        app.patch("/usuarios/{id}", UsuarioController::atualizarDadosPerfil);
        app.get("/usuarios/search", UsuarioController::buscarUsuariosPesquisados);
        app.patch("/img/usuarios/{id}", UsuarioController::atualizarFoto);


        // AUTH
        app.post("/login", AuthController::authenticateUser);
        app.post("/register", AuthController::registerUser);

        //AMIZADE
        app.post("/relacao", RelacaoController::criarRelacao);
        app.get("/relacao", RelacaoController::buscarRelacao);
        app.delete("/relacao/{id}", RelacaoController::removerRelacao);
        app.patch("/relacao/{id}", RelacaoController::aceitarRelacao);
        app.get("/relacao/compartilhado", RelacaoController::buscarRelacoesEmComum);

        // POSTAGEM
        app.post("/postagem", PostagemController::criarPostagem);
        app.get("/postagem", PostagemController::buscarPostagens);
        app.get("/postagem/{id}", PostagemController::buscarPostagemPorId);
        app.delete("/postagem/{id}", PostagemController::excluirPostagem);

        // CURTIDA
        app.post("/curtida", CurtidaController::inserirCurtida);
        app.get("/curtida", CurtidaController::buscarCurtida);
        app.delete("/curtida/{id}", CurtidaController::removerCurtida);
        app.get("/curtida/numero/{id_conteudo}", CurtidaController::numeroCurtidasPostagem);

        //IMAGEM
        app.get("/img/{id_usuario}/{id_imagem}", ImagemController::buscarImagem);

        //NOTIFICATION
        app.get("/notification", NotificationController::getUserNotifications);
        app.post("/notification", NotificationController::createNotification);
        app.patch("/notification/{id_receiver}", NotificationController::viewNotifications);

        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "http://localhost:5173");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        app.options("/*", ctx -> {
           ctx.header("Access-Control-Allow-Origin", "http://localhost:5173");
           ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
           ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
           ctx.status(200);
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