package de.haproxyhq.security.token;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.haproxyhq.security.encryption.EncryptionUtil;

/**
*
* @author Johannes Hiemer.
*
*/
@Component
public class TokenUtil {

   private static final Logger LOG = LoggerFactory.getLogger(TokenUtil.class);

   private EncryptionUtil encryptionUtil = new EncryptionUtil();

   private Period sessionMaxAge;

   @Value("${auth.token.name}")
   private String headerName;

   @Value("${auth.session.maxAge}")
   private String maxAge;

   @Value("${auth.encryption.enabled}")
   private String encryptionEnabled;

   @Value("${auth.encryption.seed}")
   private String seed;

   @PostConstruct
   private void init() {
       if (StringUtils.isNotBlank(encryptionEnabled) && Boolean.parseBoolean(encryptionEnabled)) {
           encryptionUtil.encryptionEnabled(true);
       }
       sessionMaxAge = getSessionMaxAge();
   }

   public String getUserName(HttpServletRequest request) {
       String header = request.getHeader(headerName);
       return StringUtils.isNotBlank(header) ? extractUserName(header) : null;
   }

   private String extractUserName(String value) {

       try {
           String decryptedValue = encryptionUtil.decrypt(value, seed);
           String[] split = decryptedValue.split("\\|");
           String username = split[0];
           DateTime timestamp =  new DateTime(Long.parseLong(split[1]));
           if (timestamp.isEqual(new DateTime(0)) || timestamp.isAfter(DateTime.now().minus(sessionMaxAge))) {
               return username;
           }
       } catch (IOException | GeneralSecurityException e) {
           LOG.debug("Unable to decrypt header", e);
       }
       return null;
   }

   public void addHeader(HttpServletResponse response, String userName) {
       try {
           String encryptedValue = createAuthToken(userName);
           response.setHeader(headerName, encryptedValue);
       } catch (IOException | GeneralSecurityException e) {
           LOG.error("Unable to encrypt header", e);
       }
   }

   public String createAuthToken(String userName) throws IOException, GeneralSecurityException {
       String value = userName + "|" + System.currentTimeMillis();
       return encryptionUtil.encrypt(value, seed);
   }
   
   public String createInfiniteAuthToken(String prefix) throws IOException, GeneralSecurityException {
	   String value = prefix + "|0";
	   return encryptionUtil.encrypt(value, seed);
   }

   private Period getSessionMaxAge() {
       PeriodFormatter format = new PeriodFormatterBuilder()
               .appendDays()
               .appendSuffix("d", "d")
               .printZeroRarelyFirst()
               .appendHours()
               .appendSuffix("h", "h")
               .printZeroRarelyFirst()
               .appendMinutes()
               .appendSuffix("m", "m")
               .toFormatter();
       Period sessionMaxAge = format.parsePeriod(maxAge);
       if (LOG.isDebugEnabled()) {
           LOG.debug("Session maxAge is: "+
                   formatIfNotZero(sessionMaxAge.getDays(), "days", "day") +
                   formatIfNotZero(sessionMaxAge.getHours(), "hours", "hour") +
                   formatIfNotZero(sessionMaxAge.getMinutes(), "minutes", "minute")
           );
       }
       return sessionMaxAge;
   }

   private static String formatIfNotZero(int value, String plural, String singleton) {
       if (value > 0) {
           if (value > 1) {
               return "" + value + " " + plural;
           }
           return "" + value + " " + singleton;
       }
       return "";
   }

}
