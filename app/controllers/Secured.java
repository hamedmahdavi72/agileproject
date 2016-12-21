package controllers;


import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by HamedMahdavi on 12/20/2016.
 */
public class Secured extends Security.Authenticator {


    @Override
    public String getUsername(Context ctx) {
        return SessionIdPool.getUsername(getSessionIdFromSession(ctx));
    }


    @Override
    public Result onUnauthorized(Context context) {
        return redirect(routes.UserRequest.loginController());
    }


    private String getSessionIdFromSession(Context ctx) {
        return ctx.session().get("sessionId");
    }


    public static boolean isLoggedIn(Session session) {
        String sessionId = session.get("sessionId");
        return (SessionIdPool.getUsername(sessionId) != null);
    }


}


