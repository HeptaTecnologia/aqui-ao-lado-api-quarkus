package br.com.aquiaolado.api;

import br.com.aquiaolado.dto.ReCaptcha3ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReCaptcha3Api {

    private ReCaptcha3Api() {
    }

    @ConfigProperty(name = "api.key.recaptcha")
    private static String secret;

    public static ReCaptcha3ResponseDTO check(String token) throws Exception {

        HttpPost post = new HttpPost("https://www.google.com/recaptcha/api/siteverify");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("secret", secret));
        urlParameters.add(new BasicNameValuePair("response", token));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            String resultado = EntityUtils.toString(response.getEntity());
            JSONObject obj = new JSONObject(resultado);


            ObjectMapper mapper = new ObjectMapper();

            ReCaptcha3ResponseDTO reCaptcha3ResponseDTO = mapper.readValue(obj.toString(), ReCaptcha3ResponseDTO.class);
            return reCaptcha3ResponseDTO;
        }
    }
}
