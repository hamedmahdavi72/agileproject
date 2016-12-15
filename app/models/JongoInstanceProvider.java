package models;

import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by HamedMahdavi on 12/13/2016.
 */
public class JongoInstanceProvider {
    private static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static PlayJongo getInstance() {
        return jongo;
    }

    private JongoInstanceProvider() {
    }
}
