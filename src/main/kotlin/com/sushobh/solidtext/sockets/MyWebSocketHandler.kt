package com.sushobh.solidtext.sockets

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketConnectionManager
import org.springframework.web.socket.handler.TextWebSocketHandler

class MyWebSocketHandler : TextWebSocketHandler()  {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
    }
}