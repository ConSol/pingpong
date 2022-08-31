package de.consol.dus.ping.config.spring;

import de.consol.dus.ping.game.Game;
import java.util.Map;
import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class JmsContainerFactoryConfig {
  @Bean
  @ApplicationScope
  public DefaultJmsListenerContainerFactory jmsListenerFactory(
      ConnectionFactory jmsConnectionFactory) {
    DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
    containerFactory.setConnectionFactory(jmsConnectionFactory);
    containerFactory.setConcurrency("5-10");
    containerFactory.setSubscriptionDurable(true);
    containerFactory.setSubscriptionShared(true);
    containerFactory.setPubSubDomain(true);
    final MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setTargetType(MessageType.TEXT);
    messageConverter.setTypeIdPropertyName("__type");
    messageConverter.setTypeIdMappings(Map.of("Game", Game.class));
    containerFactory.setMessageConverter(messageConverter);
    return containerFactory;
  }
}
