package org.capedwarf.phoner.server.mvc;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Response {
    static final Response OK = new Response("OK");
    static final Response FAILURE = new Response("FAILURE");

    private String result;

    public Response() {
    }

    public Response(Object result) {
        this.result = String.valueOf(result);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
