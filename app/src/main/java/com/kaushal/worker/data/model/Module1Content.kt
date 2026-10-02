package com.kaushal.worker.data.model

/** Module 1 content is transcribed from the supplied KAUSHAL Fire & Explosion Response PDF. */
data class Module1Decision(
    val question: String,
    val options: List<String>,
    val correctOption: Int,
    val feedback: List<String>
)

data class Module1StoryScreen(
    val id: String,
    val chapterId: String,
    val screenNumber: Int,
    val title: String,
    val storyText: String,
    val backgroundAsset: String,
    val characterAssets: List<String> = listOf("raju"),
    val poseAsset: String? = null,
    val objectAssets: List<String> = emptyList(),
    val effectAssets: List<String> = emptyList(),
    val isDecisionPoint: Boolean = false,
    val arAvailable: Boolean = false,
    val arScenarioId: String? = null,
    val decision: Module1Decision? = null
)

data class Module1Chapter(
    val id: String,
    val title: String,
    val screens: List<Module1StoryScreen>
)

data class Module1AssessmentQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOption: Int,
    val feedback: List<String>
)


val module1Chapters: List<Module1Chapter> = listOf(
    Module1Chapter(
        id = "chapter_01",
        title = """The Small Warning""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch01_s01",
                chapterId = "chapter_01",
                screenNumber = 1,
                title = """Raju’s First Morning""".trimIndent(),
                storyText = """Raju had recently joined the mine. On his first morning shift, he was assigned to work near a large machine. While walking toward his workstation, he noticed a piece of oily cloth lying close to the machine. No flame was visible and the machine was running normally.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s02",
                chapterId = "chapter_01",
                screenNumber = 2,
                title = """Something Does Not Look Right""".trimIndent(),
                storyText = """Raju looked again. The cloth was close to a hot part of the machine. He remembered his supervisor's instruction to keep combustible material away from heat and possible ignition sources.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:looking_down""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent(), """obj_oily_cloth""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s03",
                chapterId = "chapter_01",
                screenNumber = 3,
                title = """What Should Raju Do?""".trimIndent(),
                storyText = """Raju had to decide what to do before starting his work.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent(), """obj_oily_cloth""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = false,
                arScenarioId = null,
                decision = Module1Decision(
                        question = """What should Raju do?""".trimIndent(),
                        options = listOf("""Ignore the cloth because there is no fire""".trimIndent(), """Report the unsafe condition and follow the site procedure""".trimIndent(), """Put the cloth on the machine""".trimIndent(), """Continue working and forget about it""".trimIndent()),
                        correctOption = 1,
                        feedback = listOf("""Ignoring a known fire-risk condition leaves the hazard in place.""".trimIndent(), """Correct. Raju reports it and follows the workplace procedure.""".trimIndent(), """Putting combustible material on a hot machine can increase danger.""".trimIndent(), """Continuing to work while ignoring a known unsafe condition can allow it to worsen.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch01_s04",
                chapterId = "chapter_01",
                screenNumber = 4,
                title = """Raju Calls His Supervisor""".trimIndent(),
                storyText = """Raju went to his supervisor, Mr. Kumar, and explained what he had seen. Mr. Kumar came to the workstation and inspected the area.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:talking""".trimIndent(), """mr_kumar:listening""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s05",
                chapterId = "chapter_01",
                screenNumber = 5,
                title = """Before a Fire Starts""".trimIndent(),
                storyText = """Mr. Kumar explained that fire can develop when combustible material meets a suitable source of heat or ignition. “Do not wait for the flame,” he said. “Unsafe conditions should be dealt with early.”""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s06",
                chapterId = "chapter_01",
                screenNumber = 6,
                title = """Making the Area Safe""".trimIndent(),
                storyText = """Mr. Kumar followed the workplace procedure. The cloth was removed safely and the area was checked. Raju watched instead of trying to handle it himself.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:observing""".trimIndent(), """mr_kumar:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent(), """obj_oily_cloth""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s07",
                chapterId = "chapter_01",
                screenNumber = 7,
                title = """Raju Looks Around""".trimIndent(),
                storyText = """Before starting work, Raju looked around again. He noticed material left near a passageway. It was not burning, but it could interfere with movement during an emergency.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_obstruction""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s08",
                chapterId = "chapter_01",
                screenNumber = 8,
                title = """Another Decision""".trimIndent(),
                storyText = """Raju did not walk past the obstruction. He informed his supervisor and followed the workplace procedure for clearing the area.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s09",
                chapterId = "chapter_01",
                screenNumber = 9,
                title = """The Shift Continues""".trimIndent(),
                storyText = """Raju began his assigned work. Nothing dramatic had happened. There had been no fire or alarm, but a small unsafe condition had been noticed and dealt with early.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch01_s10",
                chapterId = "chapter_01",
                screenNumber = 10,
                title = """The Same Workplace, A Different Raju""".trimIndent(),
                storyText = """Later, Raju walked past the machine again. This time he automatically looked at the surrounding area before concentrating on his task.""".trimIndent(),
                backgroundAsset = "bg_machine_area",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_01""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_02",
        title = """When Smoke Appears""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch02_s01",
                chapterId = "chapter_02",
                screenNumber = 1,
                title = """Raju Smells Something Strange""".trimIndent(),
                storyText = """A few days later, Raju noticed a strange smell while working. Moments later, he saw a thin layer of smoke coming from equipment nearby. He stopped.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_02""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s02",
                chapterId = "chapter_02",
                screenNumber = 2,
                title = """The Smoke Gets Thicker""".trimIndent(),
                storyText = """The smoke became easier to see. Raju did not assume that a small amount was harmless. He moved away and informed the responsible person according to procedure.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_02""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s03",
                chapterId = "chapter_02",
                screenNumber = 3,
                title = """What Should Raju Do?""".trimIndent(),
                storyText = """Raju could see smoke coming from the equipment.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_machine_02""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_smoke_equipment",
                decision = Module1Decision(
                        question = """Smoke is coming from equipment. What should Raju do?""".trimIndent(),
                        options = listOf("""Go very close to find the exact source""".trimIndent(), """Ignore it if there is no flame""".trimIndent(), """Raise the alarm/report it and keep away from the danger area""".trimIndent(), """Touch the equipment to check how hot it is""".trimIndent()),
                        correctOption = 2,
                        feedback = listOf("""Going close can expose Raju unnecessarily to smoke, heat or other hazards.""".trimIndent(), """Smoke can be an early sign of a serious problem, so ignoring it is unsafe.""".trimIndent(), """Correct. Raju should report/raise the alarm according to procedure and keep away.""".trimIndent(), """Touching equipment is not a safe way to investigate a suspected fire or overheating condition.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch02_s04",
                chapterId = "chapter_02",
                screenNumber = 4,
                title = """The Alarm Is Raised""".trimIndent(),
                storyText = """The responsible person raised the alarm. Workers moved away from the affected area. Raju followed the designated safe route.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s05",
                chapterId = "chapter_02",
                screenNumber = 5,
                title = """Raju Remembers the Route""".trimIndent(),
                storyText = """During orientation, Raju had noticed the emergency signs and route. Now those signs became important. He did not stay to watch what would happen.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s06",
                chapterId = "chapter_02",
                screenNumber = 6,
                title = """Someone Wants to Go Back""".trimIndent(),
                storyText = """A worker said he had left something behind and started turning back. Raju stopped him. “The alarm has been raised. We should follow the emergency procedure,” he said.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:pointing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s07",
                chapterId = "chapter_02",
                screenNumber = 7,
                title = """At the Assembly Point""".trimIndent(),
                storyText = """Raju reached the designated assembly area. The supervisor began checking workers. Raju stayed there.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s08",
                chapterId = "chapter_02",
                screenNumber = 8,
                title = """Raju Waits""".trimIndent(),
                storyText = """Raju could still see smoke. He felt curious, but he knew curiosity was not a reason to enter a dangerous area. He waited for instructions.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s09",
                chapterId = "chapter_02",
                screenNumber = 9,
                title = """The Situation Is Controlled""".trimIndent(),
                storyText = """After the responsible personnel dealt with the situation, workers were told the next step. Raju did not assume the area was safe merely because visible smoke had reduced.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch02_s10",
                chapterId = "chapter_02",
                screenNumber = 10,
                title = """Back at Work""".trimIndent(),
                storyText = """When the responsible personnel confirmed the next step, Raju returned according to procedure. The smoke had been small, but the correct response had begun before the situation became worse.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_03",
        title = """Finding the Safe Way Out""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch03_s01",
                chapterId = "chapter_03",
                screenNumber = 1,
                title = """A Normal Shift Changes""".trimIndent(),
                storyText = """Raju was working underground when an emergency alarm sounded. Workers stopped their tasks and began responding to the emergency procedure. Raju knew he had to leave the affected area safely.""".trimIndent(),
                backgroundAsset = "bg_mine_passage",
                characterAssets = listOf("""raju:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s02",
                chapterId = "chapter_03",
                screenNumber = 2,
                title = """Raju Looks for the Route""".trimIndent(),
                storyText = """Raju remembered the route from orientation. He looked for the designated signs. One part of the passage was unfamiliar, and he had to decide which direction to take.""".trimIndent(),
                backgroundAsset = "bg_mine_passage",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s03",
                chapterId = "chapter_03",
                screenNumber = 3,
                title = """Which Way Should Raju Go?""".trimIndent(),
                storyText = """One route appeared shorter, but he was not sure where it led. The other was the designated emergency route.""".trimIndent(),
                backgroundAsset = "bg_mine_passage",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_emergency_route",
                decision = Module1Decision(
                        question = """Which route should Raju follow?""".trimIndent(),
                        options = listOf("""The route that looks shortest""".trimIndent(), """The route followed by the largest group, even if it is unmarked""".trimIndent(), """The designated emergency route according to the site procedure""".trimIndent(), """Any route that leads away from his workstation""".trimIndent()),
                        correctOption = 2,
                        feedback = listOf("""A short route is not necessarily safe or authorized.""".trimIndent(), """Following a group does not replace the emergency procedure.""".trimIndent(), """Correct. Raju should follow the designated emergency route.""".trimIndent(), """Simply moving away from the workstation does not guarantee safety.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch03_s04",
                chapterId = "chapter_03",
                screenNumber = 4,
                title = """Raju Follows the Signs""".trimIndent(),
                storyText = """Raju followed the emergency signs and kept moving without running. Other workers moved toward the safe area.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s05",
                chapterId = "chapter_03",
                screenNumber = 5,
                title = """Visibility Becomes Difficult""".trimIndent(),
                storyText = """The passage became harder to see through. Raju slowed down, stayed alert and continued following the established route.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s06",
                chapterId = "chapter_03",
                screenNumber = 6,
                title = """Someone Falls Behind""".trimIndent(),
                storyText = """Raju noticed another worker having difficulty keeping up. He alerted the responsible person instead of leaving the route to investigate the emergency.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:pointing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s07",
                chapterId = "chapter_03",
                screenNumber = 7,
                title = """Reaching Safety""".trimIndent(),
                storyText = """The workers reached the safe location. Raju moved to the assembly area and remained there while the supervisor began checking.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s08",
                chapterId = "chapter_03",
                screenNumber = 8,
                title = """Raju Wants to Go Back""".trimIndent(),
                storyText = """Raju realized his tool bag was still inside. For a moment he thought about returning, then stayed where he was.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_tool_bag""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s09",
                chapterId = "chapter_03",
                screenNumber = 9,
                title = """The Supervisor Gives Instructions""".trimIndent(),
                storyText = """The supervisor reminded everyone not to re-enter the affected area unless authorized. Raju listened. His tool bag could wait.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:listening""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch03_s10",
                chapterId = "chapter_03",
                screenNumber = 10,
                title = """The Route Raju Had Practised""".trimIndent(),
                storyText = """Later, Raju thought about the emergency route. During the alarm, knowing where to go had helped him respond without wasting time.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_04",
        title = """The Extinguisher""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch04_s01",
                chapterId = "chapter_04",
                screenNumber = 1,
                title = """A Small Fire""".trimIndent(),
                storyText = """Raju was working where a small fire had started. The responsible person had been informed. Raju saw a fire extinguisher nearby and remembered that extinguishers should be used only in appropriate situations by trained/authorized people.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s02",
                chapterId = "chapter_04",
                screenNumber = 2,
                title = """Raju Looks at the Fire""".trimIndent(),
                storyText = """The fire was small, but Raju did not assume it was safe to approach. He waited for the responsible instruction.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s03",
                chapterId = "chapter_04",
                screenNumber = 3,
                title = """Should Raju Use the Extinguisher?""".trimIndent(),
                storyText = """Raju sees a small fire and an extinguisher nearby.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_extinguisher",
                decision = Module1Decision(
                        question = """What should Raju consider before attempting to use an extinguisher?""".trimIndent(),
                        options = listOf("""Whether he has the appropriate training/authorization and whether the situation is suitable""".trimIndent(), """Whether the extinguisher looks new""".trimIndent(), """Whether other workers are watching""".trimIndent(), """Whether the fire looks interesting""".trimIndent()),
                        correctOption = 0,
                        feedback = listOf("""Correct. Training/authorization, suitability, the fire situation and site procedure matter.""".trimIndent(), """Appearance alone does not determine whether an extinguisher is appropriate or safe.""".trimIndent(), """Having people watch does not make an unsafe response safe.""".trimIndent(), """Curiosity is not a reason to approach or fight a fire.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch04_s04",
                chapterId = "chapter_04",
                screenNumber = 4,
                title = """Checking the Extinguisher""".trimIndent(),
                storyText = """The trained worker responsible for the response selected the appropriate extinguisher, checked it and positioned himself safely. Raju watched from a safe location.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s05",
                chapterId = "chapter_04",
                screenNumber = 5,
                title = """Choosing the Correct Type""".trimIndent(),
                storyText = """The worker checked that the extinguisher was appropriate for the type of fire. Raju noticed that extinguishers were not simply interchangeable.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s06",
                chapterId = "chapter_04",
                screenNumber = 6,
                title = """Using the Extinguisher""".trimIndent(),
                storyText = """The trained worker used the extinguisher according to the approved procedure. Raju remained at a safe distance.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s07",
                chapterId = "chapter_04",
                screenNumber = 7,
                title = """The Fire Reduces""".trimIndent(),
                storyText = """The flames became smaller. The worker continued following procedure until the immediate fire was controlled. Raju did not move closer.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s08",
                chapterId = "chapter_04",
                screenNumber = 8,
                title = """Is the Area Automatically Safe?""".trimIndent(),
                storyText = """Raju wondered whether the incident was finished. The supervisor explained that visible flames disappearing does not automatically mean the area is safe.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s09",
                chapterId = "chapter_04",
                screenNumber = 9,
                title = """Raju Stays Back""".trimIndent(),
                storyText = """Raju remained outside the danger area and did not touch equipment or move anything without authorization.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch04_s10",
                chapterId = "chapter_04",
                screenNumber = 10,
                title = """The Extinguisher Is Not Just Equipment""".trimIndent(),
                storyText = """Later, Raju remembered why knowing the location of emergency equipment matters—and why an untrained worker should not automatically use an extinguisher just because it is nearby.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_extinguisher""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_05",
        title = """When Fire Becomes an Explosion Risk""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch05_s01",
                chapterId = "chapter_05",
                screenNumber = 1,
                title = """A Different Kind of Danger""".trimIndent(),
                storyText = """Raju was working where combustible material and possible ignition sources had to be controlled. He noticed a strong smell and that ventilation did not appear normal.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_gas_cylinder""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s02",
                chapterId = "chapter_05",
                screenNumber = 2,
                title = """Raju Stops""".trimIndent(),
                storyText = """Raju did not create a flame or take an unnecessary action to investigate. He moved away from the suspected hazard and informed the responsible person.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_gas_cylinder""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s03",
                chapterId = "chapter_05",
                screenNumber = 3,
                title = """What Should Raju Avoid?""".trimIndent(),
                storyText = """Raju suspects that a flammable atmosphere may be present.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_gas_cylinder""".trimIndent(), """obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_flammable_atmosphere",
                decision = Module1Decision(
                        question = """What should Raju avoid?""".trimIndent(),
                        options = listOf("""Following the emergency procedure""".trimIndent(), """Reporting the suspected hazard""".trimIndent(), """Creating an ignition source or taking an unauthorized action that could create one""".trimIndent(), """Moving to a safe location""".trimIndent()),
                        correctOption = 2,
                        feedback = listOf("""Following emergency procedure is an appropriate response.""".trimIndent(), """Reporting the hazard helps responsible personnel respond.""".trimIndent(), """Correct. An ignition source can increase danger when a flammable atmosphere may be present.""".trimIndent(), """Moving to a safe location may be part of the emergency response.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch05_s04",
                chapterId = "chapter_05",
                screenNumber = 4,
                title = """The Area Is Restricted""".trimIndent(),
                storyText = """The responsible person restricted access. Nobody was allowed to enter simply to have a look.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s05",
                chapterId = "chapter_05",
                screenNumber = 5,
                title = """Ventilation Matters""".trimIndent(),
                storyText = """The supervisor explained that the situation required proper assessment. Where combustible gases or vapours may be present, ventilation and atmospheric conditions can matter.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:pointing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_gas_cylinder""".trimIndent(), """obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s06",
                chapterId = "chapter_05",
                screenNumber = 6,
                title = """Checking Before Re-entry""".trimIndent(),
                storyText = """Responsible personnel carried out the required checks. Raju remained outside and waited for instructions.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s07",
                chapterId = "chapter_05",
                screenNumber = 7,
                title = """A Worker Asks a Question""".trimIndent(),
                storyText = """A worker asked why everyone was being kept away when there was no visible fire. The supervisor explained that a hazardous condition can exist before flames appear.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:listening""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s08",
                chapterId = "chapter_05",
                screenNumber = 8,
                title = """Raju Understands the Difference""".trimIndent(),
                storyText = """Raju remembered the oily cloth. That had been a visible fire-risk condition. This situation showed that some hazards can be dangerous even before smoke or flames appear.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_oily_cloth""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s09",
                chapterId = "chapter_05",
                screenNumber = 9,
                title = """The Area Is Assessed""".trimIndent(),
                storyText = """Responsible personnel completed the required assessment and controls. Raju waited for authorization before normal work resumed.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch05_s10",
                chapterId = "chapter_05",
                screenNumber = 10,
                title = """Prevention Happens Before the Flame""".trimIndent(),
                storyText = """Raju returned to normal duties after the appropriate instruction. He thought about how both incidents required action before a large fire appeared.""".trimIndent(),
                backgroundAsset = "bg_restricted_area",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_06",
        title = """The Emergency Decision""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch06_s01",
                chapterId = "chapter_06",
                screenNumber = 1,
                title = """The Alarm Sounds Again""".trimIndent(),
                storyText = """Several weeks later, Raju was working during a busy shift when the emergency alarm sounded. Workers began moving toward the emergency route. Raju had to act quickly but carefully.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:working""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s02",
                chapterId = "chapter_06",
                screenNumber = 2,
                title = """Raju Sees a Shortcut""".trimIndent(),
                storyText = """Raju noticed a passage that looked faster. He knew it, but it was not the designated emergency route. Some workers moved toward it. Raju stopped.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:observing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s03",
                chapterId = "chapter_06",
                screenNumber = 3,
                title = """The Shortcut""".trimIndent(),
                storyText = """Raju has to choose between a shortcut and the marked emergency route.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_shortcut_route",
                decision = Module1Decision(
                        question = """What should Raju do?""".trimIndent(),
                        options = listOf("""Take the shortcut because it is faster""".trimIndent(), """Follow the designated emergency route and site instructions""".trimIndent(), """Wait at his workstation""".trimIndent(), """Choose a route without checking anything""".trimIndent()),
                        correctOption = 1,
                        feedback = listOf("""A shortcut may not be safe or authorized.""".trimIndent(), """Correct. Raju should follow the designated route and emergency instructions.""".trimIndent(), """Remaining at the workstation delays evacuation and may expose Raju to danger.""".trimIndent(), """Choosing a route without checking procedure can lead into an unsafe area.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch06_s04",
                chapterId = "chapter_06",
                screenNumber = 4,
                title = """Raju Chooses the Designated Route""".trimIndent(),
                storyText = """Raju followed the marked route. He did not run or push. He stayed alert to instructions.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s05",
                chapterId = "chapter_06",
                screenNumber = 5,
                title = """Communication Matters""".trimIndent(),
                storyText = """Raju noticed that one worker had not heard the alarm clearly. He informed the responsible person, who directed the worker toward the appropriate route.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:pointing""".trimIndent(), """worker_02:confused""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s06",
                chapterId = "chapter_06",
                screenNumber = 6,
                title = """Raju Reaches the Assembly Point""".trimIndent(),
                storyText = """Raju reached the assembly point. The supervisor began checking attendance. Raju stayed in place.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s07",
                chapterId = "chapter_06",
                screenNumber = 7,
                title = """A Missing Worker""".trimIndent(),
                storyText = """The supervisor discovered one worker had not arrived. Several people wanted to go back. Raju remembered the instruction about re-entering an emergency area.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:standing""".trimIndent(), """worker_02:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s08",
                chapterId = "chapter_06",
                screenNumber = 8,
                title = """Raju Waits for the Response Team""".trimIndent(),
                storyText = """Raju told others not to enter the affected area on their own. The designated emergency response personnel were informed.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s09",
                chapterId = "chapter_06",
                screenNumber = 9,
                title = """The Worker Is Located""".trimIndent(),
                storyText = """The emergency response team handled the situation according to the site's arrangements. The missing worker was located.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch06_s10",
                chapterId = "chapter_06",
                screenNumber = 10,
                title = """Raju Looks Back""".trimIndent(),
                storyText = """Raju thought about the few minutes between the alarm and reaching safety. He had chosen the established emergency procedure instead of making his own shortcut.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:thinking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    ),
    Module1Chapter(
        id = "chapter_07",
        title = """Raju During a Fire Emergency""".trimIndent(),
        screens = listOf(
            Module1StoryScreen(
                id = "ch07_s01",
                chapterId = "chapter_07",
                screenNumber = 1,
                title = """The Shift Begins Normally""".trimIndent(),
                storyText = """Raju began another ordinary shift. Then the emergency alarm sounded.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s02",
                chapterId = "chapter_07",
                screenNumber = 2,
                title = """Smoke Is Reported""".trimIndent(),
                storyText = """A worker reported smoke nearby. The emergency procedure was activated. Raju stopped his normal task and prepared to leave according to the instructions.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:moving_away""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_alarm""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s03",
                chapterId = "chapter_07",
                screenNumber = 3,
                title = """Raju Has to Decide""".trimIndent(),
                storyText = """Raju saw that his belongings were still at his workstation and a worker nearby looked confused.""".trimIndent(),
                backgroundAsset = "bg_equipment_area",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_tool_bag""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = true,
                arAvailable = true,
                arScenarioId = "m1_fire_emergency_priority",
                decision = Module1Decision(
                        question = """What should Raju prioritize?""".trimIndent(),
                        options = listOf("""Collecting his belongings""".trimIndent(), """Staying to watch the fire""".trimIndent(), """Following the emergency procedure and moving to safety""".trimIndent(), """Returning to his workstation""".trimIndent()),
                        correctOption = 2,
                        feedback = listOf("""Belongings should not take priority over emergency evacuation and safety.""".trimIndent(), """Watching a fire is not a safe reason to remain in the affected area.""".trimIndent(), """Correct. Raju should follow the emergency procedure and move to safety.""".trimIndent(), """Returning to the workstation can increase exposure to the emergency.""".trimIndent())
                    )
            ),
            Module1StoryScreen(
                id = "ch07_s04",
                chapterId = "chapter_07",
                screenNumber = 4,
                title = """Raju Moves Out""".trimIndent(),
                storyText = """Raju followed the emergency route. He did not run or push. He stayed alert and followed instructions.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s05",
                chapterId = "chapter_07",
                screenNumber = 5,
                title = """He Helps Without Creating Another Risk""".trimIndent(),
                storyText = """The confused worker asked which direction to take. Raju pointed toward the designated route but did not leave the route to investigate the emergency.""".trimIndent(),
                backgroundAsset = "bg_emergency_route",
                characterAssets = listOf("""raju:pointing""".trimIndent(), """worker_02:confused""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf("""obj_warning_sign""".trimIndent()),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s06",
                chapterId = "chapter_07",
                screenNumber = 6,
                title = """The Assembly Point""".trimIndent(),
                storyText = """Raju reached the assembly point and remained there while the supervisor organized the workers and began the attendance check.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:walking""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s07",
                chapterId = "chapter_07",
                screenNumber = 7,
                title = """Raju Gives Information""".trimIndent(),
                storyText = """The supervisor asked what Raju had seen. Raju explained exactly what he had observed and did not guess about people he had not seen.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:listening""".trimIndent(), """worker_02:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s08",
                chapterId = "chapter_07",
                screenNumber = 8,
                title = """Waiting for Instructions""".trimIndent(),
                storyText = """Some workers asked whether they could return. Raju remembered that an emergency area should not be re-entered without authorization.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s09",
                chapterId = "chapter_07",
                screenNumber = 9,
                title = """The Emergency Is Controlled""".trimIndent(),
                storyText = """After the responsible personnel dealt with the emergency, instructions were given. Raju waited for those instructions instead of assuming the danger had disappeared.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:waiting""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
            Module1StoryScreen(
                id = "ch07_s10",
                chapterId = "chapter_07",
                screenNumber = 10,
                title = """Raju’s Shift Ends""".trimIndent(),
                storyText = """The emergency had begun during an ordinary shift. The route, alarm, assembly point and workplace procedure had given Raju a structure to follow when there was no time to improvise.""".trimIndent(),
                backgroundAsset = "bg_assembly_point",
                characterAssets = listOf("""raju:standing""".trimIndent()),
                poseAsset = null,
                objectAssets = listOf(),
                effectAssets = emptyList(),
                isDecisionPoint = false,
                arAvailable = false,
                arScenarioId = null,
                decision = null
            ),
        )
    )
)

val module1Assessment: List<Module1AssessmentQuestion> = listOf(
    Module1AssessmentQuestion(
        id = 1,
        question = """Raju finds oily cloth close to a hot machine. What is the appropriate first response?""".trimIndent(),
        options = listOf("""Ignore it because there is no flame""".trimIndent(), """Report the unsafe condition and follow the site procedure""".trimIndent(), """Put the cloth on the machine""".trimIndent(), """Continue working and forget about it""".trimIndent()),
        correctOption = 1,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 2,
        question = """Why is noticing a fire-risk condition early important?""".trimIndent(),
        options = listOf("""It can allow the unsafe condition to be addressed before it develops into an incident""".trimIndent(), """It makes the machine run faster""".trimIndent(), """It removes the need for emergency procedures""".trimIndent(), """It means alarms are no longer necessary""".trimIndent()),
        correctOption = 0,
        feedback = listOf("""Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 3,
        question = """Raju notices smoke coming from equipment. What should he do?""".trimIndent(),
        options = listOf("""Go very close to investigate""".trimIndent(), """Ignore it""".trimIndent(), """Raise the alarm/report it and keep away from the danger area according to procedure""".trimIndent(), """Touch the equipment""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 4,
        question = """After reaching the assembly point, Raju should:""".trimIndent(),
        options = listOf("""Return immediately to see what happened""".trimIndent(), """Wait for instructions""".trimIndent(), """Go back for his belongings""".trimIndent(), """Enter the affected area""".trimIndent()),
        correctOption = 1,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 5,
        question = """During an emergency evacuation, which route should Raju normally follow?""".trimIndent(),
        options = listOf("""The shortest route he can find""".trimIndent(), """The route with the most people""".trimIndent(), """The designated emergency route according to site procedure""".trimIndent(), """Any route away from his workstation""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 6,
        question = """Why should a worker avoid going back into an affected area during an emergency?""".trimIndent(),
        options = listOf("""Because personal belongings are unimportant""".trimIndent(), """Because the area may still contain hazards""".trimIndent(), """Because supervisors do not want workers to move""".trimIndent(), """Because evacuation is only for new workers""".trimIndent()),
        correctOption = 1,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 7,
        question = """Before using a fire extinguisher, a worker should consider:""".trimIndent(),
        options = listOf("""Whether the extinguisher looks attractive""".trimIndent(), """Whether the worker is trained/authorized and whether the situation is suitable""".trimIndent(), """Whether people are watching""".trimIndent(), """Whether the fire is interesting""".trimIndent()),
        correctOption = 1,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 8,
        question = """Why is the correct extinguisher important?""".trimIndent(),
        options = listOf("""Different types of fire require appropriate firefighting methods""".trimIndent(), """All extinguishers work exactly the same way""".trimIndent(), """The biggest extinguisher is always safest""".trimIndent(), """Extinguishers are only for decoration""".trimIndent()),
        correctOption = 0,
        feedback = listOf("""Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 9,
        question = """If Raju suspects a flammable atmosphere, what should he avoid?""".trimIndent(),
        options = listOf("""Following the emergency procedure""".trimIndent(), """Reporting the hazard""".trimIndent(), """Creating an ignition source or taking an unauthorized action that could create one""".trimIndent(), """Moving to a safe location""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 10,
        question = """Why might an area be restricted even when no visible fire is present?""".trimIndent(),
        options = listOf("""Because hazardous conditions may exist before flames appear""".trimIndent(), """Because workers are not allowed to walk""".trimIndent(), """Because the supervisor wants the area empty""".trimIndent(), """Because every workplace must always be closed""".trimIndent()),
        correctOption = 0,
        feedback = listOf("""Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 11,
        question = """During an emergency, Raju sees a shortcut that is not the designated emergency route. What should he do?""".trimIndent(),
        options = listOf("""Take the shortcut""".trimIndent(), """Follow the designated emergency route""".trimIndent(), """Wait for everyone else to decide""".trimIndent(), """Return to his workstation""".trimIndent()),
        correctOption = 1,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 12,
        question = """At the assembly point, Raju learns that another worker is missing. What should he do?""".trimIndent(),
        options = listOf("""Enter the affected area himself""".trimIndent(), """Ignore the information""".trimIndent(), """Inform the responsible emergency personnel and follow instructions""".trimIndent(), """Send several untrained workers back""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 13,
        question = """During a fire emergency, Raju notices that he has left his belongings behind. What should he prioritize?""".trimIndent(),
        options = listOf("""Collecting the belongings""".trimIndent(), """Returning for the belongings""".trimIndent(), """Following the emergency procedure and moving to safety""".trimIndent(), """Waiting inside until the fire is controlled""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    ),
    Module1AssessmentQuestion(
        id = 14,
        question = """After reaching the assembly point, what should Raju do?""".trimIndent(),
        options = listOf("""Leave immediately without telling anyone""".trimIndent(), """Return to work on his own""".trimIndent(), """Stay at the assembly point and follow instructions""".trimIndent(), """Enter the affected area to investigate""".trimIndent()),
        correctOption = 2,
        feedback = listOf("""This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent(), """Correct. This option follows the appropriate safety response for the situation.""".trimIndent(), """This option is not appropriate because it does not follow the safe response described in the situation.""".trimIndent())
    )
)

val module1AllStoryScreens: List<Module1StoryScreen> = module1Chapters.flatMap { it.screens }

fun getLocalizedModule1Chapters(languageCode: String): List<Module1Chapter> {
    val code = languageCode.lowercase()
    return when {
        code.startsWith("hi") -> getHindiModule1Chapters()
        code.startsWith("sat") -> getSantaliModule1Chapters()
        else -> module1Chapters
    }
}

fun getLocalizedModule1Assessment(languageCode: String): List<Module1AssessmentQuestion> {
    val code = languageCode.lowercase()
    return when {
        code.startsWith("hi") -> getHindiModule1Assessment()
        code.startsWith("sat") -> getSantaliModule1Assessment()
        else -> module1Assessment
    }
}

private fun getHindiModule1Chapters(): List<Module1Chapter> {
    return module1Chapters.map { ch ->
        when (ch.id) {
            "chapter_01" -> ch.copy(
                title = "छोटी चेतावनी",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch01_s01" -> s.copy(title = "राजू की पहली सुबह", storyText = "राजू हाल ही में खदान में शामिल हुआ था। अपनी पहली सुबह की पाली में, उसे एक बड़ी मशीन के पास काम करने का काम सौंपा गया था। अपने वर्कस्टेशन की ओर बढ़ते समय, उसने मशीन के पास तेल लगा एक कपड़ा पड़ा देखा। कोई लौ दिखाई नहीं दे रही थी और मशीन सामान्य रूप से चल रही थी।")
                        "ch01_s02" -> s.copy(title = "कुछ सही नहीं लग रहा है", storyText = "राजू ने दोबारा देखा। कपड़ा मशीन के एक गर्म हिस्से के पास था। उसे अपने पर्यवेक्षक का निर्देश याद आया कि ज्वलनशील सामग्री को गर्मी और संभावित प्रज्वलन स्रोतों से दूर रखें।")
                        "ch01_s03" -> s.copy(title = "राजू को क्या करना चाहिए?", storyText = "राजू को अपना काम शुरू करने से पहले तय करना था कि क्या करना है।", decision = s.decision?.copy(question = "राजू को क्या करना चाहिए?", options = listOf("कपड़े को अनदेखा करें क्योंकि वहां कोई आग नहीं है", "असुरक्षित स्थिति की रिपोर्ट करें और साइट प्रक्रिया का पालन करें", "कपड़े को मशीन पर रख दें", "काम करना जारी रखें और इसे भूल जाएं"), feedback = listOf("ज्ञात आग के जोखिम की स्थिति की उपेक्षा करने से खतरा बना रहता है।", "सही। राजू इसकी रिपोर्ट करता है और कार्यस्थल की प्रक्रिया का पालन करता है।", "गर्म मशीन पर ज्वलनशील सामग्री रखने से खतरा बढ़ सकता है।", "असुरक्षित स्थिति को अनदेखा करते हुए काम जारी रखने से स्थिति बिगड़ सकती है।")))
                        "ch01_s04" -> s.copy(title = "राजू अपने पर्यवेक्षक को बुलाता है", storyText = "राजू अपने पर्यवेक्षक श्री कुमार के पास गया और बताया कि उसने क्या देखा था। श्री कुमार वर्कस्टेशन पर आए और क्षेत्र का निरीक्षण किया।")
                        "ch01_s05" -> s.copy(title = "आग शुरू होने से पहले", storyText = "श्री कुमार ने समझाया कि आग तब लग सकती है जब ज्वलनशील सामग्री गर्मी या प्रज्वलन के किसी उपयुक्त स्रोत से मिलती है। \"लौ का इंतजार न करें,\" उन्होंने कहा। \"असुरक्षित स्थितियों से जल्दी निपटा जाना चाहिए।\"")
                        "ch01_s06" -> s.copy(title = "क्षेत्र को सुरक्षित बनाना", storyText = "श्री कुमार ने कार्यस्थल की प्रक्रिया का पालन किया। कपड़े को सुरक्षित रूप से हटा दिया गया और क्षेत्र का निरीक्षण किया गया। राजू ने खुद इसे संभालने की कोशिश करने के बजाय देखा।")
                        "ch01_s07" -> s.copy(title = "राजू आसपास देखता है", storyText = "काम शुरू करने से पहले राजू ने फिर आसपास देखा। उसने देखा कि पैसेजवे के पास सामग्री छोड़ी गई है। यह जल नहीं रहा था, लेकिन आपात स्थिति के दौरान यह आवाजाही में बाधा डाल सकता था।")
                        "ch01_s08" -> s.copy(title = "एक और निर्णय", storyText = "राजू बाधा के पास से नहीं गुजरा। उसने अपने पर्यवेक्षक को सूचित किया और क्षेत्र को साफ करने के लिए कार्यस्थल की प्रक्रिया का पालन किया।")
                        "ch01_s09" -> s.copy(title = "पाली जारी रहती है", storyText = "राजू ने अपना सौंपा गया काम शुरू किया। कुछ भी नाटकीय नहीं हुआ था। कोई आग या अलार्म नहीं था, लेकिन एक छोटी सी असुरक्षित स्थिति को देखा गया था और समय रहते निपटा गया था।")
                        "ch01_s10" -> s.copy(title = "वही कार्यस्थल, एक अलग राजू", storyText = "बाद में, राजू फिर से मशीन के पास से गुजरा। इस बार उसने अपने काम पर ध्यान केंद्रित करने से पहले स्वचालित रूप से आसपास के क्षेत्र को देखा।")
                        else -> s
                    }
                }
            )
            "chapter_02" -> ch.copy(
                title = "जब धुआं दिखाई दे",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch02_s01" -> s.copy(title = "राजू को अजीब गंध आती है", storyText = "कुछ दिनों बाद, राजू को काम करते समय एक अजीब गंध आई। कुछ ही पलों बाद, उसने पास के उपकरण से धुएं की एक पतली परत आते देखी। वह रुक गया।")
                        "ch02_s02" -> s.copy(title = "धुआं गाढ़ा हो जाता है", storyText = "धुआं अधिक स्पष्ट हो गया। राजू ने यह नहीं माना कि थोड़ी सी मात्रा नुकसान रहित थी। वह दूर चला गया और प्रक्रिया के अनुसार जिम्मेदार व्यक्ति को सूचित किया।")
                        "ch02_s03" -> s.copy(title = "राजू को क्या करना चाहिए?", storyText = "राजू उपकरण से धुआं निकलते देख सकता था।", decision = s.decision?.copy(question = "उपकरण से धुआं आ रहा है। राजू को क्या करना चाहिए?", options = listOf("सटीक स्रोत खोजने के लिए बहुत करीब जाएं", "यदि कोई लौ नहीं है तो इसे अनदेखा करें", "अलार्म बजाएं/रिपोर्ट करें और खतरे वाले क्षेत्र से दूर रहें", "कितना गर्म है यह जांचने के लिए उपकरण को छुएं"), feedback = listOf("करीब जाने से राजू अनावश्यक रूप से धुएं, गर्मी या अन्य खतरों की चपेट में आ सकता है।", "धुआं किसी गंभीर समस्या का शुरुआती संकेत हो सकता है, इसलिए इसे अनदेखा करना असुरक्षित है।", "सही। राजू को प्रक्रिया के अनुसार अलार्म/रिपोर्ट करना चाहिए और दूर रहना चाहिए।", "उपकरण को छूना संदिग्ध आग की जांच का सुरक्षित तरीका नहीं है।")))
                        "ch02_s04" -> s.copy(title = "अलार्म बजाया गया", storyText = "जिम्मेदार व्यक्ति ने अलार्म बजाया। श्रमिक प्रभावित क्षेत्र से दूर चले गए। राजू ने निर्धारित सुरक्षित मार्ग का पालन किया।")
                        "ch02_s05" -> s.copy(title = "राजू को रास्ता याद आता है", storyText = "ओरिएंटेशन के दौरान, राजू ने आपातकालीन संकेतों और मार्ग पर ध्यान दिया था। अब वे संकेत महत्वपूर्ण हो गए। वह यह देखने के लिए नहीं रुका कि क्या होगा।")
                        "ch02_s06" -> s.copy(title = "कोई वापस जाना चाहता है", storyText = "एक कर्मचारी ने कहा कि वह कुछ पीछे छोड़ आया है और वापस मुड़ने लगा। राजू ने उसे रोक दिया। \"अलार्म बजा दिया गया है। हमें आपातकालीन प्रक्रिया का पालन करना चाहिए,\" उसने कहा।")
                        "ch02_s07" -> s.copy(title = "असेंबली पॉइंट पर", storyText = "राजू निर्धारित असेंबली क्षेत्र में पहुंच गया। पर्यवेक्षक ने श्रमिकों की जांच शुरू की। राजू वहीं रुका।")
                        "ch02_s08" -> s.copy(title = "राजू इंतजार करता है", storyText = "राजू अब भी धुआं देख सकता था। वह उत्सुक महसूस कर रहा था, लेकिन वह जानता था कि उत्सुकता खतरनाक क्षेत्र में प्रवेश करने का कारण नहीं है। उसने निर्देशों का इंतजार किया।")
                        "ch02_s09" -> s.copy(title = "स्थिति नियंत्रित है", storyText = "जिम्मेदार कर्मियों द्वारा स्थिति से निपटने के बाद, श्रमिकों को अगला कदम बताया गया। राजू ने यह नहीं माना कि केवल दिखाई देने वाला धुआं कम होने से क्षेत्र सुरक्षित हो गया है।")
                        "ch02_s10" -> s.copy(title = "वापस काम पर", storyText = "जब जिम्मेदार कर्मियों ने अगले कदम की पुष्टि की, तो राजू प्रक्रिया के अनुसार लौट आया। धुआं कम था, लेकिन स्थिति बिगड़ने से पहले सही प्रतिक्रिया शुरू हो गई थी।")
                        else -> s
                    }
                }
            )
            "chapter_03" -> ch.copy(
                title = "बाहर निकलने का सुरक्षित रास्ता खोजना",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch03_s01" -> s.copy(title = "एक सामान्य पाली बदलती है", storyText = "राजू भूमिगत काम कर रहा था जब एक आपातकालीन अलार्म बजा। श्रमिकों ने अपने काम रोक दिए और आपातकालीन प्रक्रिया का जवाब देना शुरू कर दिया। राजू जानता था कि उसे प्रभावित क्षेत्र को सुरक्षित रूप से छोड़ना है।")
                        "ch03_s02" -> s.copy(title = "राजू रास्ता ढूंढता है", storyText = "राजू को ओरिएंटेशन का रास्ता याद आया। उसने निर्धारित संकेतों की तलाश की। मार्ग का एक हिस्सा अपरिचित था, और उसे तय करना था कि किस दिशा में जाना है।")
                        "ch03_s03" -> s.copy(title = "राजू को किस रास्ते जाना चाहिए?", storyText = "एक रास्ता छोटा लग रहा था, लेकिन उसे यकीन नहीं था कि वह कहाँ जाता है। दूसरा निर्धारित आपातकालीन मार्ग था।", decision = s.decision?.copy(question = "राजू को किस मार्ग का पालन करना चाहिए?", options = listOf("वह रास्ता जो सबसे छोटा लगता है", "सबसे बड़े समूह द्वारा अपनाया गया रास्ता, भले ही वह अचिह्नित हो", "साइट प्रक्रिया के अनुसार निर्धारित आपातकालीन मार्ग", "कोई भी मार्ग जो उसके वर्कस्टेशन से दूर ले जाता है"), feedback = listOf("जरूरी नहीं कि छोटा रास्ता सुरक्षित या अधिकृत हो।", "किसी समूह का अनुसरण करना आपातकालीन प्रक्रिया का स्थान नहीं लेता है।", "सही। राजू को निर्धारित आपातकालीन मार्ग का पालन करना चाहिए।", "केवल वर्कस्टेशन से दूर जाने से सुरक्षा की गारंटी नहीं मिलती है।")))
                        "ch03_s04" -> s.copy(title = "राजू संकेतों का पालन करता है", storyText = "राजू ने आपातकालीन संकेतों का पालन किया और बिना भागे चलता रहा। अन्य श्रमिक सुरक्षित क्षेत्र की ओर बढ़ गए।")
                        "ch03_s05" -> s.copy(title = "दृश्यता कठिन हो जाती है", storyText = "मार्ग से देखना कठिन हो गया। राजू धीमा हो गया, सतर्क रहा और स्थापित मार्ग का पालन करता रहा।")
                        "ch03_s06" -> s.copy(title = "कोई पीछे छूट जाता है", storyText = "राजू ने देखा कि एक अन्य कर्मचारी को साथ चलने में कठिनाई हो रही है। उसने आपात स्थिति की जांच के लिए मार्ग छोड़ने के बजाय जिम्मेदार व्यक्ति को सचेत किया।")
                        "ch03_s07" -> s.copy(title = "सुरक्षा तक पहुंचना", storyText = "श्रमिक सुरक्षित स्थान पर पहुंच गए। राजू असेंबली क्षेत्र में चला गया और वहीं रहा जब पर्यवेक्षक ने जांच शुरू की।")
                        "ch03_s08" -> s.copy(title = "राजू वापस जाना चाहता है", storyText = "राजू को अहसास हुआ कि उसका टूल बैग अभी भी अंदर है। एक पल के लिए उसने लौटने के बारे में सोचा, फिर वह वहीं रुका रहा जहाँ वह था।")
                        "ch03_s09" -> s.copy(title = "पर्यवेक्षक निर्देश देता है", storyText = "पर्यवेक्षक ने सभी को याद दिलाया कि अधिकृत किए बिना प्रभावित क्षेत्र में दोबारा प्रवेश न करें। राजू ने सुना। उसका टूल बैग इंतजार कर सकता था।")
                        "ch03_s10" -> s.copy(title = "वह रास्ता जिसका राजू ने अभ्यास किया था", storyText = "बाद में, राजू ने आपातकालीन मार्ग के बारे में सोचा। अलार्म के दौरान, यह जानने से कि कहां जाना है, उसे बिना समय बर्बाद किए प्रतिक्रिया देने में मदद मिली थी।")
                        else -> s
                    }
                }
            )
            "chapter_04" -> ch.copy(
                title = "अग्निशामक यंत्र (एक्सटिंग्विशर)",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch04_s01" -> s.copy(title = "एक छोटी सी आग", storyText = "राजू वहां काम कर रहा था जहां छोटी सी आग लग गई थी। जिम्मेदार व्यक्ति को सूचित कर दिया गया था। राजू ने पास में एक अग्निशामक यंत्र देखा और याद रखा कि एक्सटिंग्विशर का उपयोग प्रशिक्षित/अधिकृत लोगों द्वारा केवल उपयुक्त स्थितियों में किया जाना चाहिए।")
                        "ch04_s02" -> s.copy(title = "राजू आग को देखता है", storyText = "आग छोटी थी, लेकिन राजू ने यह नहीं माना कि पास जाना सुरक्षित है। उसने जिम्मेदार निर्देश का इंतजार किया।")
                        "ch04_s03" -> s.copy(title = "क्या राजू को एक्सटिंग्विशर का उपयोग करना चाहिए?", storyText = "राजू एक छोटी सी आग और पास में एक एक्सटिंग्विशर देखता है।", decision = s.decision?.copy(question = "एक्सटिंग्विशर का उपयोग करने का प्रयास करने से पहले राजू को क्या ध्यान में रखना चाहिए?", options = listOf("क्या उसके पास उचित प्रशिक्षण/प्राधिकरण है और क्या स्थिति उपयुक्त है", "क्या अग्निशामक नया दिखता है", "क्या अन्य श्रमिक देख रहे हैं", "क्या आग दिलचस्प लगती है"), feedback = listOf("सही। प्रशिक्षण/प्राधिकरण, उपयुक्तता, आग की स्थिति और साइट की प्रक्रिया मायने रखती है।", "अकेले रूप-रंग से यह तय नहीं होता कि एक्सटिंग्विशर उपयुक्त या सुरक्षित है या नहीं।", "लोगों द्वारा देखना किसी असुरक्षित प्रतिक्रिया को सुरक्षित नहीं बनाता है।", "उत्सुकता आग के पास जाने या बुझाने का कारण नहीं है।")))
                        "ch04_s04" -> s.copy(title = "एक्सटिंग्विशर की जांच", storyText = "प्रतिक्रिया के लिए जिम्मेदार प्रशिक्षित कर्मचारी ने उपयुक्त एक्सटिंग्विशर का चयन किया, उसकी जांच की और खुद को सुरक्षित रूप से स्थित किया। राजू ने सुरक्षित स्थान से देखा।")
                        "ch04_s05" -> s.copy(title = "सही प्रकार चुनना", storyText = "कर्मचारी ने जांच की कि एक्सटिंग्विशर आग के प्रकार के लिए उपयुक्त था। राजू ने देखा कि एक्सटिंग्विशर आपस में बदले जाने योग्य नहीं थे।")
                        "ch04_s06" -> s.copy(title = "एक्सटिंग्विशर का उपयोग", storyText = "प्रशिक्षित कर्मचारी ने अनुमोदित प्रक्रिया के अनुसार एक्सटिंग्विशर का उपयोग किया। राजू सुरक्षित दूरी पर रहा।")
                        "ch04_s07" -> s.copy(title = "आग कम हो जाती है", storyText = "लपटें छोटी हो गईं। कर्मचारी ने तब तक प्रक्रिया का पालन जारी रखा जब तक कि तत्काल आग पर नियंत्रण नहीं पा लिया गया। राजू करीब नहीं गया।")
                        "ch04_s08" -> s.copy(title = "क्या क्षेत्र स्वतः सुरक्षित है?", storyText = "राजू सोच रहा था कि क्या घटना समाप्त हो गई है। पर्यवेक्षक ने समझाया कि दिखाई देने वाली लपटों का गायब होना स्वचालित रूप से यह नहीं दर्शाता कि क्षेत्र सुरक्षित है।")
                        "ch04_s09" -> s.copy(title = "राजू पीछे हटता है", storyText = "राजू खतरे के क्षेत्र से बाहर रहा और बिना अनुमति के उपकरण नहीं छुए या कुछ भी नहीं हटाया।")
                        "ch04_s10" -> s.copy(title = "एक्सटिंग्विशर केवल उपकरण नहीं है", storyText = "बाद में, राजू को याद आया कि आपातकालीन उपकरणों का स्थान जानना क्यों मायने रखता है—और एक अप्रशिक्षित कर्मचारी को केवल इसलिए एक्सटिंग्विशर का उपयोग क्यों नहीं करना चाहिए क्योंकि वह पास में है।")
                        else -> s
                    }
                }
            )
            "chapter_05" -> ch.copy(
                title = "जब आग विस्फोट का खतरा बन जाती है",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch05_s01" -> s.copy(title = "एक अलग तरह का खतरा", storyText = "राजू वहां काम कर रहा था जहां ज्वलनशील सामग्री और संभावित प्रज्वलन स्रोतों को नियंत्रित किया जाना था। उसने एक तेज गंध महसूस की और देखा कि वेंटिलेशन सामान्य नहीं लग रहा था।")
                        "ch05_s02" -> s.copy(title = "राजू रुकता है", storyText = "राजू ने कोई लौ नहीं पैदा की और न ही जांच के लिए कोई अनावश्यक कदम उठाया। वह संदिग्ध खतरे से दूर चला गया और जिम्मेदार व्यक्ति को सूचित किया।")
                        "ch05_s03" -> s.copy(title = "राजू को किससे बचना चाहिए?", storyText = "राजू को संदेह है कि एक ज्वलनशील वातावरण मौजूद हो सकता है।", decision = s.decision?.copy(question = "राजू को किससे बचना चाहिए?", options = listOf("आपातकालीन प्रक्रिया का पालन करना", "संदिग्ध खतरे की रिपोर्ट करना", "इग्निशन स्रोत बनाना या अनधिकृत कार्रवाई करना जिससे इग्निशन स्रोत बन सकता है", "सुरक्षित स्थान पर जाना"), feedback = listOf("आपातकालीन प्रक्रिया का पालन करना एक उचित प्रतिक्रिया है।", "खतरे की रिपोर्ट करने से जिम्मेदार कर्मियों को प्रतिक्रिया देने में मदद मिलती है।", "सही। ज्वलनशील वातावरण मौजूद होने पर इग्निशन स्रोत खतरा बढ़ा सकता है।", "सुरक्षित स्थान पर जाना आपातकालीन प्रतिक्रिया का हिस्सा हो सकता है।")))
                        "ch05_s04" -> s.copy(title = "क्षेत्र प्रतिबंधित है", storyText = "जिम्मेदार व्यक्ति ने पहुंच प्रतिबंधित कर दी। किसी को भी केवल देखने के लिए प्रवेश करने की अनुमति नहीं थी।")
                        "ch05_s05" -> s.copy(title = "वेंटिलेशन मायने रखता है", storyText = "पर्यवेक्षक ने समझाया कि स्थिति के लिए उचित मूल्यांकन की आवश्यकता है। जहां ज्वलनशील गैसें या वाष्प मौजूद हो सकते हैं, वहां वेंटिलेशन और वायुमंडलीय स्थितियां मायने रखती हैं।")
                        "ch05_s06" -> s.copy(title = "पुनः प्रवेश से पहले जांच", storyText = "जिम्मेदार कर्मियों ने आवश्यक जांच की। राजू बाहर रहा और निर्देशों का इंतजार किया।")
                        "ch05_s07" -> s.copy(title = "एक कर्मचारी प्रश्न पूछता है", storyText = "एक कर्मचारी ने पूछा कि जब कोई दृश्य आग नहीं थी तो सभी को दूर क्यों रखा जा रहा था। पर्यवेक्षक ने समझाया कि लपटें दिखाई देने से पहले भी खतरनाक स्थिति मौजूद हो सकती है।")
                        "ch05_s08" -> s.copy(title = "राजू अंतर समझता है", storyText = "राजू को तेल लगा कपड़ा याद आया। वह एक दृश्य आग के जोखिम की स्थिति थी। इस स्थिति ने दिखाया कि धुएं या लपटें दिखाई देने से पहले भी कुछ खतरे खतरनाक हो सकते हैं।")
                        "ch05_s09" -> s.copy(title = "क्षेत्र का मूल्यांकन किया जाता है", storyText = "जिम्मेदार कर्मियों ने आवश्यक मूल्यांकन और नियंत्रण पूरा किया। राजू ने सामान्य काम फिर से शुरू होने से पहले अनुमति का इंतजार किया।")
                        "ch05_s10" -> s.copy(title = "रोकथाम लौ से पहले होती है", storyText = "उचित निर्देश के बाद राजू सामान्य कर्तव्यों पर लौट आया। उसने सोचा कि कैसे दोनों घटनाओं में बड़ी आग लगने से पहले कार्रवाई की आवश्यकता थी।")
                        else -> s
                    }
                }
            )
            "chapter_06" -> ch.copy(
                title = "आपातकालीन निर्णय",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch06_s01" -> s.copy(title = "अलार्म फिर बजता है", storyText = "कई हफ्तों बाद, राजू एक व्यस्त पाली के दौरान काम कर रहा था जब आपातकालीन अलार्म बजा। श्रमिक आपातकालीन मार्ग की ओर बढ़ने लगे। राजू को तेजी से लेकिन सावधानी से काम करना पड़ा।")
                        "ch06_s02" -> s.copy(title = "राजू एक शॉर्टकट देखता है", storyText = "राजू ने एक रास्ता देखा जो तेज़ लग रहा था। वह इसे जानता था, लेकिन यह निर्धारित आपातकालीन मार्ग नहीं था। कुछ श्रमिक उसकी ओर बढ़े। राजू रुक गया।")
                        "ch06_s03" -> s.copy(title = "शॉर्टकट", storyText = "राजू को शॉर्टकट और चिह्नित आपातकालीन मार्ग के बीच चयन करना है।", decision = s.decision?.copy(question = "राजू को क्या करना चाहिए?", options = listOf("शॉर्टकट लें क्योंकि यह तेज़ है", "निर्धारित आपातकालीन मार्ग और साइट निर्देशों का पालन करें", "अपने वर्कस्टेशन पर प्रतीक्षा करें", "बिना जांच किए कोई मार्ग चुनें"), feedback = listOf("शॉर्टकट सुरक्षित या अधिकृत नहीं हो सकता है।", "सही। राजू को निर्धारित मार्ग और आपातकालीन निर्देशों का पालन करना चाहिए।", "वर्कस्टेशन पर रहने से निकासी में देरी होती है और खतरा हो सकता है।", "प्रक्रिया की जांच किए बिना मार्ग चुनने से असुरक्षित क्षेत्र में पहुंचा जा सकता है।")))
                        "ch06_s04" -> s.copy(title = "राजू निर्धारित मार्ग चुनता है", storyText = "राजू ने चिह्नित मार्ग का पालन किया। वह भागा या धक्का नहीं दिया। वह निर्देशों के प्रति सचेत रहा।")
                        "ch06_s05" -> s.copy(title = "संचार मायने रखता है", storyText = "राजू ने देखा कि एक कर्मचारी ने अलार्म स्पष्ट रूप से नहीं सुना था। उसने जिम्मेदार व्यक्ति को सूचित किया, जिसने कर्मचारी को उचित मार्ग की ओर निर्देशित किया।")
                        "ch06_s06" -> s.copy(title = "राजू असेंबली पॉइंट पर पहुंचता है", storyText = "राजू असेंबली पॉइंट पर पहुंचा। पर्यवेक्षक ने उपस्थिति की जांच शुरू की। राजू अपनी जगह पर रहा।")
                        "ch06_s07" -> s.copy(title = "एक लापता कर्मचारी", storyText = "पर्यवेक्षक ने पाया कि एक कर्मचारी नहीं आया था। कई लोग वापस जाना चाहते थे। राजू को आपातकालीन क्षेत्र में दोबारा प्रवेश करने के निर्देश याद आए।")
                        "ch06_s08" -> s.copy(title = "राजू रेस्पॉन्स टीम का इंतजार करता है", storyText = "राजू ने दूसरों को अपने आप प्रभावित क्षेत्र में प्रवेश न करने के लिए कहा। निर्धारित आपातकालीन प्रतिक्रिया कर्मियों को सूचित किया गया।")
                        "ch06_s09" -> s.copy(title = "कर्मचारी मिल गया", storyText = "आपातकालीन प्रतिक्रिया दल ने स्थल की व्यवस्था के अनुसार स्थिति को संभाला। लापता कर्मचारी मिल गया।")
                        "ch06_s10" -> s.copy(title = "राजू पीछे मुड़कर देखता है", storyText = "राजू ने अलार्म और सुरक्षा तक पहुंचने के बीच के कुछ मिनटों के बारे में सोचा। उसने अपना शॉर्टकट बनाने के बजाय स्थापित आपातकालीन प्रक्रिया को चुना था।")
                        else -> s
                    }
                }
            )
            "chapter_07" -> ch.copy(
                title = "आपात स्थिति के दौरान राजू",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch07_s01" -> s.copy(title = "पाली सामान्य रूप से शुरू होती है", storyText = "राजू ने एक और साधारण पाली शुरू की। तभी आपातकालीन अलार्म बजा।")
                        "ch07_s02" -> s.copy(title = "धुएं की रिपोर्ट की गई है", storyText = "एक कर्मचारी ने पास में धुएं की सूचना दी। आपातकालीन प्रक्रिया सक्रिय हो गई। राजू ने अपना सामान्य काम रोक दिया और निर्देशों के अनुसार निकलने की तैयारी की।")
                        "ch07_s03" -> s.copy(title = "राजू को तय करना है", storyText = "राजू ने देखा कि उसका सामान अभी भी उसके वर्कस्टेशन पर है और पास का एक कर्मचारी भ्रमित दिख रहा था।", decision = s.decision?.copy(question = "राजू को किसे प्राथमिकता देनी चाहिए?", options = listOf("अपना सामान इकट्ठा करना", "आग देखने के लिए रुकना", "आपातकालीन प्रक्रिया का पालन करना और सुरक्षा की ओर बढ़ना", "अपने वर्कस्टेशन पर लौटना"), feedback = listOf("सामान को आपातकालीन निकासी और सुरक्षा पर प्राथमिकता नहीं दी जानी चाहिए।", "आग देखना प्रभावित क्षेत्र में रहने का सुरक्षित कारण नहीं है।", "सही। राजू को आपातकालीन प्रक्रिया का पालन करना चाहिए और सुरक्षा की ओर बढ़ना चाहिए।", "वर्कस्टेशन पर लौटने से आपात स्थिति का जोखिम बढ़ सकता है।")))
                        "ch07_s04" -> s.copy(title = "राजू बाहर निकलता है", storyText = "राजू ने आपातकालीन मार्ग का पालन किया। वह भागा या धक्का नहीं दिया। वह सतर्क रहा और निर्देशों का पालन किया।")
                        "ch07_s05" -> s.copy(title = "वह दूसरा खतरा पैदा किए बिना मदद करता है", storyText = "भ्रमित कर्मचारी ने पूछा कि किस दिशा में जाना है। राजू ने निर्धारित मार्ग की ओर इशारा किया लेकिन आपात स्थिति की जांच के लिए मार्ग नहीं छोड़ा।")
                        "ch07_s06" -> s.copy(title = "असेंबली पॉइंट", storyText = "राजू असेंबली पॉइंट पर पहुंचा और वहीं रहा जबकि पर्यवेक्षक ने श्रमिकों को व्यवस्थित किया और उपस्थिति जांच शुरू की।")
                        "ch07_s07" -> s.copy(title = "राजू जानकारी देता है", storyText = "पर्यवेक्षक ने पूछा कि राजू ने क्या देखा था। राजू ने ठीक वही बताया जो उसने देखा था और जिन लोगों को उसने नहीं देखा था उनके बारे में अनुमान नहीं लगाया।")
                        "ch07_s08" -> s.copy(title = "निर्देशों का इंतजार", storyText = "कुछ कर्मचारियों ने पूछा कि क्या वे लौट सकते हैं। राजू को याद आया कि अनुमति के बिना किसी आपातकालीन क्षेत्र में दोबारा प्रवेश नहीं किया जाना चाहिए।")
                        "ch07_s09" -> s.copy(title = "आपात स्थिति नियंत्रित है", storyText = "जिम्मेदार कर्मियों द्वारा आपात स्थिति से निपटने के बाद निर्देश दिए गए। राजू ने यह मानने के बजाय कि खतरा गायब हो गया है, उन निर्देशों का इंतजार किया।")
                        "ch07_s10" -> s.copy(title = "राजू की पाली समाप्त होती है", storyText = "आपात स्थिति एक साधारण पाली के दौरान शुरू हुई थी। मार्ग, अलार्म, असेंबली पॉइंट और कार्यस्थल की प्रक्रिया ने राजू को पालन करने के लिए एक संरचना दी थी जब सुधार करने का समय नहीं था।")
                        else -> s
                    }
                }
            )
            else -> ch
        }
    }
}

