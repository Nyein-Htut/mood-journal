package com.example.moodjournal.util;

/**
 * Stores the user's Groq API key on-device using AndroidX Security's
 * EncryptedSharedPreferences. The key never leaves the device except
 * as part of the user's own direct HTTPS calls to Groq.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R(\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R$\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001a"}, d2 = {"Lcom/example/moodjournal/util/PreferencesManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "groqApiKey", "getGroqApiKey", "()Ljava/lang/String;", "setGroqApiKey", "(Ljava/lang/String;)V", "", "isDarkMode", "()Z", "setDarkMode", "(Z)V", "isGuestMode", "setGuestMode", "prefs", "Landroid/content/SharedPreferences;", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", "Companion", "app_debug"})
public final class PreferencesManager {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy prefs$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_GROQ_API = "groq_api_key";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DARK_MODE = "is_dark_mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_GUEST_MODE = "is_guest_mode";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.moodjournal.util.PreferencesManager.Companion Companion = null;
    
    public PreferencesManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.content.SharedPreferences getPrefs() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGroqApiKey() {
        return null;
    }
    
    public final void setGroqApiKey(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final boolean isDarkMode() {
        return false;
    }
    
    public final void setDarkMode(boolean value) {
    }
    
    public final boolean isGuestMode() {
        return false;
    }
    
    public final void setGuestMode(boolean value) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/moodjournal/util/PreferencesManager$Companion;", "", "()V", "KEY_DARK_MODE", "", "KEY_GROQ_API", "KEY_GUEST_MODE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}