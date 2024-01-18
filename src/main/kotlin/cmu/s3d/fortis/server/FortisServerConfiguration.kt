package cmu.s3d.fortis.server

import cmu.s3d.fortis.service.RobustificationService
import cmu.s3d.fortis.service.RobustnessComputationService
import cmu.s3d.fortis.service.WeakeningService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.rmi.ConnectException
import java.rmi.registry.LocateRegistry

@Configuration
class FortisServerConfiguration {

    @Bean
    fun robustnessComputationServiceProxy(): RobustnessComputationService {
        val registry = LocateRegistry.getRegistry()
        for (i in 1 .. 3) {
            try {
                val robustnessComputationService = registry.lookup("RobustnessComputationService")
                return robustnessComputationService as RobustnessComputationService
            } catch (e: ConnectException) {
                Thread.sleep(3000)
            }
        }
        error("Could not connect to the RMI server of fortis-core")
    }

    @Bean
    fun robustificationServiceProxy(): RobustificationService {
        val registry = LocateRegistry.getRegistry()
        for (i in 1 .. 3) {
            try {
                val robustificationService = registry.lookup("RobustificationService")
                return robustificationService as RobustificationService
            } catch (e: ConnectException) {
                Thread.sleep(3000)
            }
        }
        error("Could not connect to the RMI server of fortis-core")
    }

    @Bean
    fun weakeningServiceProxy(): WeakeningService {
        val registry = LocateRegistry.getRegistry()
        for (i in 1 .. 3) {
            try {
                val weakeningService = registry.lookup("WeakeningService")
                return weakeningService as WeakeningService
            } catch (e: ConnectException) {
                Thread.sleep(3000)
            }
        }
        error("Could not connect to the RMI server of fortis-core")
    }
}