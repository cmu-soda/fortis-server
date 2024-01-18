package cmu.s3d.fortis.server.service

import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class LoggingService(
    private val messagingTemplate: SimpMessagingTemplate
) {
    fun forwardFortisLog(log: String) {
        messagingTemplate.convertAndSend("/topic/logs", log)
    }
}