package br.com.aquiaolado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class ReCaptcha3ResponseDTO {
    private Boolean success;// whether this request was a valid reCAPTCHA token for your site
    private Double score;// the score for this request (0.0 - 1.0)
    private String action;// the action name for this request (important to verify)

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime challenge_ts;// timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
    private String hostname;// the hostname of the site where the reCAPTCHA was solved
    @JsonProperty("error-codes")
    private List<String> error_codes;// optional



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getChallenge_ts() {
        return challenge_ts;
    }

    public void setChallenge_ts(LocalDateTime challenge_ts) {
        this.challenge_ts = challenge_ts;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getError_codes() {
        return error_codes;
    }

    public void setError_codes(List<String> error_codes) {
        this.error_codes = error_codes;
    }

}

