package com.example.moodjournal.util;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/example/moodjournal/util/CrisisSupport;", "", "()V", "SUPPORT_MESSAGE", "", "concerningPhrases", "", "containsConcerningLanguage", "", "text", "app_debug"})
public final class CrisisSupport {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> concerningPhrases = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SUPPORT_MESSAGE = "It sounds like things feel really heavy right now. You don\'t have to go through this alone - please consider reaching out to a mental health professional or a crisis line in your country. If you\'re in the US, you can call or text 988 (Suicide & Crisis Lifeline). Outside the US, findahelpline.com can point you to a local service. If you\'re in immediate danger, please contact your local emergency number.";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.moodjournal.util.CrisisSupport INSTANCE = null;
    
    private CrisisSupport() {
        super();
    }
    
    public final boolean containsConcerningLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return false;
    }
}