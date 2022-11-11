package jakarta.common;

public class Constantes {

    //Main Paths for REST /api
    public static final String PATH_NEWSPAPERS = "/newspapers";
    public static final String PATH_RATINGS = "/ratings";
    public static final String PATH_ARTICLES = "/articles";
    public static final String PATH_READERS = "/readers";
    public static final String PATH_SUBSCRIPTIONS = "/subscriptions";
    //Auxiliar Paths for REST /api/mainPath/auxPath
    public static final String ID_PATH_PARAM = "/{id}";
    public static final String READER_PATH = "/reader";
    public static final String NEWSPAPER_PATH = "/newspaper";
    public static final String BAD_RATINGS_PATH = "/badRatings";
    public static final String TYPE_PATH = "/type";
    public static final String ARTICLE_TYPES_PATH = "/articleTypes";
    public static final String ARTICLE_TYPE_PATH = "/articleType";
    public static final String OLDEST_PATH = "/oldest";
    public static final String ID_PATH = "/id";
    //Parameters for REST
    public static final String ID = "id";
    public static final String PASSWORD = "newPass";
    public static final String ID_READER = "idReader";
    public static final String ID_NEWSPAPER = "idNewspaper";

    private Constantes() {
    }
}
