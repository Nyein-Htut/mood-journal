package com.example.moodjournal.util

/**
 * A lightweight, on-device pre-check for language suggesting acute distress.
 * This runs BEFORE any network call so the app can react instantly and
 * doesn't depend on the LLM alone. It intentionally errs on the side of
 * over-flagging - a false positive just shows a supportive message,
 * which is a low-cost outcome compared to a missed flag.
 *
 * This is a supplement to, not a replacement for, professional judgment.
 * It should never be the only safety mechanism in a real product.
 */
object CrisisSupport {

    private val concerningPhrases = listOf(
        "kill myself", "want to die", "end my life", "suicide",
        "hurting myself", "self harm", "self-harm", "no reason to live",
        "better off without me", "can't go on"
    )

    fun containsConcerningLanguage(text: String): Boolean {
        val lower = text.lowercase()
        return concerningPhrases.any { lower.contains(it) }
    }

    const val SUPPORT_MESSAGE =
        "It sounds like things feel really heavy right now. You don't have to " +
        "go through this alone - please consider reaching out to a mental health " +
        "professional or a crisis line in your country. If you're in the US, you can " +
        "call or text 988 (Suicide & Crisis Lifeline). Outside the US, " +
        "findahelpline.com can point you to a local service. If you're in immediate " +
        "danger, please contact your local emergency number."
}
