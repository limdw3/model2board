package kr.co.dw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class ChatHandler extends TextWebSocketHandler {
// 모든 클라이언트의 정보를 저장하는 List
	private static List<WebSocketSession> users =
			new ArrayList<WebSocketSession>();
	
	
	// 연결이 이루어진 후 호출되는 메소드 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) 
			throws Exception {
		users.add(session);
	}
	// 연결이 해제된 후 호출되는 메소드 
	
	@Override
	public void afterConnectionClosed(
			WebSocketSession session, CloseStatus status) throws Exception {
	 // List 에서 세션 젝 ㅓ
		users.remove(session);
	}

	// 클라이언트로부터 메세지가 전송되었을 떄 호출되는 메소드 
	@Override
	protected void handleTextMessage(
			WebSocketSession session, TextMessage message) throws Exception {
		// 모든 클라이언트 에게 전송
		for(WebSocketSession s : users) {
			s.sendMessage(new TextMessage(message.getPayload()));
			
			
		}
		
	}

	
}
