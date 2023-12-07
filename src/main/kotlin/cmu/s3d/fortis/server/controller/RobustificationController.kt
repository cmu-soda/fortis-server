package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.common.Spec
import cmu.s3d.fortis.common.SpecType
import cmu.s3d.fortis.common.SupervisoryOptions
import cmu.s3d.fortis.service.RobustificationService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/robustify")
@CrossOrigin
class RobustificationController(
    private val service: RobustificationService
) {
    @PostMapping("/supervisory")
    fun robustify(@RequestBody request: RobustificationRequest): List<String> {
        return service.robustify(
            request.sysSpecs,
            request.envSpecs,
            request.propSpecs,
            request.options,
            request.outputFormat
        )
    }
}

data class RobustificationRequest(
    val sysSpecs: List<Spec>,
    val envSpecs: List<Spec>,
    val propSpecs: List<Spec>,
    val options: SupervisoryOptions,
    val outputFormat: SpecType
)