private fun getSantaliModule1Chapters(): List<Module1Chapter> {
    return module1Chapters.map { ch ->
        when (ch.id) {
            "chapter_01" -> ch.copy(
                title = "ᱦᱩᱰᱤᱧ ᱪᱮᱛᱟᱣᱱᱤ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch01_s01" -> s.copy(title = "ᱨᱟᱡᱩᱣᱟᱜ ᱯᱟᱹᱦᱤᱞ ᱥᱮᱛᱟᱜ", storyText = "ᱨᱟᱡᱩ ᱱᱟᱶᱟ ᱜᱮ ᱠᱷᱟᱫᱟᱱ ᱨᱮ ᱥᱮᱞᱮᱫ ᱞᱮᱱᱟ। ᱟᱭᱟᱜ ᱯᱟᱹᱦᱤᱞ ᱥᱮᱛᱟᱜ ᱠᱟᱹᱢᱤ ᱨᱮ, ᱩᱱᱤ ᱫᱚ ᱢᱤᱫ ᱢᱟᱨᱟᱝ ᱢᱮᱥᱤᱱ ᱡᱟᱯᱟᱜ ᱨᱮ ᱠᱟᱹᱢᱤ ᱮᱢ ᱦᱩᱭ ᱞᱮᱱᱟ। ᱟᱭᱟᱜ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱥᱮᱫ ᱪᱟᱞᱟᱜ ᱡᱚᱠᱷᱮᱡ, ᱩᱱᱤ ᱢᱮᱥᱤᱱ ᱡᱟᱯᱟᱜ ᱨᱮ ᱥᱩᱱᱩᱢᱟᱱ ᱞᱩᱜᱽᱲᱤ ᱴᱩᱠᱨᱟᱹ ᱧᱩᱨ ᱟᱠᱟᱱᱮ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱪᱮᱫ ᱥᱮᱸᱜᱮᱞ ᱦᱚᱸ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ ᱟᱨ ᱢᱮᱥᱤᱱ ᱴᱷᱤᱠ ᱜᱮ ᱪᱟᱞᱟᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ।")
                        "ch01_s02" -> s.copy(title = "ᱪᱮᱫ ᱪᱚᱝ ᱵᱟᱝ ᱴᱷᱤᱠ ᱧᱮᱞᱚᱜ ᱠᱟᱱᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱨᱦᱚᱸ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱞᱩᱜᱽᱲᱤ ᱫᱚ ᱢᱮᱥᱤᱱ ᱨᱮᱱᱟᱜ ᱞᱚᱞᱚ ᱴᱷᱟᱶ ᱡᱟᱯᱟᱜ ᱨᱮ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱩᱱᱤ ᱟᱭᱟᱜ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨᱟᱜ ᱠᱟᱛᱷᱟ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ ᱡᱮ ᱡᱩᱞᱩᱜ ᱡᱤᱱᱤᱥ ᱫᱚ ᱞᱚᱞᱚ ᱟᱨ ᱥᱮᱸᱜᱮᱞ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱨᱮ ᱫᱚᱦᱚᱭ ᱢᱮ।")
                        "ch01_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?", storyText = "ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱞᱟᱦᱟᱨᱮ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ ᱵᱟᱪᱷᱟᱣ ᱦᱩᱭ ᱟᱭ ᱛᱟᱦᱮᱸᱱ।", decision = s.decision?.copy(question = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱞᱩᱜᱽᱲᱤ ᱟᱞᱚᱢ ᱫᱷᱭᱟᱱᱟ ᱪᱮᱫᱟᱜ ᱥᱮ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱹᱱᱩᱜ-ᱟ", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱞᱟᱹᱭ ᱢᱮ ᱟᱨ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱞᱩᱜᱽᱲᱤ ᱢᱮᱥᱤᱱ ᱪᱮᱛᱟᱱ ᱨᱮ ᱫᱚᱦᱚᱭ ᱢᱮ", "ᱠᱟᱹᱢᱤ ᱞᱟᱦᱟᱭ ᱢᱮ ᱟᱨ ᱦᱤᱲᱤᱧ ᱢᱮ"), feedback = listOf("ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱟᱞᱚᱢ ᱵᱟᱹᱜᱤᱭᱟ, ᱚᱱᱟ ᱛᱮ ᱡᱚᱠᱷᱤᱢ ᱛᱟᱦᱮᱸᱱ-ᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱨᱟᱡᱩ ᱞᱟᱹᱭ ᱟᱠᱚᱣᱟ ᱟᱨ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱟ।", "ᱞᱚᱞᱚ ᱢᱮᱥᱤᱱ ᱪᱮᱛᱟᱱ ᱨᱮ ᱡᱩᱞᱩᱜ ᱡᱤᱱᱤᱥ ᱫᱚᱦᱚ ᱞᱮᱠᱷᱟᱱ ᱵᱚᱛᱚᱨ ᱵᱟ push-ᱟ।", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱵᱟᱹᱜᱤ ᱠᱟᱛᱮ ᱠᱟᱹᱢᱤ ᱞᱮᱠᱷᱟᱱ ᱦᱟᱞᱚᱛ ᱵᱟᱹᱲᱤᱡᱚᱜ-ᱟ।")))
                        "ch01_s04" -> s.copy(title = "ᱨᱟᱡᱩ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨᱮ ᱦᱚᱦᱚ ᱟᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱭᱟᱜ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱢᱟᱹᱱ ᱠᱩᱢᱟᱨ ᱴᱷᱮᱱ ᱪᱟᱞᱟᱣ ᱮᱱᱟ ᱟᱨ ᱪᱮᱫ ᱧᱮᱞ ᱟᱠᱟᱫ-ᱟᱭ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ। ᱢᱟᱹᱱ ᱠᱩᱢᱟᱨ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱛᱮ ᱦᱮᱡ ᱠᱟᱛᱮ ᱴᱷᱟᱶ ᱧᱮᱞ ᱠᱮᱫ-ᱟ।")
                        "ch01_s05" -> s.copy(title = "ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞᱩᱜ ᱞᱟᱦᱟᱨᱮ", storyText = "ᱢᱟᱹᱱ ᱠᱩᱢᱟᱨ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ ᱡᱮ ᱡᱩᱞᱩᱜ ᱡᱤᱱᱤᱥ ᱞᱚᱞᱚ ᱴᱷᱮᱱ ᱧᱟᱯᱟᱢ ᱞᱮᱠᱷᱟᱱ ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ। \"ᱥᱮᱸᱜᱮᱞ ᱛᱟᱺᱜᱤ ᱨᱮ ᱟᱞᱚᱢ ᱛᱟᱦᱮᱸᱱ-ᱟ,\" ᱩᱱᱤ ᱢᱮᱱ ᱠᱮᱫ-ᱟ। \"ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱫᱚ ᱞᱚᱜᱚᱱ ᱥᱟᱯᱲᱟᱣ ᱞᱟᱹᱠᱛᱤ।\"")
                        "ch01_s06" -> s.copy(title = "ᱴᱷᱟᱶ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱮᱱᱟᱣ", storyText = "ᱢᱟᱹᱱ ᱠᱩᱢᱟᱨ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ। ᱞᱩᱜᱽᱲᱤ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱛᱮ ᱚᱪᱚᱜ ᱮᱱᱟ ᱟᱨ ᱴᱷᱟᱶ ᱡᱟᱥ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱟᱯᱱᱟᱨ ᱛᱮ ᱵᱟᱝ ᱠᱚᱨᱟᱣ ᱠᱟᱛᱮ ᱧᱮᱞ ᱠᱮᱫ-ᱟ।")
                        "ch01_s07" -> s.copy(title = "ᱨᱟᱡᱩ ᱟᱰᱮ ᱯᱟᱥᱮ ᱧᱮᱞᱟ", storyText = "ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱞᱟᱦᱟᱨᱮ ᱨᱟᱡᱩ ᱟᱨᱦᱚᱸ ᱟᱰᱮ ᱯᱟᱥᱮ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱦᱚᱨ ᱡᱟᱯᱟᱜ ᱨᱮ ᱡᱤᱱᱤᱥ ᱧᱩᱨ ᱟᱠᱟᱱᱮ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱚᱱᱟ ᱫᱚ ᱵᱟᱝ ᱡᱩᱞᱩᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ, ᱢᱮᱱᱠᱷᱟᱱ ᱟᱯᱟᱛ ᱨᱮ ᱪᱟᱞᱟᱜ ᱨᱮ ᱟᱴᱠᱟᱣ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।")
                        "ch01_s08" -> s.copy(title = "ᱟᱨᱦᱚᱸ ᱢᱤᱫ ᱵᱟᱪᱷᱟᱣ", storyText = "ᱨᱟᱡᱩ ᱟᱴᱠᱟᱣ ᱥᱮᱫ ᱵᱟᱝ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱩᱱᱤ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨᱮ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ ᱟᱨ ᱴᱷᱟᱶ ᱥᱟᱯᱷᱟ ᱞᱟᱹᱜᱤᱫ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ।")
                        "ch01_s09" -> s.copy(title = "ᱠᱟᱹᱢᱤ ᱞᱟᱦᱟᱜ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱭᱟᱜ ᱮᱢ ᱟᱠᱟᱱ ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱪᱮᱫ ᱵᱚᱛᱚᱨᱟᱱ ᱵᱟᱝ ᱦᱩᱭ ᱞᱮᱱᱟ। ᱥᱮᱸᱜᱮᱞ ᱥᱮ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱝ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ, ᱢᱮᱱᱠᱷᱟᱱ ᱢᱤᱫ ᱦᱩᱰᱤᱧ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱧᱮᱞ ᱠᱟᱛᱮ ᱞᱚᱜᱚᱱ ᱥᱟᱯᱲᱟᱣ ᱮᱱᱟ।")
                        "ch01_s10" -> s.copy(title = "ᱚᱱᱟ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ, ᱮᱴᱟᱜ ᱨᱟᱡᱩ", storyText = "ᱛᱟᱭᱚᱢ ᱛᱮ, ᱨᱟᱡᱩ ᱟᱨᱦᱚᱸ ᱢᱮᱥᱤᱱ ᱡᱟᱯᱟᱜ ᱛᱮ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱱᱤᱭᱟᱹ ᱫᱷᱟᱣ ᱩᱱᱤ ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱞᱟᱦᱟᱨᱮ ᱟᱯᱱᱟᱨ ᱛᱮᱜᱮ ᱟᱰᱮ ᱯᱟᱥᱮ ᱧᱮᱞ ᱠᱮᱫ-ᱟ।")
                        else -> s
                    }
                }
            )
            "chapter_02" -> ch.copy(
                title = "ᱡᱚᱠᱷᱚᱱ ᱫᱷᱩᱶᱟᱹ ᱧᱮᱞᱚᱜ-ᱟ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch02_s01" -> s.copy(title = "ᱨᱟᱡᱩ ᱮᱴᱟᱜ ᱥᱚ ᱟᱹᱭᱠᱟᱹᱣᱟ", storyText = "ᱛᱤᱱᱟᱹᱜ ᱫᱤᱱ ᱛᱟᱭᱚᱢ, ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱡᱚᱠᱷᱮᱡ ᱮᱴᱟᱜ ᱥᱚ ᱟᱹᱭᱠᱟᱹᱣ ᱠᱮᱫ-ᱟ। ᱤᱱᱟᱹ ᱛᱟᱭᱚᱢ, ᱩᱱᱤ ᱡᱟᱯᱟᱜ ᱥᱟᱯᱟᱵ ᱠᱷᱚᱱ ᱫᱷᱩᱶᱟᱹ ᱚᱰᱚᱠᱚᱜ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱛᱤᱸᱜᱩ ᱮᱱᱟ।")
                        "ch02_s02" -> s.copy(title = "ᱫᱷᱩᱶᱟᱹ ᱡᱟᱹᱥᱛᱤᱜ-ᱟ", storyText = "ᱫᱷᱩᱶᱟᱹ ᱞᱚᱜᱚᱱ ᱧᱮᱞᱚᱜ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱠᱟᱹᱴᱤᱡ ᱫᱷᱩᱶᱟᱹ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱢᱟᱱᱟᱣ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱥᱟᱹᱜᱤᱧ ᱪᱟᱞᱟᱣ ᱮᱱᱟ ᱟᱨ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ।")
                        "ch02_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?", storyText = "ᱨᱟᱡᱩ ᱥᱟᱯᱟᱵ ᱠᱷᱚᱱ ᱫᱷᱩᱶᱟᱹ ᱚᱰᱚᱠᱚᱜ ᱧᱮᱞ ᱫᱟᱲᱮᱭᱟᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ।", decision = s.decision?.copy(question = "ᱥᱟᱯᱟᱵ ᱠᱷᱚᱱ ᱫᱷᱩᱶᱟᱹ ᱚᱰᱚᱠᱚᱜ ᱠᱟᱱᱟ। ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱥᱩᱨ ᱪᱟᱞᱟᱣ ᱠᱟᱛᱮ ᱧᱮᱞ ᱢᱮ", "ᱥᱮᱸᱜᱮᱞ ᱵᱟᱹᱱᱩᱜ ᱠᱷᱟᱱ ᱟᱞᱚᱢ ᱫᱷᱭᱟᱱᱟ", "ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ/ᱞᱟᱹᱭ ᱢᱮ ᱟᱨ ᱥᱟᱹᱜᱤᱧ ᱛᱟᱦᱮᱸᱱ ᱢᱮ", "ᱥᱟᱯᱟᱵ ᱡᱚᱴᱮᱫ ᱠᱟᱛᱮ ᱞᱚᱞᱚ ᱡᱟᱥ ᱢᱮ"), feedback = listOf("ᱥᱩᱨ ᱪᱟᱞᱟᱣ ᱞᱮᱠᱷᱟᱱ ᱫᱷᱩᱶᱟᱹ ᱟᱨ ᱞᱚᱞᱚ ᱛᱮ ᱡᱚᱠᱷᱤᱢ ᱦᱩᱭᱩᱜ-ᱟ।", "ᱫᱷᱩᱶᱟᱹ ᱫᱚ ᱢᱟᱨᱟᱝ ᱵᱚᱛᱚᱨ ᱨᱮᱱᱟᱜ ᱪᱤᱱᱦᱟᱹ ᱠᱟᱱᱟ, ᱵᱟᱹᱜᱤ ᱫᱚ ᱵᱟᱹᱲᱤᱡ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱨᱟᱡᱩ ᱞᱟᱹᱭ ᱟᱠᱚᱣᱟ ᱟᱨ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱛᱟᱦᱮᱸᱱ-ᱟ।", "ᱥᱟᱯᱟᱵ ᱡᱚᱴᱮᱫ ᱫᱚ ᱥᱮᱸᱜᱮᱞ ᱡᱟᱥ ᱨᱮᱱᱟᱜ ᱴᱷᱤᱠ ᱦᱚᱨ ᱵᱟᱝ ᱠᱟᱱᱟ।")))
                        "ch02_s04" -> s.copy(title = "ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱮᱱᱟ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱠᱮᱫ-ᱟ। ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ।")
                        "ch02_s05" -> s.copy(title = "ᱨᱟᱡᱩ ᱦᱚᱨ ᱫᱤᱥᱟᱹᱭᱟ", storyText = " orient ᱡᱚᱠᱷᱮᱡ, ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱪᱤᱱᱦᱟᱹ ᱧᱮᱞ ᱞᱮᱫ-ᱟ। ᱱᱤᱛᱚᱜ ᱚᱱᱟ ᱪᱤᱱᱦᱟᱹ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱮᱱᱟ। ᱩᱱᱤ ᱧᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱵᱟᱝ ᱛᱤᱸᱜᱩ ᱮᱱᱟ।")
                        "ch02_s06" -> s.copy(title = "ᱚᱠᱚᱭ ᱪᱚᱝ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱥᱟᱱᱟᱭᱮᱫᱮᱭᱟ", storyText = "ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱢᱮᱱ ᱠᱮᱫ-ᱟ ᱡᱤᱱᱤᱥ ᱵᱟᱹᱜᱤ ᱟᱠᱟᱱᱟ ᱟᱨ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱩᱱᱤ ᱟᱴᱠᱟᱣ ᱠᱮᱫᱮᱭᱟ। \"ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱟᱠᱟᱱᱟ, ᱵᱚᱱ ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱟ,\" ᱩᱱᱤ ᱢᱮᱱ ᱠᱮᱫ-ᱟ।")
                        "ch02_s07" -> s.copy(title = "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ", storyText = "ᱨᱟᱡᱩ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ ᱥᱮᱴᱮᱨ ᱮᱱᱟ। ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱞᱮᱠᱷᱟ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱚᱸᱰᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch02_s08" -> s.copy(title = "ᱨᱟᱡᱩ ᱛᱟᱺᱜᱤᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱱᱤᱛᱚᱜ ᱦᱚᱸ ᱫᱷᱩᱶᱟᱹ ᱧᱮᱞ ᱫᱟᱲᱮᱭᱟᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ। ᱩᱱᱤ ᱧᱮᱞ ᱥᱟᱱᱟᱭᱮᱫᱮ ᱛᱟᱦᱮᱸᱱ, ᱢᱮᱱᱠᱷᱟᱱ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱪᱟᱞᱟᱜ ᱨᱮᱱᱟᱜ ᱠᱟᱛᱷᱟ ᱵᱟᱝ ᱠᱟᱱᱟ। ᱩᱱᱤ ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch02_s09" -> s.copy(title = "ᱦᱟᱞᱚᱛ ᱥᱟᱯᱲᱟᱣ ᱮᱱᱟ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱦᱟᱞᱚᱛ ᱥᱟᱯᱲᱟᱣ ᱠᱟᱛᱮ, ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱤᱱᱟᱹ ᱛᱟᱭᱚᱢ ᱠᱟᱹᱢᱤ ᱞᱟᱹᱭ ᱟᱠᱚᱣᱟ। ᱨᱟᱡᱩ ᱫᱷᱩᱶᱟᱹ ᱠᱚᱢ ᱮᱱ ᱛᱮᱜᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱢᱟᱱᱟᱣ ᱠᱮᱫ-ᱟ।")
                        "ch02_s10" -> s.copy(title = "ᱟᱨᱦᱚᱸ ᱠᱟᱹᱢᱤ ᱨᱮ", storyText = "ᱡᱚᱠᱷᱚᱱ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱞᱟᱦᱟᱜ ᱠᱟᱛᱷᱟ ᱞᱟᱹᱭ ᱠᱮᱫ-ᱟ, ᱨᱟᱡᱩ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱨᱩᱣᱟᱹᱲ ᱮᱱᱟ। ᱫᱷᱩᱶᱟᱹ ᱠᱟᱹᱴᱤᱡ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ, ᱢᱮᱱᱠᱷᱟᱱ ᱴᱷᱤᱠ ᱨᱩᱣᱟᱹᱲ ᱦᱟᱞᱚᱛ ᱵᱟᱹᱲᱤᱡᱚᱜ ᱞᱟᱦᱟᱨᱮ ᱮᱦᱚᱵᱽ ᱞᱮᱱᱟ।")
                        else -> s
                    }
                }
            )
            "chapter_03" -> ch.copy(
                title = "ᱨᱩᱠᱷᱤᱭᱟᱹ ᱚᱰᱚᱠᱚᱜ ᱦᱚᱨ ᱧᱟᱢ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch03_s01" -> s.copy(title = "ᱥᱟ ordinary ᱠᱟᱹᱢᱤ ᱵᱚᱫᱚᱞᱚᱜ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱞᱟᱛᱟᱨ ᱠᱷᱟᱫᱟᱱ ᱨᱮ ᱠᱟᱹᱢᱤ ᱮᱫ ᱛᱟᱦᱮᱸᱱ ᱡᱚᱠᱷᱚᱱ ᱟᱯᱟᱛ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱮᱱᱟ। ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱠᱟᱹᱢᱤ ᱵᱟᱹᱜᱤ ᱠᱟᱛᱮ ᱟᱯᱟᱛ ᱟᱹᱱ ᱠᱚᱨᱟᱣ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱵᱟᱰᱟᱭ ᱠᱮᱫ-ᱟ ᱡᱮ ᱩᱱᱤ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱛᱮ ᱚᱰᱚᱠᱚᱜ ᱞᱟᱹᱠᱛᱤ।")
                        "ch03_s02" -> s.copy(title = "ᱨᱟᱡᱩ ᱦᱚᱨ ᱧᱮᱞᱟ", storyText = "ᱨᱟᱡᱩ ᱚᱨᱤᱭᱮᱱᱴᱮᱥᱚᱱ ᱦᱚᱨ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱪᱤᱱᱦᱟᱹ ᱠᱚ ᱧᱮᱞ ᱠᱮᱫ-ᱟ। ᱦᱚᱨ ᱨᱮᱱᱟᱜ ᱢᱤᱫ ᱴᱷᱟᱶ ᱚᱸᱰᱚᱜ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ, ᱟᱨ ᱩᱱᱤ ᱵᱟᱪᱷᱟᱣ ᱦᱩᱭ ᱟᱭ ᱛᱟᱦᱮᱸᱱ।")
                        "ch03_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱚᱠᱟ ᱦᱚᱨ ᱛᱮ ᱪᱟᱞᱟᱜ-ᱟ?", storyText = "ᱢᱤᱫ ᱦᱚᱨ ᱠᱷᱟᱴᱚ ᱧᱮᱞᱚᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ, ᱢᱮᱱᱠᱷᱟᱱ ᱚᱠᱟ ᱥᱮᱫ ᱪᱟᱞᱟᱜ-ᱟ ᱵᱟᱝ ᱵᱟᱰᱟᱭ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱮᱴᱟᱜᱟᱜ ᱫᱚ ᱟᱯᱟᱛ ᱦᱚᱨ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ।", decision = s.decision?.copy(question = "ᱨᱟᱡᱩ ᱚᱠᱟ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱠᱷᱟᱴᱚ ᱧᱮᱞᱚᱜ ᱠᱟᱱ ᱦᱚᱨ", "ᱡᱟᱹᱥᱛᱤ ᱦᱚᱲ ᱪᱟᱞᱟᱜ ᱠᱟᱱ ᱦᱚᱨ", "ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱚᱠᱟ ᱦᱚᱨ ᱦᱚᱸ"), feedback = listOf("ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱫᱚ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱥᱮ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱵᱟᱝ ᱦᱩᱭ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।", "ᱦᱚᱲ ᱯᱟᱸᱡᱟ ᱫᱚ ᱟᱯᱟᱛ ᱟᱹᱱ ᱨᱮᱱᱟᱜ ᱴᱷᱟᱶ ᱵᱟᱝ ᱤᱫᱤᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱨᱟᱡᱩ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱞᱟᱹᱠᱛᱤ।", "ᱠᱷᱟᱹᱞᱤ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱪᱟᱞᱟᱣ ᱫᱚ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱮᱢᱟ।")))
                        "ch03_s04" -> s.copy(title = "ᱨᱟᱡᱩ ᱪᱤᱱᱦᱟᱹ ᱯᱟᱸᱡᱟᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱪᱤᱱᱦᱟᱹ ᱯᱟᱸᱡᱟ ᱠᱟᱛᱮ ᱵᱟᱝ ᱫᱟᱹᱲ ᱠᱟᱛᱮ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱮᱴᱟᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱥᱮᱫ ᱪᱟᱞᱟᱣ ᱮᱱᱟ।")
                        "ch03_s05" -> s.copy(title = "ᱧᱮᱞᱚᱜ ᱟᱴᱠᱟᱣᱚᱜ-ᱟ", storyText = "ᱦᱚᱨ ᱨᱮ ᱧᱮᱞᱚᱜ ᱟᱸᱴ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱠᱷᱟᱴᱚ ᱮᱱᱟ, ᱦᱩᱥᱤᱭᱟᱹᱨ ᱛᱟᱦᱮᱸ ᱮᱱᱟ ᱟᱨ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ।")
                        "ch03_s06" -> s.copy(title = "ᱚᱠᱚᱭ ᱪᱚᱝ ᱛᱟᱭᱚᱢᱚᱜ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱪᱟᱞᱟᱜ ᱨᱮ ᱟᱸᱴᱚᱜ ᱠᱟᱱᱟ। ᱩᱱᱤ ᱦᱚᱨ ᱵᱟᱹᱜᱤ ᱠᱟᱛᱮ ᱵᱟᱝ ᱪᱟᱞᱟᱣ ᱠᱟᱛᱮ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ।")
                        "ch03_s07" -> s.copy(title = "ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱥᱮᱴᱮᱨ", storyText = "ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱠᱚ ᱥᱮᱴᱮᱨ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱛᱮ ᱪᱟᱞᱟᱣ ᱮᱱᱟ ᱟᱨ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱞᱮᱠᱷᱟ ᱡᱚᱠᱷᱮᱡ ᱚᱸᱰᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch03_s08" -> s.copy(title = "ᱨᱟᱡᱩ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱥᱟᱱᱟᱭᱮᱫᱮᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱵᱩᱡᱷᱟᱹᱣ ᱠᱮᱫ-ᱟ ᱟᱭᱟᱜ ᱥᱟᱯᱟᱵ ᱛᱷᱚᱞᱟ ᱵᱷᱤᱛᱨᱤ ᱨᱮ ᱵᱟᱹᱜᱤ ᱟᱠᱟᱱᱟ। ᱢᱤᱫ ᱜᱷᱟᱹᱲᱤᱡ ᱞᱟᱹᱜᱤᱫ ᱩᱱᱤ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ, ᱤᱱᱟᱹ ᱛᱟᱭᱚᱢ ᱚᱸᱰᱮ ᱜᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch03_s09" -> s.copy(title = "ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱦᱩᱠᱩᱢ ᱮᱢᱟ", storyText = "ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱡᱚᱛᱚ ᱦᱚᱲᱮ ᱫᱤᱥᱟᱹ ᱟᱫᱽ ᱠᱚᱣᱟ ᱡᱮ ᱵᱟᱝ ᱦᱩᱠᱩᱢ ᱛᱮ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱟᱞᱚᱯᱮ ᱵᱚᱞᱚᱱᱟ। ᱨᱟᱡᱩ ᱟᱸᱡᱚᱢ ᱠᱮᱫ-ᱟ। ᱟᱭᱟᱜ ᱛᱷᱚᱞᱟ ᱛᱟᱺᱜᱤ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।")
                        "ch03_s10" -> s.copy(title = "ᱚᱱᱟ ᱦᱚᱨ ᱡᱟᱦᱟᱸ ᱨᱟᱡᱩ ᱯᱨᱮᱠᱴᱤᱥ ᱞᱮᱫ-ᱟ", storyText = "ᱛᱟᱭᱚᱢ ᱛᱮ, ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱦᱚᱨ ᱵᱟᱵᱚᱛ ᱛᱮ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ। ᱜᱷᱟᱱᱴᱤ ᱡᱚᱠᱷᱮᱡ, ᱚᱠᱟ ᱪᱟᱞᱟᱜ ᱵᱟᱰᱟᱭ ᱛᱮ ᱚᱠᱛᱚ ᱵᱟᱝ ᱵᱟᱹᱲᱤᱡ ᱠᱟᱛᱮ ᱪᱟᱞᱟᱣ ᱮᱱᱟ।")
                        else -> s
                    }
                }
            )
            "chapter_04" -> ch.copy(
                title = "ᱥᱮᱸᱜᱮᱞ ᱤᱬᱤᱡ ᱥᱟᱯᱟᱵ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch04_s01" -> s.copy(title = "ᱢᱤᱫ ᱦᱩᱰᱤᱧ ᱥᱮᱸᱜᱮᱞ", storyText = "ᱨᱟᱡᱩ ᱚᱸᱰᱮ ᱠᱟᱹᱢᱤ ᱮᱫ ᱛᱟᱦᱮᱸᱱ ᱡᱟᱦᱟᱸ ᱦᱩᱰᱤᱧ ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞ ᱞᱮᱱᱟ। ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱞᱟᱹᱭ ᱟᱫᱮ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱨᱟᱡᱩ ᱥᱩᱨ ᱨᱮ ᱥᱮᱸᱜᱮᱞ ᱤᱬᱤᱡ ᱥᱟᱯᱟᱵ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱟᱨ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ ᱡᱮ ᱥᱟᱯᱟᱵ ᱫᱚ ᱠᱷᱟᱹᱞᱤ ᱴᱷᱤᱠ ᱦᱚᱲ ᱜᱮ ᱵᱮᱣᱦᱟᱨ ᱞᱟᱹᱠᱛᱤ।")
                        "ch04_s02" -> s.copy(title = "ᱨᱟᱡᱩ ᱥᱮᱸᱜᱮᱞ ᱧᱮᱞᱟ", storyText = "ᱥᱮᱸᱜᱮᱞ ᱦᱩᱰᱤᱧ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ, ᱢᱮᱱᱠᱷᱟᱱ ᱨᱟᱡᱩ ᱥᱩᱨ ᱪᱟᱞᱟᱜ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱢᱟᱱᱟᱣ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch04_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱥᱟᱯᱟᱵ ᱵᱮᱣᱦᱟᱨ ᱞᱟᱹᱠᱛᱤ?", storyText = "ᱨᱟᱡᱩ ᱦᱩᱰᱤᱧ ᱥᱮᱸᱜᱮᱞ ᱟᱨ ᱥᱟᱯᱟᱵ ᱧᱮᱞᱮᱫ-ᱟ।", decision = s.decision?.copy(question = "ᱥᱟᱯᱟᱵ ᱵᱮᱣᱦᱟᱨ ᱞᱟᱦᱟᱨᱮ ᱨᱟᱡᱩ ᱪᱮᱫ ᱵᱷᱟᱵᱤ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱟᱭᱟᱜ ᱴᱷᱤᱠ ᱪᱮᱫᱚᱜ/ᱦᱩᱠᱩᱢ ᱢᱮᱱᱟᱜ-ᱟ ᱥᱮ ᱵᱟᱝ ᱟᱨ ᱴᱷᱟᱶ ᱴᱷᱤᱠ ᱜᱮᱭᱟ ᱥᱮ ᱵᱟᱝ", "ᱥᱟᱯᱟᱵ ᱱᱟᱶᱟ ᱧᱮᱞᱚᱜ ᱠᱟᱱᱟ ᱥᱮ ᱵᱟᱝ", "ᱮᱴᱟᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱧᱮᱞᱮᱫ-ᱟ ᱥᱮ ᱵᱟᱝ", "ᱥᱮᱸᱜᱮᱞ ᱨᱟᱹᱥᱠᱟᱹ ᱧᱮᱞᱚᱜ ᱠᱟᱱᱟ ᱥᱮ ᱵᱟᱝ"), feedback = listOf("ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱪᱮᱫᱚᱜ, ᱦᱩᱠᱩᱢ, ᱴᱷᱤᱠ ᱥᱟᱯᱟᱵ ᱟᱨ ᱟᱹᱱ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ।", "ᱧᱮᱞᱚᱜ ᱛᱮᱜᱮ ᱥᱟᱯᱟᱵ ᱴᱷᱤᱠ ᱥᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱵᱟᱰᱟᱭᱚᱜ-ᱟ।", "ᱦᱚᱲ ᱧᱮᱞ ᱛᱮ ᱵᱟᱹᱲᱤᱡ ᱠᱟᱹᱢᱤ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱵᱮᱱᱟᱣᱚᱜ-ᱟ।", "ᱧᱮᱞ ᱥᱟᱱᱟ ᱛᱮ ᱥᱮᱸᱜᱮᱞ ᱥᱩᱨ ᱪᱟᱞᱟᱜ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")))
                        "ch04_s04" -> s.copy(title = "ᱥᱟᱯᱟᱵ ᱡᱟᱥ", storyText = "ᱪᱮᱫᱚᱜ ᱟᱠᱟᱱ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱴᱷᱤᱠ ᱥᱟᱯᱟᱵ ᱵᱟᱪᱷᱟᱣ ᱠᱮᱫ-ᱟ, ᱡᱟᱥ ᱠᱮᱫ-ᱟ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱛᱤᱸᱜᱩ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱥᱟᱹᱜᱤᱧ ᱠᱷᱚᱱ ᱧᱮᱞ ᱠᱮᱫ-ᱟ।")
                        "ch04_s05" -> s.copy(title = "ᱴᱷᱤᱠ ᱥᱟᱯᱟᱵ ᱵᱟᱪᱷᱟᱣ", storyText = "ᱠᱟᱹᱢᱤᱭᱟᱹ ᱡᱟᱥ ᱠᱮᱫ-ᱟ ᱡᱮ ᱥᱟᱯᱟᱵ ᱫᱚ ᱥᱮᱸᱜᱮᱞ ᱞᱮᱠᱟᱛᱮ ᱴᱷᱤᱠ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱨᱟᱡᱩ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱡᱮ ᱡᱚᱛᱚ ᱥᱟᱯᱟᱵ ᱢᱤᱫ ᱜᱮ ᱵᱟᱝ ᱠᱟᱱᱟ।")
                        "ch04_s06" -> s.copy(title = "ᱥᱟᱯᱟᱵ ᱵᱮᱣᱦᱟᱨ", storyText = "ᱪᱮᱫᱚᱜ ᱟᱠᱟᱱ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱥᱟᱯᱟᱵ ᱵᱮᱣᱦᱟᱨ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱥᱟᱹᱜᱤᱧ ᱨᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch04_s07" -> s.copy(title = "ᱥᱮᱸᱜᱮᱞ ᱠᱚᱢᱚᱜ-ᱟ", storyText = "ᱥᱮᱸᱜᱮᱞ ᱦᱩᱰᱤᱧ ᱮᱱᱟ। ᱠᱟᱹᱢᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱠᱟᱛᱮ ᱥᱮᱸᱜᱮᱞ ᱤᱬᱤᱡ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱥᱩᱨ ᱵᱟᱝ ᱪᱟᱞᱟᱣ ᱮᱱᱟ।")
                        "ch04_s08" -> s.copy(title = "ᱴᱷᱟᱶ ᱟᱯᱱᱟᱨ ᱛᱮᱜᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱮᱱᱟ?", storyText = "ᱨᱟᱡᱩ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ ᱠᱟᱹᱢᱤ ᱯᱩᱨᱟᱹᱣ ᱮᱱᱟ ᱥᱮ ᱵᱟᱝ। ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ ᱡᱮ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱛᱮᱜᱮ ᱴᱷᱟᱶ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱵᱟᱝ ᱦᱩᱭᱩᱜ-ᱟ।")
                        "ch04_s09" -> s.copy(title = "ᱨᱟᱡᱩ ᱛᱟᱭᱚᱢ ᱨᱮ ᱛᱟᱦᱮᱸᱱ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱵᱟᱦᱨᱮ ᱨᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ ᱟᱨ ᱵᱟᱝ ᱦᱩᱠᱩᱢ ᱛᱮ ᱥᱟᱯᱟᱵ ᱵᱟᱝ ᱡᱚᱴᱮᱫ ᱠᱮᱫ-ᱟ।")
                        "ch04_s10" -> s.copy(title = "ᱥᱟᱯᱟᱵ ᱫᱚ ᱠᱷᱟᱹᱞᱤ ᱡᱤᱱᱤᱥ ᱵᱟᱝ ᱠᱟᱱᱟ", storyText = "ᱛᱟᱭᱚᱢ ᱛᱮ, ᱨᱟᱡᱩ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ ᱪᱮᱫᱟᱜ ᱥᱟᱯᱟᱵ ᱴᱷᱟᱶ ᱵᱟᱰᱟᱭ ᱞᱟᱹᱠᱛᱤ—ᱟᱨ ᱪᱮᱫᱟᱜ ᱵᱟᱝ ᱪᱮᱫᱚᱜ ᱟᱠᱟᱱ ᱦᱚᱲ ᱥᱟᱯᱟᱵ ᱵᱟᱝ ᱵᱮᱣᱦᱟᱨ ᱞᱟᱹᱠᱛᱤ।")
                        else -> s
                    }
                }
            )
            "chapter_05" -> ch.copy(
                title = "ᱡᱚᱠᱷᱚᱱ ᱥᱮᱸᱜᱮᱞ ᱯᱚᱥᱟᱜ ᱵᱚᱛᱚᱨ ᱵᱮᱱᱟᱣᱚᱜ-ᱟ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch05_s01" -> s.copy(title = "ᱮᱴᱟᱜ ᱞᱮᱠᱟᱱ ᱵᱚᱛᱚᱨ", storyText = "ᱨᱟᱡᱩ ᱚᱸᱰᱮ ᱠᱟᱹᱢᱤ ᱮᱫ ᱛᱟᱦᱮᱸᱱ ᱡᱟᱦᱟᱸ ᱡᱩᱞᱩᱜ ᱡᱤᱱᱤᱥ ᱟᱨ ᱥᱮᱸᱜᱮᱞ ᱥᱟᱯᱲᱟᱣ ᱞᱟᱹᱠᱛᱤ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱩᱱᱤ ᱠᱮᱴᱮᱡ ᱥᱚ ᱟᱹᱭᱠᱟᱹᱣ ᱠᱮᱫ-ᱟ ᱟᱨ ᱦᱚᱭ ᱪᱟᱞᱟᱣ ᱵᱟᱝ ᱴᱷᱤᱠ ᱧᱮᱞ ᱠᱮᱫ-ᱟ।")
                        "ch05_s02" -> s.copy(title = "ᱨᱟᱡᱩ ᱛᱤᱸᱜᱩᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱡᱩᱞ ᱠᱮᱫ-ᱟ ᱟᱨ ᱵᱟᱝ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱠᱟᱹᱢᱤ ᱵᱟᱝ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱪᱟᱞᱟᱣ ᱮᱱᱟ ᱟᱨ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ।")
                        "ch05_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱷᱚᱱ ᱥᱟᱦᱟ blind ᱞᱟᱹᱠᱛᱤ?", storyText = "ᱨᱟᱡᱩ ᱵᱷᱟᱵᱤ ᱮᱫ-ᱟ ᱡᱮ ᱡᱩᱞᱩᱜ ᱦᱚᱭ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।", decision = s.decision?.copy(question = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱷᱚᱱ ᱥᱟᱦᱟ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱞᱟᱹᱭ", "ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞᱩᱜ ᱠᱟᱹᱢᱤ ᱥᱮ ᱵᱟᱝ ᱦᱩᱠᱩᱢ ᱠᱟᱹᱢᱤ ᱠᱚᱨᱟᱣ", "ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱪᱟᱞᱟᱣ"), feedback = listOf("ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱫᱚ ᱴᱷᱤᱠ ᱠᱟᱹᱢᱤ ᱠᱟᱱᱟ।", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱞᱟᱹᱭ ᱛᱮ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱠᱚ ᱠᱟᱹᱢᱤ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱡᱩᱞᱩᱜ ᱦᱚᱭ ᱛᱟᱦᱮᱸᱱ ᱨᱮ ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞ ᱞᱮᱠᱷᱟᱱ ᱵᱚᱛᱚᱨ ᱵᱟ push-ᱟ।", "ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱪᱟᱞᱟᱣ ᱫᱚ ᱟᱯᱟᱛ ᱟᱹᱱ ᱨᱮᱱᱟᱜ ᱴᱷᱟᱶ ᱠᱟᱱᱟ।")))
                        "ch05_s04" -> s.copy(title = "ᱴᱷᱟᱶ ᱟᱴᱠᱟᱣ ᱮᱱᱟ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱵᱚᱞᱚᱱ ᱟᱴᱠᱟᱣ ᱠᱮᱫ-ᱟ। ᱚᱠᱚᱭ ᱦᱚᱸ ᱠᱷᱟᱹᱞᱤ ᱧᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱵᱚᱞᱚᱱ ᱵᱟᱝ ᱪᱷᱟᱹᱲ ᱮᱱᱟ।")
                        "ch05_s05" -> s.copy(title = "ᱦᱚᱭ ᱪᱟᱞᱟᱣ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ", storyText = "ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ ᱡᱮ ᱴᱷᱟᱶ ᱡᱟᱥ ᱞᱟᱹᱠᱛᱤ। ᱡᱟᱦᱟᱸ ᱡᱩᱞᱩᱜ ᱜᱮᱥ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ, ᱚᱸᱰᱮ ᱦᱚᱭ ᱪᱟᱞᱟᱣ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ।")
                        "ch05_s06" -> s.copy(title = "ᱵᱚᱞᱚᱱ ᱞᱟᱦᱟᱨᱮ ᱡᱟᱥ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱡᱟᱥ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱵᱟᱦᱨᱮ ᱨᱮ ᱛᱟᱦᱮᱸ ᱠᱟᱛᱮ ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch05_s07" -> s.copy(title = "ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱩᱠᱞᱤ ᱠᱩᱞᱤᱭᱟ", storyText = "ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱩᱞᱤ ᱠᱮᱫ-ᱟ ᱪᱮᱫᱟᱜ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱨᱮᱦᱚᱸ ᱥᱟᱹᱜᱤᱧ ᱨᱮ ᱫᱚᱦᱚ ᱦᱩᱭᱩᱜ ᱠᱟᱱᱟ। ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ ᱡᱮ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱨᱮᱦᱚᱸ ᱵᱚᱛᱚᱨ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।")
                        "ch05_s08" -> s.copy(title = "ᱨᱟᱡᱩ ᱵᱷᱮᱜᱟᱨ ᱵᱩᱡᱷᱟᱹᱣᱟ", storyText = "ᱨᱟᱡᱩ ᱥᱩᱱᱩᱢᱟᱱ ᱞᱩᱜᱽᱲᱤ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ। ᱚᱱᱟ ᱫᱚ ᱧᱮᱞᱚᱜ ᱥᱮᱸᱜᱮᱞ ᱵᱚᱛᱚᱨ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱱᱚᱣᱟ ᱫᱚ ᱞᱟᱹᱭ ᱠᱮᱫ-ᱟ ᱡᱮ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱨᱮᱦᱚᱸ ᱵᱚᱛᱚᱨ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।")
                        "ch05_s09" -> s.copy(title = "ᱴᱷᱟᱶ ᱡᱟᱥ ᱮᱱᱟ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱡᱟᱥ ᱯᱩᱨᱟᱹᱣ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱞᱟᱦᱟᱨᱮ ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch05_s10" -> s.copy(title = "ᱟᱴᱠᱟᱣ ᱫᱚ ᱥᱮᱸᱜᱮᱞ ᱞᱟᱦᱟᱨᱮ ᱦᱩᱭᱩᱜ-ᱟ", storyText = "ᱦᱩᱠᱩᱢ ᱧᱟᱢ ᱠᱟᱛᱮ ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱛᱮ ᱨᱩᱣᱟᱹᱲ ᱮᱱᱟ। ᱩᱱᱤ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ ᱪᱮᱫᱮᱠᱟ ᱵᱟᱱᱟᱨ ᱴᱷᱟᱶ ᱨᱮ ᱢᱟᱨᱟᱝ ᱥᱮᱸᱜᱮᱞ ᱞᱟᱦᱟᱨᱮ ᱠᱟᱹᱢᱤ ᱞᱟᱹᱠᱛᱤ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ।")
                        else -> s
                    }
                }
            )
            "chapter_06" -> ch.copy(
                title = "ᱟᱯᱟᱛ ᱵᱟᱪᱷᱟᱣ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch06_s01" -> s.copy(title = "ᱜᱷᱟᱱᱴᱤ ᱟᱨᱦᱚᱸ ᱵᱟᱡᱟᱣᱚᱜ-ᱟ", storyText = "ᱛᱤᱱᱟᱹᱜ ᱦᱟᱯᱛᱟ ᱛᱟᱭᱚᱢ, ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱡᱚᱠᱷᱮᱡ ᱟᱯᱟᱛ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱮᱱᱟ। ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱟᱯᱟᱛ ᱦᱚᱨ ᱥᱮᱫ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱞᱚᱜᱚᱱ ᱟᱨ ᱦᱩᱥᱤᱭᱟᱹᱨ ᱛᱮ ᱠᱟᱹᱢᱤ ᱦᱩᱭ ᱟᱭ ᱛᱟᱦᱮᱸᱱ।")
                        "ch06_s02" -> s.copy(title = "ᱨᱟᱡᱩ ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱧᱮᱞᱟ", storyText = "ᱨᱟᱡᱩ ᱢᱤᱫ ᱦᱚᱨ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱡᱟᱦᱟᱸ ᱞᱚᱜᱚᱱ ᱧᱮᱞᱚᱜ ᱠᱟᱱ ᱛᱟᱦᱮᱸᱱ। ᱩᱱᱤ ᱵᱟᱰᱟᱭ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ, ᱢᱮᱱᱠᱷᱟᱱ ᱚᱱᱟ ᱫᱚ ᱟᱯᱟᱛ ᱦᱚᱨ ᱵᱟᱝ ᱛᱟᱦᱮᱸᱠᱟᱱᱟ। ᱛᱤᱱᱟᱹᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱚᱱᱟ ᱥᱮᱫ ᱠᱚ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱛᱤᱸᱜᱩ ᱮᱱᱟ।")
                        "ch06_s03" -> s.copy(title = "ᱠᱷᱟᱴᱚ ᱦᱚᱨ", storyText = "ᱨᱟᱡᱩ ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱟᱨ ᱪᱤᱱᱦᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ ᱛᱟᱞᱟ ᱨᱮ ᱵᱟᱪᱷᱟᱣ ᱦᱩᱭ ᱟᱭᱟ।", decision = s.decision?.copy(question = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱤᱫᱤ ᱢᱮ ᱪᱮᱫᱟᱜ ᱥᱮ ᱞᱚᱜᱚᱱ ᱜᱮᱭᱟ", "ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱨᱮ ᱛᱟᱺᱜᱤ ᱢᱮ", "ᱵᱟᱝ ᱡᱟᱥ ᱠᱟᱛᱮ ᱦᱚᱨ ᱵᱟᱪᱷᱟᱣ ᱢᱮ"), feedback = listOf("ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱫᱚ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱥᱮ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱵᱟᱝ ᱦᱩᱭ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱨᱟᱡᱩ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱦᱚᱨ ᱟᱨ ᱦᱩᱠᱩᱢ ᱯᱟᱸᱡᱟ ᱞᱟᱹᱠᱛᱤ।", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱨᱮ ᱛᱟᱦᱮᱸᱱ ᱛᱮ ᱚᱰᱚᱠᱚᱜ ᱫᱮᱨᱤᱜ-ᱟ ᱟᱨ ᱵᱚᱛᱚᱨ ᱦᱩᱭᱩᱜ-ᱟ।", "ᱵᱟᱝ ᱡᱟᱥ ᱠᱟᱛᱮ ᱦᱚᱨ ᱵᱟᱪᱷᱟᱣ ᱞᱮᱠᱷᱟᱱ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱪᱟᱞᱟᱣ ᱦᱩᱭᱩᱜ-ᱟ।")))
                        "ch06_s04" -> s.copy(title = "ᱨᱟᱡᱩ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱦᱚᱨ ᱵᱟᱪᱷᱟᱣᱟ", storyText = "ᱨᱟᱡᱩ ᱪᱤᱱᱦᱟᱹ ᱟᱠᱟᱱ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱵᱟᱝ ᱫᱟᱹᱲ ᱠᱮᱫ-ᱟ ᱥᱮ ᱵᱟᱝ ᱴᱷᱮᱞᱟ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱦᱩᱠᱩᱢ ᱨᱮ ᱦᱩᱥᱤᱭᱟᱹᱨ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch06_s05" -> s.copy(title = "ᱞᱟᱹᱭ ᱥᱚᱫᱚᱨ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱝ ᱟᱸᱡᱚᱢ ᱞᱮᱫ-ᱟ। ᱩᱱᱤ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱞᱟᱹᱭ ᱟᱫᱮᱭᱟ, ᱡᱟᱦᱟᱸᱭ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱴᱷᱤᱠ ᱦᱚᱨ ᱥᱮᱫ ᱠᱩᱞ ᱠᱮᱫᱮᱭᱟ।")
                        "ch06_s06" -> s.copy(title = "ᱨᱟᱡᱩ ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱥᱮᱴᱮᱨᱚᱜ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ ᱥᱮᱴᱮᱨ ᱮᱱᱟ। ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱞᱮᱠᱷᱟ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱨᱟᱡᱩ ᱟᱭᱟᱜ ᱴᱷᱟᱶ ᱨᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch06_s07" -> s.copy(title = "ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱵᱟᱹᱱᱩᱜ-ᱟ", storyText = "ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱧᱟᱢ ᱠᱮᱫ-ᱟ ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱵᱟᱝ ᱥᱮᱴᱮᱨ ᱟᱠᱟᱱᱟ। ᱛᱤᱱᱟᱹᱜ ᱦᱚᱲ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱠᱚ ᱥᱟᱱᱟᱭᱮᱫ ᱠᱚᱣᱟ। ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱴᱷᱟᱶ ᱵᱚᱞᱚᱱ ᱦᱩᱠᱩᱢ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ।")
                        "ch06_s08" -> s.copy(title = "ᱨᱟᱡᱩ ᱴᱤᱢ ᱛᱟᱺᱜᱤᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱮᱴᱟᱜ ᱦᱚᱲ ᱟᱯᱱᱟᱨ ᱛᱮ ᱵᱚᱞᱚᱱ ᱵᱟᱝ ᱠᱮᱫ ᱠᱚᱣᱟ। ᱟᱯᱟᱛ ᱴᱤᱢ ᱞᱟᱹᱭ ᱟᱠᱚᱣᱟ।")
                        "ch06_s09" -> s.copy(title = "ᱠᱟᱹᱢᱤᱭᱟᱹ ᱧᱟᱢ ᱮᱱᱟ", storyText = "ᱟᱯᱟᱛ ᱴᱤᱢ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱦᱟᱞᱚᱛ ᱥᱟᱯᱲᱟᱣ ᱠᱮᱫ-ᱟ। ᱵᱟᱹᱱᱩᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱧᱟᱢ ᱮᱱᱟ।")
                        "ch06_s10" -> s.copy(title = "ᱨᱟᱡᱩ ᱛᱟᱭᱚᱢ ᱧᱮᱞᱟ", storyText = "ᱨᱟᱡᱩ ᱜᱷᱟᱱᱴᱤ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱛᱟᱞᱟ ᱨᱮᱱᱟᱜ ᱚᱠᱛᱚ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱟᱯᱱᱟᱨ ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱵᱟᱝ ᱵᱮᱱᱟᱣ ᱠᱟᱛᱮ ᱟᱹᱱ ᱟᱯᱟᱛ ᱦᱚᱨ ᱵᱟᱪᱷᱟᱣ ᱞᱮᱫ-ᱟ।")
                        else -> s
                    }
                }
            )
            "chapter_07" -> ch.copy(
                title = "ᱟᱯᱟᱛ ᱴᱷᱟᱶ ᱨᱮ ᱨᱟᱡᱩ",
                screens = ch.screens.map { s ->
                    when (s.id) {
                        "ch07_s01" -> s.copy(title = "ᱠᱟᱹᱢᱤ ᱥᱟ ordinary ᱮᱦᱚᱵᱽ ᱮᱱᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱨᱦᱚᱸ ᱢᱤᱫ ᱥᱟ ordinary ᱠᱟᱹᱢᱤ ᱮᱦᱚᱵᱽ ᱠᱮᱫ-ᱟ। ᱛᱚᱵᱮ ᱜᱮ ᱟᱯᱟᱛ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ ᱮᱱᱟ।")
                        "ch07_s02" -> s.copy(title = "ᱫᱷᱩᱶᱟᱹ ᱞᱟᱹᱭ ᱮᱱᱟ", storyText = "ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱥᱩᱨ ᱨᱮ ᱫᱷᱩᱶᱟᱹ ᱞᱟᱹᱭ ᱠᱮᱫ-ᱟ। ᱟᱯᱟᱛ ᱟᱹᱱ ᱪᱟᱞᱟᱣ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱠᱟᱹᱢᱤ ᱵᱟᱹᱜᱤ ᱠᱟᱛᱮ ᱦᱩᱠᱩᱢ ᱞᱮᱠᱟᱛᱮ ᱚᱰᱚᱠᱚᱜ ᱥᱟᱯᱲᱟᱣ ᱮᱱᱟ।")
                        "ch07_s03" -> s.copy(title = "ᱨᱟᱡᱩ ᱵᱟᱪᱷᱟᱣ ᱦᱩᱭ ᱟᱭᱟ", storyText = "ᱨᱟᱡᱩ ᱧᱮᱞ ᱠᱮᱫ-ᱟ ᱟᱭᱟᱜ ᱡᱤᱱᱤᱥ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱨᱮ ᱢᱮᱱᱟᱜ-ᱟ ᱟᱨ ᱥᱩᱨ ᱨᱮᱱ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱟᱸᱴᱚᱜ ᱠᱟᱱᱟ।", decision = s.decision?.copy(question = "ᱨᱟᱡᱩ ᱪᱮᱫ ᱞᱟᱦᱟᱨᱮ ᱫᱚᱦᱚ ᱞᱟᱹᱠᱛᱤ?", options = listOf("ᱟᱭᱟᱜ ᱡᱤᱱᱤᱥ ᱡᱟᱨᱣᱟ", "ᱥᱮᱸᱜᱮᱞ ᱧᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱛᱤᱸᱜᱩ", "ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱪᱟᱞᱟᱣ", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱛᱮ ᱨᱩᱣᱟᱹᱲ"), feedback = listOf("ᱡᱤᱱᱤᱥ ᱫᱚ ᱟᱯᱟᱛ ᱚᱰᱚᱠᱚᱜ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱠᱷᱚᱱ ᱞᱟᱦᱟᱨᱮ ᱵᱟᱝ ᱫᱚᱦᱚ ᱞᱟᱹᱠᱛᱤ।", "ᱥᱮᱸᱜᱮᱞ ᱧᱮᱞ ᱫᱚ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱨᱮ ᱛᱟᱦᱮᱸᱱ ᱨᱮᱱᱟᱜ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱠᱟᱛᱷᱟ ᱵᱟᱝ ᱠᱟᱱᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱪᱟᱞᱟᱣ ᱞᱟᱹᱠᱛᱤ।", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱛᱮ ᱨᱩᱣᱟᱹᱲ ᱞᱮᱠᱷᱟᱱ ᱵᱚᱛᱚᱨ ᱵᱟ push-ᱟ।")))
                        "ch07_s04" -> s.copy(title = "ᱨᱟᱡᱩ ᱚᱰᱚᱠᱚᱜ-ᱟ", storyText = "ᱨᱟᱡᱩ ᱟᱯᱟᱛ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱵᱟᱝ ᱫᱟᱹᱲ ᱠᱮᱫ-ᱟ ᱥᱮ ᱵᱟᱝ ᱴᱷᱮᱞᱟ ᱠᱮᱫ-ᱟ। ᱩᱱᱤ ᱦᱩᱥᱤᱭᱟᱹᱨ ᱛᱟᱦᱮᱸ ᱠᱟᱛᱮ ᱦᱩᱠᱩᱢ ᱯᱟᱸᱡᱟ ᱠᱮᱫ-ᱟ।")
                        "ch07_s05" -> s.copy(title = "ᱩᱱᱤ ᱵᱚᱛᱚᱨ ᱵᱟᱝ ᱵᱮᱱᱟᱣ ᱠᱟᱛᱮ ᱜᱚᱲᱚ ᱮᱢᱟ", storyText = "ᱟᱸᱴᱚᱜ ᱠᱟᱱ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱩᱞᱤ ᱠᱮᱫ-ᱟ ᱚᱠᱟ ᱥᱮᱫ ᱪᱟᱞᱟᱜ-ᱟ। ᱨᱟᱡᱩ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱦᱚᱨ ᱥᱮᱫ ᱩᱫᱩᱜ ᱟᱫᱮᱭᱟ ᱢᱮᱱᱠᱷᱟᱱ ᱟᱯᱟᱛ ᱧᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱦᱚᱨ ᱵᱟᱝ ᱵᱟᱹᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch07_s06" -> s.copy(title = "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ", storyText = "ᱨᱟᱡᱩ ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ ᱥᱮᱴᱮᱨ ᱮᱱᱟ ᱟᱨ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱥᱟᱯᱲᱟᱣ ᱟᱨ ᱞᱮᱠᱷᱟ ᱡᱚᱠᱷᱮᱡ ᱚᱸᱰᱮ ᱛᱟᱦᱮᱸ ᱮᱱᱟ।")
                        "ch07_s07" -> s.copy(title = "ᱨᱟᱡᱩ ᱠᱟᱛᱷᱟ ᱞᱟᱹᱭᱟ", storyText = "ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱠᱩᱞᱤ ᱠᱮᱫ-ᱟ ᱨᱟᱡᱩ ᱪᱮᱫ ᱧᱮᱞ ᱟᱠᱟᱫ-ᱟᱭ। ᱨᱟᱡᱩ ᱥᱟᱹᱨᱤ ᱧᱮᱞ ᱟᱠᱟᱫ-ᱟᱭ ᱞᱟᱹᱭ ᱠᱮᱫ-ᱟ ᱟᱨ ᱵᱟᱝ ᱧᱮᱞ ᱟᱠᱟᱫ ᱦᱚᱲ ᱵᱟᱵᱚᱛ ᱛᱮ ᱵᱟᱝ ᱵᱷᱟᱵᱤ ᱠᱮᱫ-ᱟ।")
                        "ch07_s08" -> s.copy(title = "ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ", storyText = "ᱛᱤᱱᱟᱹᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱠᱩᱞᱤ ᱠᱮᱫ-ᱟ ᱨᱩᱣᱟᱹᱲ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ ᱠᱚ ᱥᱮ ᱵᱟᱝ। ᱨᱟᱡᱩ ᱫᱤᱥᱟᱹ ᱠᱮᱫ-ᱟ ᱡᱮ ᱵᱟᱝ ᱦᱩᱠᱩᱢ ᱛᱮ ᱟᱯᱟᱛ ᱴᱷᱟᱶ ᱵᱟᱝ ᱵᱚᱞᱚᱱ ᱞᱟᱹᱠᱛᱤ।")
                        "ch07_s09" -> s.copy(title = "ᱟᱯᱟᱛ ᱦᱟᱞᱚᱛ ᱥᱟᱯᱲᱟᱣ ᱮᱱᱟ", storyText = "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱦᱚᱲ ᱟᱯᱟᱛ ᱥᱟᱯᱲᱟᱣ ᱠᱟᱛᱮ, ᱦᱩᱠᱩᱢ ᱮᱢ ᱮᱱᱟ। ᱨᱟᱡᱩ ᱵᱚᱛᱚᱨ ᱪᱟᱵᱟ ᱮᱱᱟ ᱵᱟᱝ ᱢᱟᱱᱟᱣ ᱠᱟᱛᱮ ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ ᱠᱮᱫ-ᱟ।")
                        "ch07_s10" -> s.copy(title = "ᱨᱟᱡᱩᱣᱟᱜ ᱠᱟᱹᱢᱤ ᱪᱟᱵᱟᱜ-ᱟ", storyText = "ᱟᱯᱟᱛ ᱦᱟᱞᱚᱛ ᱥᱟ ordinary ᱠᱟᱹᱢᱤ ᱡᱚᱠᱷᱮᱡ ᱮᱦᱚᱵᱽ ᱞᱮᱱᱟ। ᱦᱚᱨ, ᱜᱷᱟᱱᱴᱤ, ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱟᱨ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱟᱹᱱ ᱨᱟᱡᱩ ᱯᱟᱸᱡᱟ ᱞᱟᱹᱜᱤᱫ ᱢᱤᱫ ᱠᱮᱴᱮᱡ ᱦᱚᱨ ᱮᱢ ᱟᱫᱮᱭᱟ।")
                        else -> s
                    }
                }
            )
            else -> ch
        }
    }
}

