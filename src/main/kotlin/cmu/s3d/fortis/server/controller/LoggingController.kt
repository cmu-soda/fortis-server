package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.server.service.LoggingService
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.net.ServerSocket

@Controller
class LoggingController(
    private val loggingService: LoggingService
) {
    private val logger = LoggerFactory.getLogger(LoggingController::class.java)

    init {
        Thread {
            try {
                val serverSocket = ServerSocket(1100)
                while (true) {
                    val socket = serverSocket.accept()
                    val input = socket.getInputStream()
                    val buffer = ByteArray(1024)
                    var len = input.read(buffer)
                    while (len != -1) {
//                        print(String(buffer, 0, len))
                        loggingService.forwardFortisLog(String(buffer, 0, len))
                        len = input.read(buffer)
                    }
                    input.close()
                    socket.close()
                }
            } catch (e: Exception) {
                logger.error(e.stackTraceToString())
            }
        }.start()
    }

    @MessageMapping("/subscribeToLogs")
    @SendTo("/topic/logs")
    fun subscribeToLogs(): String {
        return "subscribed to fortis logs..."
    }
}