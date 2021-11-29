package fpt.toeic.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final Integer IS_SHOW = 1;
    public static final String RESULT_VALIDATE_MSG = "RESULT_VALIDATE_MSG";

    public static final String ACTOR = "actor";
    public static final char DEFAULT_ESCAPE_CHAR = '&';
    public static final char DEFAULT_ESCAPE_CHAR_QUERY = '\\';
    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String URL_PART_FALSE = "url.part.false";
    public static final String GOOGLE_RECAPTCHA_FALSE = "google.recaptcha.false";
    public static final String DETAIL = "detail";
    public static final String URL_PART = "url.part";
    public static final String LOGIN_FALSE = "login.pass.false";
    public static final String TOKEN_ERROR = "token.error";

    public static final String LOGIN_USER_REQUIRED = "login.user.required";
    public static final String LOGIN_PASS_REQUIRED = "login.pass.required";
    public static final String LOGIN_PASS_MIN = "login.pass.min";
    public static final String LOGIN_PASS_MAX = "login.pass.max";
    public static final String LOGIN_PASS_OLL_REQUIRED = "login.pass.old.required";
    public static final String LOGIN_PASS_OLL_MIN = "login.pass.old.min";
    public static final String LOGIN_PASS_OLL_MAX = "login.pass.old.max";
    public static final String LOGIN_PASS_COM_REQUIRED = "login.pass.com.required";
    public static final String LOGIN_PASS_COM_MIN = "login.pass.com.min";
    public static final String LOGIN_PASS_COM_MAX = "login.pass.com.max";
    public static final String LOGIN_EMAIL_FALSE = "login.email.false";
    public static final String LOGIN_PASS_CHANGE_FALSE = "login.pass.change.false";
    public static final String LOGIN_PASS_OLD_SS_FALSE = "login.pass.old.ss.false";
    public static final String LOGIN_PASS_NEW_SS_FALSE = "login.pass.new.ss.false";
    public static final String CHANGE_PASS_SUS = "change.pass.sus";
    public static final String CHANGE_PASS_FALSE = "change.pass.false";
    public static final String CHANGE_NAME_FALSE = "change.name.false";
    public static final String LOGIN_NAME_FALSE = "login.name.false";
    public static final String EMAIL_CONTENT1 = "email.conten1";
    public static final String ORDER_CONTENT1 = "order.conten1";
    public static final String EMAIL_CONTENT2 = "email.conten2";
    public static final String EMAIL_SUBJECT = "email.subject";
    public static final String EMAIL_SUBJECT_LOGIN = "email.subject.login";
    public static final String EMAIL_SUBJECT1 = "email.subject1";
    public static final String EMAIL_PATH = "email.path";
    public static final String EMAIL_FALSE = "email.false";
    public static final String EMAIL_TRUE = "email.true";
    public static final String EMAIL_CONTENT3 = "email.content3";
    public static final String EMAIL_START = "email.hello";
    public static final boolean IS_MULTI_PART = true;
    public static final boolean IS_HTML = true;
    // he dieu hanh win
    //public static final String URL_FILE_UPLOAD = "C:\\Users\\hieutt1\\IdeaProjects\\toeic-web-fe\\toeic_web";
    public static final String URL_FILE_UPLOAD = "/Users/kimnguyen/toeic_web";
    // he dieu hanh mac
    public static final String RECAPTCHA_SECRET_KEY = "6Ldch8gZAAAAACQWBmqfe1R2HFp0N0LlH4J5Row0";
    public static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public interface CONTENT_TYPE {
        String JPG = "images/jpg";
        String JPEG = "image/jpeg";
        String PNG = "image/png";

        String MOV = "video/quicktime";
        String MP4 = "video/mp4";
        String AVI = "video/x-msvideo";
        String FLV = "video/x-flv";
        String MPEG = "video/mpeg";

        String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        String XLS = "application/vnd.ms-excel";
        String DOC = "application/msword";
        String PPT = "application/vnd.ms-powerpoint";
        String PDF = "application/pdf";
    }
    private Constants() {
    }

    public interface DATA_TYPE {
        String STRING = "STRING";
        String DATE = "DATE";
        String LONG = "LONG";
        String DOUBLE = "DOUBLE";
        String INT = "INT";
        String BIGINT = "BIGINT";
    }

    public interface FILE_IMPORT {
        int START_COL = 0;
        int START_ROW = 3;
    }
}
