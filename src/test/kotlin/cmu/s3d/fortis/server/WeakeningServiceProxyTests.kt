package cmu.s3d.fortis.server

import cmu.s3d.fortis.common.Spec
import cmu.s3d.fortis.common.SpecType
import cmu.s3d.fortis.common.asSerializableWord
import cmu.s3d.fortis.service.WeakeningService
import net.automatalib.word.Word
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Disabled
@SpringBootTest(classes = [FortisServerApplication::class])
class WeakeningServiceProxyTests(
    @Autowired private val service : WeakeningService
) {
    @Test
    fun testGenerateExamplesFromTraceForTherac25() {
        val examples = service.generateExamplesFromTrace(
            listOf(Spec(SpecType.FSP, ClassLoader.getSystemResource("specs/therac25-2/sys.lts").readText())),
            listOf(Spec(SpecType.FSP, ClassLoader.getSystemResource("specs/therac25-2/env.lts").readText())),
            Word.fromSymbols("x", "up", "e", "enter", "b").asSerializableWord(),
            listOf("x", "up", "e", "enter", "b"),
            listOf(
                "fluent Xray = <set_xray, {set_ebeam, reset}>",
                "fluent EBeam = <set_ebeam, {set_xray, reset}>",
                "fluent InPlace = <x, e> initially 1",
                "fluent Fired = <{fire_xray, fire_ebeam}, reset>",
            )
        )
        assertEquals(
            setOf(
                Word.fromList("x,set_xray,up,e,set_ebeam,enter,b,fire_ebeam,reset".split(",")),
                Word.fromList("x,set_xray,up,e,enter,b,fire_xray,reset".split(",")),
            ),
            examples.toSet()
        )
    }
}