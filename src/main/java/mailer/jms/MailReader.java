package mailer.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import mailer.domain.pojo.Email;
import mailer.service.MailServise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailReader {

    @Autowired
    private MailServise mailServise;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MailReader.class);

    @JmsListener(destination = "mailBox"   , containerFactory = "myFactory")
    public void receiveMessage(String message) {
        LOGGER.trace("Получено новое сообщение");
        Email email;
        ObjectMapper mapper = new ObjectMapper();
        try {
            email = mapper.readValue(message,Email.class);
            mailServise.send(email);
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }

    }


}
