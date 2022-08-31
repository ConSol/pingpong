package de.consol.dus.pong.boundary.messaging.producer.jms;

import de.consol.dus.pong.config.jms.JmsGameTemplateConfig;
import de.consol.dus.pong.game.Game;
import java.time.Instant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Producer {
  private static final String MESSAGES_TOPIC = "messages";

  private final JmsTemplate jmsTemplate;

  public Producer(@Qualifier(JmsGameTemplateConfig.JMS_GAME_TEMPLATE) JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }


  public void send(Game game) {
    jmsTemplate.setPubSubDomain(true);
    log.info("Sending message [{}] to jms-topic [{}]", game, MESSAGES_TOPIC);
    jmsTemplate.convertAndSend(MESSAGES_TOPIC, game, message -> {
      message.setLongProperty("CREATION_TIME", Instant.now().toEpochMilli());
      return message;
    });
  }
}
