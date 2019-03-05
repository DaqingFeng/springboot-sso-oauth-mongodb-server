package st.malike.auth.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;
import st.malike.auth.server.model.LogoutResponse;
import st.malike.auth.server.service.security.TokenStoreService;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"Token管理服务"})
@RestController
public class OAuthController {

    @Autowired
    private TokenStoreService tokenStore;

    @ApiOperation(value = "移除调用凭证", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "凭证", required = true, dataType = "string", paramType = "header")
    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LogoutResponse logout(HttpServletRequest request) {
        LogoutResponse response = new LogoutResponse();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            try {
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                response.setErrorCode("000");
                tokenStore.removeAccessToken(accessToken);
                response.setSuccess(true);
            } catch (Exception ex) {
                response.setErrorCode("001");
                response.setErrorMsg("can't get authorization information.");
                response.setSuccess(false);
            }
        } else {
            response.setErrorCode("001");
            response.setErrorMsg("none of authorization information.");
            response.setSuccess(false);
        }
        return response;
    }

}
