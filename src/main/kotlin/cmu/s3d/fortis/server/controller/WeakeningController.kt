package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.common.Spec
import cmu.s3d.fortis.common.asSerializableWord
import cmu.s3d.fortis.service.WeakeningService
import net.automatalib.word.Word
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/weakening")
@CrossOrigin
class WeakeningController(
    private val service: WeakeningService
) {
    @PostMapping("/examplesFromProgress")
    fun generateExamplesFromProgress(@RequestBody request: ExampleGenerationRequest): List<Word<String>> {
        return service.generateExamplesFromProgress(
            request.sysSpecs,
            request.envSpecs,
            request.progress?: error("progress is null"),
            request.fluents
        )
    }

    @PostMapping("/examplesFromTrace")
    fun generateExamplesFromTrace(@RequestBody request: ExampleGenerationRequest): List<Word<String>> {
        return service.generateExamplesFromTrace(
            request.sysSpecs,
            request.envSpecs,
            request.trace?: error("trace is null"),
            request.inputs?: error("inputs is null"),
            request.fluents
        )
    }

    @PostMapping("/weakenSafetyInvariant")
    fun weakenSafetyInvariant(@RequestBody request: WeakeningRequest): List<String> {
        return service.weakenSafetyInvariant(
            request.invariant,
            request.fluents,
            request.positiveExamples,
            request.negativeExamples
        )
    }
}

data class ExampleGenerationRequest(
    val sysSpecs: List<Spec>,
    val envSpecs: List<Spec>,
    val progress: String?,
    val trace: Word<String>?,
    val inputs: List<String>?,
    val fluents: List<String>
)

data class WeakeningRequest(
    val invariant: String,
    val fluents: List<String>,
    val positiveExamples: List<Word<String>>,
    val negativeExamples: List<Word<String>>
)