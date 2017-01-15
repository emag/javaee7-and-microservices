package javaee7.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint("/chat")
public class ChatEndpoint {

  private static final Logger LOGGER = Logger.getLogger(ChatEndpoint.class.getName());

  @OnMessage
  public void message(String message, Session session) {
    session.getOpenSessions()
      .forEach(s -> s.getAsyncRemote().sendText(message));
  }

  @OnOpen
  public void open(Session session) {
    LOGGER.info("Open: " + session.getId());
  }

  @OnClose
  public void close(Session session, CloseReason reason) {
    LOGGER.info(String.format("Closed: %s with %s", session.getId(), reason.getCloseCode()));
  }

  @OnError
  public void error(Session session, Throwable error) {
    LOGGER.severe(String.format("Error: %s with %s", session.getId(), error.getMessage()));
    error.printStackTrace();
  }

}
