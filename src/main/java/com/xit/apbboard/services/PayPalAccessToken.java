package com.xit.apbboard.services;

import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;

import java.util.Properties;

/**
 * Created by
 *
 * @author homer
 * @since 19.09.14.
 */
public class PayPalAccessToken {
    public static PayPalAccessToken tokenGenerator = new PayPalAccessToken();

    private PayPalAccessToken(){
        Properties prop = new Properties();
        prop.put("service.EndPoint", "https://api.sandbox.paypal.com");
        prop.put("http.ConnectionTimeOut", "5000");
        prop.put("http.Retry", "1");
        prop.put("http.ReadTimeOut", "30000");
        prop.put("http.MaxConnection", "100");
        prop.put("http.UseProxy", "false");
        prop.put("http.GoogleAppEngine", "false");
        PayPalResource.initConfig(prop);
    }

    public String generateToken() throws PayPalRESTException{
        OAuthTokenCredential tokenCredential = new OAuthTokenCredential("AQEs6xCpYIYfdgMPalU6ZNbA217WAYUzmDygj7ZmglidF_AdTvMh8XD1x-wA",
                "EHcxsBCpFcOo0M7ioPhsIn25CN3aNCm0Mz6496w-LDGQzRjGKmMEphXKRwa6");

        return tokenCredential.getAccessToken();

    }
}
