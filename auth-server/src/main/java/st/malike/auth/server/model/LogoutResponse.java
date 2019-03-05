package st.malike.auth.server.model;


import lombok.Data;

@Data
public class LogoutResponse {

    private boolean isSuccess;

    private String errorCode;

    private String errorMsg;
}