private fun getHindiModule1Assessment(): List<Module1AssessmentQuestion> {
    return module1Assessment.map { q ->
        when (q.id) {
            1 -> q.copy(
                question = "राजू को एक गर्म मशीन के पास तेल लगा कपड़ा मिलता है। उचित पहली प्रतिक्रिया क्या है?",
                options = listOf("इसे अनदेखा करें क्योंकि कोई लौ नहीं है", "असुरक्षित स्थिति की रिपोर्ट करें और साइट प्रक्रिया का पालन करें", "कपड़े को मशीन पर रख दें", "काम जारी रखें और इसके बारे में भूल जाएं"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            2 -> q.copy(
                question = "आग के जोखिम की स्थिति को जल्दी पहचानना क्यों महत्वपूर्ण है?",
                options = listOf("यह असुरक्षित स्थिति को किसी घटना में बदलने से पहले संबोधित करने की अनुमति दे सकता है", "यह मशीन को तेजी से चलाता है", "यह आपातकालीन प्रक्रियाओं की आवश्यकता को समाप्त करता है", "इसका मतलब है कि अलार्म अब आवश्यक नहीं हैं"),
                feedback = listOf("सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            3 -> q.copy(
                question = "राजू देखता है कि उपकरण से धुआं निकल रहा है। उसे क्या करना चाहिए?",
                options = listOf("जांच करने के लिए बहुत पास जाएं", "इसे अनदेखा करें", "अलार्म बजाएं/रिपोर्ट करें और प्रक्रिया के अनुसार खतरे के क्षेत्र से दूर रहें", "उपकरण को छुएं"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            4 -> q.copy(
                question = "असेंबली पॉइंट पर पहुंचने के बाद, राजू को करना चाहिए:",
                options = listOf("यह देखने के लिए तुरंत लौटें कि क्या हुआ", "निर्देशों का इंतजार करें", "अपने सामान के लिए वापस जाएं", "प्रभावित क्षेत्र में प्रवेश करें"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            5 -> q.copy(
                question = "आपातकालीन निकासी के दौरान, राजू को सामान्यतः किस मार्ग का पालन करना चाहिए?",
                options = listOf("सबसे छोटा मार्ग जो वह पा सकता है", "सबसे अधिक लोगों वाला मार्ग", "साइट प्रक्रिया के अनुसार निर्धारित आपातकालीन मार्ग", "उसके वर्कस्टेशन से दूर कोई भी मार्ग"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            6 -> q.copy(
                question = "आपात स्थिति के दौरान किसी कर्मचारी को प्रभावित क्षेत्र में वापस जाने से क्यों बचना चाहिए?",
                options = listOf("क्योंकि व्यक्तिगत सामान महत्वहीन हैं", "क्योंकि क्षेत्र में अभी भी खतरे हो सकते हैं", "क्योंकि पर्यवेक्षक नहीं चाहते कि कर्मचारी आएं", "क्योंकि निकासी केवल नए कर्मचारियों के लिए है"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            7 -> q.copy(
                question = "अग्निशामक (एक्सटिंग्विशर) का उपयोग करने से पहले, एक कर्मचारी को विचार करना चाहिए:",
                options = listOf("क्या एक्सटिंग्विशर आकर्षक दिखता है", "क्या कर्मचारी प्रशिक्षित/अधिकृत है और क्या स्थिति उपयुक्त है", "क्या लोग देख रहे हैं", "क्या आग दिलचस्प है"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            8 -> q.copy(
                question = "सही अग्निशामक (एक्सटिंग्विशर) क्यों महत्वपूर्ण है?",
                options = listOf("आग के विभिन्न प्रकारों के लिए उपयुक्त अग्निशमन विधियों की आवश्यकता होती है", "सभी एक्सटिंग्विशर बिल्कुल एक ही तरह से काम करते हैं", "सबसे बड़ा एक्सटिंग्विशर हमेशा सबसे सुरक्षित होता है", "एक्सटिंग्विशर केवल सजावट के लिए हैं"),
                feedback = listOf("सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            9 -> q.copy(
                question = "यदि राजू को ज्वलनशील वातावरण का संदेह है, तो उसे किससे बचना चाहिए?",
                options = listOf("आपातकालीन प्रक्रिया का पालन करना", "खतरे की रिपोर्ट करना", "इग्निशन स्रोत बनाना या अनधिकृत कार्रवाई करना जिससे इग्निशन स्रोत बन सकता है", "सुरक्षित स्थान पर जाना"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            10 -> q.copy(
                question = "दृश्यमान आग न होने पर भी किसी क्षेत्र को क्यों प्रतिबंधित किया जा सकता है?",
                options = listOf("क्योंकि लपटें दिखाई देने से पहले भी खतरनाक स्थितियां मौजूद हो सकती हैं", "क्योंकि कर्मचारियों को चलने की अनुमति नहीं है", "क्योंकि पर्यवेक्षक क्षेत्र को खाली चाहता है", "क्योंकि हर कार्यस्थल हमेशा बंद होना चाहिए"),
                feedback = listOf("सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            11 -> q.copy(
                question = "आपात स्थिति के दौरान, राजू एक शॉर्टकट देखता है जो निर्धारित आपातकालीन मार्ग नहीं है। उसे क्या करना चाहिए?",
                options = listOf("शॉर्टकट लें", "निर्धारित आपातकालीन मार्ग का पालन करें", "बाकी सभी के निर्णय लेने का इंतजार करें", "अपने वर्कस्टेशन पर लौटें"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            12 -> q.copy(
                question = "असेंबली पॉइंट पर, राजू को पता चलता है कि एक अन्य कर्मचारी लापता है। उसे क्या करना चाहिए?",
                options = listOf("खुद प्रभावित क्षेत्र में प्रवेश करें", "जानकारी को अनदेखा करें", "जिम्मेदार आपातकालीन कर्मियों को सूचित करें और निर्देशों का पालन करें", "कई अप्रशिक्षित कर्मचारियों को वापस भेजें"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            13 -> q.copy(
                question = "आग की आपात स्थिति के दौरान, राजू देखता है कि वह अपना सामान पीछे छोड़ आया है। उसे किसे प्राथमिकता देनी चाहिए?",
                options = listOf("सामान इकट्ठा करना", "सामान के लिए लौटना", "आपातकालीन प्रक्रिया का पालन करना और सुरक्षा की ओर बढ़ना", "आग नियंत्रित होने तक अंदर प्रतीक्षा करना"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            14 -> q.copy(
                question = "असेंबली पॉइंट पर पहुंचने के बाद, राजू को क्या करना चाहिए?",
                options = listOf("किसी को बताए बिना तुरंत चले जाएं", "अपने आप काम पर लौटें", "असेंबली पॉइंट पर रहें और निर्देशों का पालन करें", "जांच करने के लिए प्रभावित क्षेत्र में प्रवेश करें"),
                feedback = listOf("यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।", "सही। यह विकल्प स्थिति के लिए उचित सुरक्षा प्रतिक्रिया का पालन करता है।", "यह विकल्प उपयुक्त नहीं है क्योंकि यह स्थिति में वर्णित सुरक्षित प्रतिक्रिया का पालन नहीं करता है।")
            )
            else -> q
        }
    }
}

private fun getSantaliModule1Assessment(): List<Module1AssessmentQuestion> {
    return module1Assessment.map { q ->
        when (q.id) {
            1 -> q.copy(
                question = "ᱨᱟᱡᱩ ᱞᱚᱞᱚ ᱢᱮᱥᱤᱱ ᱡᱟᱯᱟᱜ ᱨᱮ ᱥᱩᱱᱩᱢᱟᱱ ᱞᱩᱜᱽᱲᱤ ᱧᱟᱢᱟ। ᱴᱷᱤᱠ ᱯᱟᱹᱦᱤᱞ ᱠᱟᱹᱢᱤ ᱪᱮᱫ ᱠᱟᱱᱟ?",
                options = listOf("ᱥᱮᱸᱜᱮᱞ ᱵᱟᱹᱱᱩᱜ ᱛᱮ ᱟᱞᱚᱢ ᱫᱷᱭᱟᱱᱟ", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱞᱟᱹᱭ ᱢᱮ ᱟᱨ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱞᱩᱜᱽᱲᱤ ᱢᱮᱥᱤᱱ ᱪᱮᱛᱟᱱ ᱨᱮ ᱫᱚᱦᱚᱭ ᱢᱮ", "ᱠᱟᱹᱢᱤ ᱞᱟᱦᱟᱭ ᱢᱮ ᱟᱨ ᱦᱤᱲᱤᱧ ᱢᱮ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ ᱪᱮᱫᱟᱜ ᱥᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱵᱟᱝ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ ᱪᱮᱫᱟᱜ ᱥᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱵᱟᱝ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ ᱪᱮᱫᱟᱜ ᱥᱮ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱵᱟᱝ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।")
            )
            2 -> q.copy(
                question = "ᱥᱮᱸᱜᱮᱞ ᱵᱚᱛᱚᱨ ᱞᱚᱜᱚᱱ ᱧᱮᱞ ᱪᱮᱫᱟᱜ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ?",
                options = listOf("ᱱᱚᱣᱟ ᱫᱚ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱦᱟᱞᱚᱛ ᱵᱟᱹᱲᱤᱡᱚᱜ ᱞᱟᱦᱟᱨᱮ ᱥᱟᱯᱲᱟᱣ ᱪᱷᱟᱹᱲ ᱮᱢᱟ", "ᱱᱚᱣᱟ ᱢᱮᱥᱤᱱ ᱞᱚᱜᱚᱱ ᱪᱟᱞᱟᱣᱟ", "ᱱᱚᱣᱟ ᱟᱯᱟᱛ ᱟᱹᱱ ᱨᱮᱱᱟᱜ ᱞᱟᱹᱠᱛᱤ ᱚᱪᱚᱜᱟ", "ᱱᱚᱣᱟ ᱨᱮᱱᱟᱜ ᱢᱮᱱᱮᱛ ᱜᱷᱟᱱᱴᱤ ᱵᱟᱝ ᱞᱟᱹᱠᱛᱤᱜ-ᱟ"),
                feedback = listOf("ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            3 -> q.copy(
                question = "ᱨᱟᱡᱩ ᱥᱟᱯᱟᱵ ᱠᱷᱚᱱ ᱫᱷᱩᱶᱟᱹ ᱚᱰᱚᱠᱚᱜ ᱧᱮᱞᱟ। ᱩᱱᱤ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱡᱟᱥ ᱞᱟᱹᱜᱤᱫ ᱥᱩᱨ ᱪᱟᱞᱟᱣ", "ᱵᱟᱹᱜᱤᱭᱟᱜ ᱢᱮ", "ᱜᱷᱟᱱᱴᱤ ᱵᱟᱡᱟᱣ/ᱞᱟᱹᱭ ᱢᱮ ᱟᱨ ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱥᱟᱹᱜᱤᱧ ᱛᱟᱦᱮᱸᱱ ᱢᱮ", "ᱥᱟᱯᱟᱵ ᱡᱚᱴᱮᱫ ᱢᱮ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            4 -> q.copy(
                question = "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱥᱮᱴᱮᱨ ᱠᱟᱛᱮ, ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ:",
                options = listOf("ᱪᱮᱫ ᱦᱩᱭ ᱮᱱᱟ ᱧᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱞᱚᱜᱚᱱ ᱨᱩᱣᱟᱹᱲ", "ᱦᱩᱠᱩᱢ ᱛᱟᱺᱜᱤ", "ᱟᱭᱟᱜ ᱡᱤᱱᱤᱥ ᱞᱟᱹᱜᱤᱫ ᱨᱩᱣᱟᱹᱲ", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱵᱚᱞᱚᱱ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            5 -> q.copy(
                question = "ᱟᱯᱟᱛ ᱚᱰᱚᱠᱚᱜ ᱡᱚᱠᱷᱮᱡ, ᱨᱟᱡᱩ ᱥᱟ ordinary ᱚᱠᱟ ᱦᱚᱨ ᱯᱟᱸᱡᱟ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱧᱟᱢᱚᱜ ᱠᱷᱟᱴᱚ ᱦᱚᱨ", "ᱡᱟᱹᱥᱛᱤ ᱦᱚᱲ ᱢᱮᱱᱟᱜ ᱠᱚ ᱦᱚᱨ", "ᱟᱹᱱ ᱞᱮᱠᱟᱛᱮ ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱠᱷᱚᱱ ᱥᱟᱹᱜᱤᱧ ᱚᱠᱟ ᱦᱚᱨ ᱦᱚᱸ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            6 -> q.copy(
                question = "ᱪᱮᱫᱟᱜ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱟᱯᱟᱛ ᱡᱚᱠᱷᱮᱡ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱨᱩᱣᱟᱹᱲᱚᱜ ᱠᱷᱚᱱ ᱥᱟᱦᱟ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱪᱮᱫᱟᱜ ᱥᱮ ᱟᱯᱱᱟᱨᱟᱜ ᱡᱤᱱᱤᱥ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱵᱟᱝ ᱠᱟᱱᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱴᱷᱟᱶ ᱨᱮ ᱱᱤᱛᱚᱜ ᱦᱚᱸ ᱵᱚᱛᱚᱨ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱪᱟᱞᱟᱣ ᱵᱟᱝ ᱥᱟᱱᱟᱭᱮᱫ ᱠᱚᱣᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱚᱰᱚᱠᱚᱜ ᱫᱚ ᱠᱷᱟᱹᱞᱤ ᱱᱟᱶᱟ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱞᱟᱹᱜᱤᱫ ᱠᱟᱱᱟ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            7 -> q.copy(
                question = "ᱥᱟᱯᱟᱵ ᱵᱮᱣᱦᱟᱨ ᱞᱟᱦᱟᱨᱮ, ᱠᱟᱹᱢᱤᱭᱟᱹ ᱪᱮᱫ ᱵᱷᱟᱵᱤ ᱞᱟᱹᱠᱛᱤ:",
                options = listOf("ᱥᱟᱯᱟᱵ ᱪᱚᱨᱚᱠ ᱧᱮᱞᱚᱜ ᱠᱟᱱᱟ ᱥᱮ ᱵᱟᱝ", "ᱠᱟᱹᱢᱤᱭᱟᱹ ᱪᱮᱫᱚᱜ/ᱦᱩᱠᱩᱢ ᱢᱮᱱᱟᱜ-ᱟ ᱥᱮ ᱵᱟᱝ ᱟᱨ ᱴᱷᱟᱶ ᱴᱷᱤᱠ ᱜᱮᱭᱟ ᱥᱮ ᱵᱟᱝ", "ᱦᱚᱲ ᱧᱮᱞᱮᱫ-ᱟ ᱠᱚ ᱥᱮ ᱵᱟᱝ", "ᱥᱮᱸᱜᱮᱞ ᱨᱟᱹᱥᱠᱟᱹ ᱜᱮᱭᱟ ᱥᱮ ᱵᱟᱝ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            8 -> q.copy(
                question = "ᱪᱮᱫᱟᱜ ᱴᱷᱤᱠ ᱥᱟᱯᱟᱵ ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱜᱮᱭᱟ?",
                options = listOf("ᱵᱷᱮᱜᱟᱨ ᱥᱮᱸᱜᱮᱞ ᱞᱟᱹᱜᱤᱫ ᱴᱷᱤᱠ ᱤᱬᱤᱡ ᱦᱚᱨ ᱞᱟᱹᱠᱛᱤ", "ᱡᱚᱛᱚ ᱥᱟᱯᱟᱵ ᱢᱤᱫ ᱜᱮ ᱠᱟᱹᱢᱤᱭᱟ", "ᱢᱟᱨᱟᱝ ᱥᱟᱯᱟᱵ ᱜᱮ ᱡᱚᱛᱚ ᱠᱷᱚᱱ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱜᱮᱭᱟ", "ᱥᱟᱯᱟᱵ ᱫᱚ ᱠᱷᱟᱹᱞᱤ ᱥᱟᱡᱟᱣ ᱞᱟᱹᱜᱤᱫ ᱠᱟᱱᱟ"),
                feedback = listOf("ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            9 -> q.copy(
                question = "ᱨᱟᱡᱩ ᱡᱩᱞᱩᱜ ᱦᱚᱭ ᱵᱷᱟᱵᱤ ᱨᱮ, ᱩᱱᱤ ᱪᱮᱫ ᱠᱷᱚᱱ ᱥᱟᱦᱟ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ", "ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱞᱟᱹᱭ", "ᱥᱮᱸᱜᱮᱞ ᱡᱩᱞᱩᱜ ᱠᱟᱹᱢᱤ ᱥᱮ ᱵᱟᱝ ᱦᱩᱠᱩᱢ ᱠᱟᱹᱢᱤ ᱠᱚᱨᱟᱣ", "ᱨᱩᱠᱷᱤᱭᱟᱹ ᱴᱷᱟᱶ ᱪᱟᱞᱟᱣ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            10 -> q.copy(
                question = "ᱪᱮᱫᱟᱜ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱨᱮᱦᱚᱸ ᱴᱷᱟᱶ ᱟᱴᱠᱟᱣ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ?",
                options = listOf("ᱪᱮᱫᱟᱜ ᱥᱮ ᱥᱮᱸᱜᱮᱞ ᱵᱟᱝ ᱧᱮᱞᱚᱜ ᱨᱮᱦᱚᱸ ᱵᱚᱛᱚᱨ ᱛᱟᱦᱮᱸ ᱫᱟᱲᱮᱭᱟᱜ-ᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱠᱚ ᱛᱟᱲᱟᱢ ᱵᱟᱝ ᱪᱷᱟᱹᱲ ᱟᱠᱟᱱᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱥᱩᱯᱚᱨᱵᱷᱟᱭᱤᱡᱚᱨ ᱴᱷᱟᱶ ᱠᱷᱟᱹᱞᱤ ᱥᱟᱱᱟᱭᱮᱫᱮᱭᱟ", "ᱪᱮᱫᱟᱜ ᱥᱮ ᱡᱚᱛᱚ ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱵᱚᱱᱫᱚ ᱞᱟᱹᱠᱛᱤ"),
                feedback = listOf("ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            11 -> q.copy(
                question = "ᱟᱯᱟᱛ ᱡᱚᱠᱷᱮᱡ, ᱨᱟᱡᱩ ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱧᱮᱞᱟ ᱡᱟᱦᱟᱸ ᱫᱚ ᱟᱯᱟᱛ ᱦᱚᱨ ᱵᱟᱝ ᱠᱟᱱᱟ। ᱩᱱᱤ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱠᱷᱟᱴᱚ ᱦᱚᱨ ᱤᱫᱤ ᱢᱮ", "ᱴᱷᱟᱹᱣᱠᱟᱹ ᱟᱠᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱨ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱮᱴᱟᱜ ᱦᱚᱲᱟᱜ ᱵᱟᱪᱷᱟᱣ ᱛᱟᱺᱜᱤ ᱢᱮ", "ᱠᱟᱹᱢᱤ ᱴᱷᱟᱶ ᱛᱮ ᱨᱩᱣᱟᱹᱲ ᱢᱮ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            12 -> q.copy(
                question = "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ, ᱨᱟᱡᱩ ᱵᱟᱰᱟᱭᱟ ᱡᱮ ᱢᱤᱫ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱵᱟᱹᱱᱩᱜ-ᱟ। ᱩᱱᱤ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱟᱯᱱᱟᱨ ᱛᱮ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱵᱚᱞᱚᱱ", "ᱠᱟᱛᱷᱟ ᱵᱟᱹᱜᱤᱭᱟᱜ ᱢᱮ", "ᱞᱟᱹᱠᱛᱤᱭᱟᱱ ᱟᱯᱟᱛ ᱦᱚᱲ ᱞᱟᱹᱭ ᱟᱠᱚᱣᱟ ᱟᱨ ᱦᱩᱠᱩᱢ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱵᱟᱝ ᱪᱮᱫᱚᱜ ᱟᱠᱟᱱ ᱠᱟᱹᱢᱤᱭᱟᱹ ᱨᱩᱣᱟᱹᱲ ᱠᱩᱞ ᱠᱚᱣᱟ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            13 -> q.copy(
                question = "ᱥᱮᱸᱜᱮᱞ ᱟᱯᱟᱛ ᱡᱚᱠᱷᱮᱡ, ᱨᱟᱡᱩ ᱧᱮᱞᱟ ᱡᱮ ᱟᱭᱟᱜ ᱡᱤᱱᱤᱥ ᱵᱟᱹᱜᱤ ᱟᱠᱟᱱᱟ। ᱩᱱᱤ ᱪᱮᱫ ᱞᱟᱦᱟᱨᱮ ᱫᱚᱦᱚ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱡᱤᱱᱤᱥ ᱡᱟᱨᱣᱟ", "ᱡᱤᱱᱤᱥ ᱞᱟᱹᱜᱤᱫ ᱨᱩᱣᱟᱹᱲ", "ᱟᱯᱟᱛ ᱟᱹᱱ ᱯᱟᱸᱡᱟ ᱟᱨ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱪᱟᱞᱟᱣ", "ᱥᱮᱸᱜᱮᱞ ᱤᱬᱤᱡᱚᱜ ᱫᱷᱟᱹᱵᱤᱡ ᱵᱷᱤᱛᱨᱤ ᱨᱮ ᱛᱟᱺᱜᱤ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            14 -> q.copy(
                question = "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱥᱮᱴᱮᱨ ᱠᱟᱛᱮ, ᱨᱟᱡᱩ ᱪᱮᱫ ᱠᱚᱨᱟᱣ ᱞᱟᱹᱠᱛᱤ?",
                options = listOf("ᱚᱠᱚᱭ ᱦᱚᱸ ᱵᱟᱝ ᱞᱟᱹᱭ ᱠᱟᱛᱮ ᱞᱚᱜᱚᱱ ᱪᱟᱞᱟᱣ", "ᱟᱯᱱᱟᱨ ᱛᱮ ᱠᱟᱹᱢᱤ ᱛᱮ ᱨᱩᱣᱟᱹᱲ", "ᱡᱟᱨᱣᱟ ᱴᱷᱟᱶ ᱨᱮ ᱛᱟᱦᱮᱸᱱ ᱢᱮ ᱟᱨ ᱦᱩᱠᱩᱢ ᱯᱟᱸᱡᱟᱭ ᱢᱮ", "ᱡᱟᱥ ᱞᱟᱹᱜᱤᱫ ᱵᱚᱛᱚᱨᱟᱱ ᱴᱷᱟᱶ ᱵᱚᱞᱚᱱ"),
                feedback = listOf("ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।", "ᱴᱷᱤᱠ ᱜᱮᱭᱟ। ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱴᱷᱤᱠ ᱨᱩᱠᱷᱤᱭᱟᱹ ᱟᱹᱱ ᱯᱟᱸᱡᱟᱭᱮᱫ-ᱟ।", "ᱱᱚᱣᱟ ᱵᱟᱪᱷᱟᱣ ᱫᱚ ᱵᱟᱝ ᱴᱷᱤᱠ ᱜᱮᱭᱟ।")
            )
            else -> q
        }
    }
}

