package consts;

public interface Errors {

    String NOT_FOUND_ERROR_STRING = "<html>\n" +
            "  <head>\n" +
            "    <title>404 Not Found</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <center>\n" +
            "      <h1>404 Not Found</h1>\n" +
            "    </center>\n" +
            "    <hr/>\n" +
            "    <center>nginx</center>\n" +
            "  </body>\n" +
            "</html>";

    String UNSUPPORTED_ERROR = "{\n" +
            "    \"result\": \"error\",\n" +
            "    \"error-type\": \"unsupported-code\"\n" +
            "}";

    String BAD_REQUEST_ERROR = "<html>\n" +
            "  <head>\n" +
            "    <title>400 Request Header Or Cookie Too Large</title>\n" +
            "  </head>\n" +
            "  <body bgcolor=\"white\">\n" +
            "    <center>\n" +
            "      <h1>400 Bad Request</h1>\n" +
            "    </center>\n" +
            "    <center>Request Header Or Cookie Too Large</center>\n" +
            "    <hr/>\n" +
            "    <center>cloudflare-nginx</center>\n" +
            "  </body>\n" +
            "</html>";

    String REQUEST_TOO_LARGE = "<html>\n" +
            "  <head>\n" +
            "    <title>414 Request-URI Too Large</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <center>\n" +
            "      <h1>414 Request-URI Too Large</h1>\n" +
            "    </center>\n" +
            "    <hr/>\n" +
            "    <center>cloudflare</center>\n" +
            "  </body>\n" +
            "</html>";
}
