package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.common.EquivClassRep
import cmu.s3d.fortis.common.RobustnessOptions
import cmu.s3d.fortis.common.Spec
import cmu.s3d.fortis.common.SpecType
import cmu.s3d.fortis.service.RobustnessComputationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/robustness")
@CrossOrigin
class RobustnessComputationController(
    private val service: RobustnessComputationService
) {
    @PostMapping("/compute")
    fun computeRobustness(@RequestBody request: RobustnessRequest): List<EquivClassRep> {
        return service.computeRobustness(
            request.sysSpecs,
            request.envSpecs,
            request.propSpecs,
            request.devSpecs?: emptyList(),
            request.options
        )
    }

    @PostMapping("/intolerable")
    fun computeIntolerableBeh(@RequestBody request: RobustnessRequest): List<EquivClassRep> {
        return service.computeIntolerableBeh(
            request.sysSpecs,
            request.envSpecs,
            request.propSpecs,
            request.devSpecs?: emptyList(),
            request.options
        )
    }

    @PostMapping("/compareSys")
    fun compareRobustnessOfTwoSystems(@RequestBody request: RobustnessRequest): List<EquivClassRep> {
        if (request.sys2Specs == null) throw IllegalArgumentException("sys2Specs is null")
        return service.compareRobustnessOfTwoSystems(
            request.sysSpecs,
            request.sys2Specs,
            request.envSpecs,
            request.propSpecs,
            request.devSpecs?: emptyList(),
            request.options
        )
    }

    @PostMapping("/compareProp")
    fun compareRobustnessOfTwoProps(@RequestBody request: RobustnessRequest): List<EquivClassRep> {
        if (request.prop2Specs == null) throw IllegalArgumentException("prop2Specs is null")
        return service.compareRobustnessOfTwoProps(
            request.sysSpecs,
            request.envSpecs,
            request.propSpecs,
            request.prop2Specs,
            request.devSpecs?: emptyList(),
            request.options
        )
    }

    @PostMapping("/wa")
    fun computeWeakestAssumption(@RequestBody request: RobustnessRequest): String {
        return service.computeWeakestAssumption(
            request.sysSpecs,
            request.envSpecs,
            request.propSpecs,
            request.options,
            request.outputFormat?: SpecType.FSP
        )
    }
}

data class RobustnessRequest(
    val sysSpecs: List<Spec>,
    val sys2Specs: List<Spec>?,
    val envSpecs: List<Spec>,
    val propSpecs: List<Spec>,
    val prop2Specs: List<Spec>?,
    val devSpecs: List<Spec>?,
    val options: RobustnessOptions,
    val outputFormat: SpecType?
)