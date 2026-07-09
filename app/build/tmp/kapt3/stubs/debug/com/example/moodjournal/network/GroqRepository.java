package com.example.moodjournal.network;

/**
 * Talks to Groq's OpenAI-compatible chat completions endpoint.
 * https://console.groq.com/docs/api-reference
 *
 * NOTE: Groq periodically retires/renames models. If GROQ_MODEL below
 * ever returns a "model_decommissioned" error, check console.groq.com
 * for the current recommended model name and swap it in.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u000fJ*\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0014J*\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/example/moodjournal/network/GroqRepository;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "jsonMediaType", "Lokhttp3/MediaType;", "analyzeEntry", "Lcom/example/moodjournal/network/GroqResult;", "Lcom/example/moodjournal/network/EntryAnalysis;", "text", "", "apiKey", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDailyAffirmation", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTrendInsight", "entries", "", "Lcom/example/moodjournal/data/JournalEntry;", "(Ljava/util/List;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWeeklyVibe", "Companion", "app_debug"})
public final class GroqRepository {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.MediaType jsonMediaType = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ENDPOINT = "https://api.groq.com/openai/v1/chat/completions";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GROQ_MODEL = "llama-3.3-70b-versatile";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.moodjournal.network.GroqRepository.Companion Companion = null;
    
    public GroqRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object analyzeEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.moodjournal.network.GroqResult<com.example.moodjournal.network.EntryAnalysis>> $completion) {
        return null;
    }
    
    /**
     * Produces short, supportive, bulleted insights based on recent trends.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTrendInsight(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries, @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.moodjournal.network.GroqResult<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWeeklyVibe(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries, @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.moodjournal.network.GroqResult<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDailyAffirmation(@org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.moodjournal.network.GroqResult<java.lang.String>> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/moodjournal/network/GroqRepository$Companion;", "", "()V", "ENDPOINT", "", "GROQ_MODEL", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}