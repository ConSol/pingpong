package de.consol.dus.pong.config.jms;

import de.consol.dus.pong.game.Game;
import java.util.Map;
import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class JmsGameTemplateConfig {

  public static final String JMS_GAME_TEMPLATE = "jmsGameTemplate";

  @Bean(name = JMS_GAME_TEMPLATE)
  @ApplicationScope
  public JmsTemplate jmsGameTemplate(ConnectionFactory connectionFactory) {
    final JmsTemplate jmsGameTemplate = new JmsTemplate(connectionFactory);
    jmsGameTemplate.setPubSubDomain(true);
    final MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setTargetType(MessageType.TEXT);
    messageConverter.setTypeIdPropertyName("__type");
    messageConverter.setTypeIdMappings(Map.of("Game", Game.class));
    jmsGameTemplate.setMessageConverter(messageConverter);
    return jmsGameTemplate;
  }
}
