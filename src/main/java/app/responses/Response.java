package app.responses;

import java.util.Objects;

public class Response {

    private boolean ok;

    private Object data;

    /**
     * Constructor of the class.
     */
    public Response() {

        this.ok = false;
        this.data = null;

    }

    public Response(boolean ok, Object data) {
        this.ok = ok;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @SuppressWarnings("CheckStyle")
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Response response = (Response) o;
        return ok == response.ok
                && Objects.equals(data, response.data);
    }
}