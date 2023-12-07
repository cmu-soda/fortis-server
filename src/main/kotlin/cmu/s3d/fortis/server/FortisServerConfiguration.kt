package cmu.s3d.fortis.server

import cmu.s3d.fortis.service.RobustificationService
import cmu.s3d.fortis.service.RobustnessComputationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.rmi.registry.LocateRegistry

@Configuration
class FortisServerConfiguration {

    @Bean
    fun robustnessComputationServiceProxy(): RobustnessComputationService {
        val registry = LocateRegistry.getRegistry()
        val robustnessComputationService = registry.lookup("RobustnessComputationService")
        return robustnessComputationService as RobustnessComputationService
    }

    @Bean
    fun robustificationServiceProxy(): RobustificationService {
        val registry = LocateRegistry.getRegistry()
        val robustificationService = registry.lookup("RobustificationService")
        return robustificationService as RobustificationService
    }
}