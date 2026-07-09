# Mood Journal

A journaling app that analyzes the tone of each entry using Groq's LLM API,
tracks mood trends over time, and offers gentle, non-clinical suggestions.
Built with Kotlin + Jetpack Compose. No Google Play Services dependency,
so it runs on Huawei devices (AppGallery or sideload) exactly like any
other Android phone.

## What's inside

- **Journal**: write an entry, it's saved locally (Room database) instantly.
- **Analysis**: each entry is sent to Groq's chat completions API, which
  returns a mood score, primary emotion, themes, and a short supportive
  reflection - all as structured JSON.
- **Trends**: a 30-day mood chart (custom-drawn, no external chart library),
  recurring theme frequency, and an on-demand AI-generated "gentle insight"
  paragraph summarizing recent patterns.
- **Safety net**: entries are scanned on-device for language suggesting
  acute distress *before* any network call, and the model itself is
  instructed to flag concerning content. Either trigger shows a supportive
  message with crisis-line info - this is a basic safeguard, not a
  substitute for a real clinical safety review if you ever ship this
  publicly.
- **Privacy**: your Groq API key is stored using `EncryptedSharedPreferences`
  and excluded from Android backups. Journal text is stored only in the
  local Room database and is sent to Groq only at the moment you save an
  entry (for analysis) or request a trend insight.

## Setup

1. Open the `MoodJournal/` folder in Android Studio (Koala or newer).
   Android Studio will offer to generate the Gradle wrapper JAR/scripts on
   first sync - accept that, since only `gradle-wrapper.properties` is
   included here (the binary wrapper jar isn't included in this download).
2. Let Gradle sync (needs internet access to Google's and Maven Central's
   repositories).
3. Get a free API key at [console.groq.com](https://console.groq.com).
4. Run the app on an emulator or device, then open **Settings** inside the
   app and paste in your Groq API key. That's it - no `.env` files or
   hardcoded secrets needed.

## Notes on the Groq model

The repository (`network/GroqRepository.kt`) currently targets
`llama-3.3-70b-versatile`. Groq periodically retires or renames models -
if you get a `model_decommissioned` error, check
[console.groq.com/docs/models](https://console.groq.com/docs/models) for
the current recommended model and update the `GROQ_MODEL` constant.

## Huawei / no-GMS compatibility

This app doesn't use Firebase, Google Maps, Google Sign-In, or Play
Billing - nothing here depends on Google Play Services. It will install
and run identically on Huawei devices (with or without HMS) and any other
Android device running API 26+. If you later add push notifications, maps,
or in-app purchases, those specific features (not the whole app) would
need Huawei-specific equivalents (HMS Push Kit, Huawei Map Kit, Huawei IAP)
alongside the Google versions.

## Where to go next

- Swap the on-device keyword check in `util/CrisisSupport.kt` for something
  more robust before shipping publicly.
- Add entry editing, search, and export.
- Consider routing the Groq call through your own backend instead of
  calling it directly from the app, so the API key never lives on-device
  at all (better for a real production app; the encrypted-local-key
  approach here is intentionally simple for a learning project).
