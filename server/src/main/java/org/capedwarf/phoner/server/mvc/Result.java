package org.capedwarf.phoner.server.mvc;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class Result {
    static final Result OK = new Result("OK");
    static final Result FAILURE = new Result("FAILURE");

    private String status;

    public Result() {
    }

    public Result(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
