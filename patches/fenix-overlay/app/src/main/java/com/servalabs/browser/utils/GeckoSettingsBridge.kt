package com.servalabs.browser.utils

import android.content.Context
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import mozilla.components.concept.engine.preferences.Branch
import mozilla.components.concept.engine.preferences.BrowserPreference
import mozilla.components.ExperimentalAndroidComponentsApi
import mozilla.components.concept.engine.Engine
import com.servalabs.browser.utils.GeckoSettingsDictionary
import com.servalabs.browser.utils.ServaPreferences
import org.mozilla.fenix.ext.settings

// Helpers for managing Gecko preferences from Fenix
/// (Thus, the name is Gecko Settings "Bridge" - this is meant to serve as a bridge between Fenix settings
/// and Gecko prefs)
/// For reference, I took some inspiration from AutoConfig functions:
// https://support.mozilla.org/kb/customizing-firefox-using-autoconfig#w_functions-of-autoconfig

object GeckoSettingsBridge {

    fun setServaPrefs(context: Context, engine: Engine) {

        setWebGLDisabled(context, engine)
        setAccessibilityEnabled(context, engine)
        setJavaScriptEnabled(context, engine)
        setFPPOverridesServaWebGLEnabled(context, engine)
        setAlwaysUsePrivateBrowsing(context, engine)
        setCacheEnabled(context, engine)
        setFPPOverridesServaEnabled(context, engine)
        setFPPOverridesMozillaEnabled(context, engine)
        setFPPOverridesServaTimezoneEnabled(context, engine)
        setSpoofEnglishEnabled(context, engine)
        setSpoofTimezoneEnabled(context, engine)
        setXPInstallEnabled(context, engine)
        setJITEnabled(context, engine)
        setJITTrustedPrincipalsEnabled(context, engine)
        setPrintEnabled(context, engine)
        setSafeBrowsingEnabled(context, engine)
        setSVGEnabled(context, engine)
        setWASMEnabled(context, engine)
        setWebRTCEnabled(context, engine)
        setTranslationsEnabled(context, engine)
        setIPv6Enabled(context, engine)
        setPDFjsDisabled(context, engine)
        setAutoplayBlockingPolicy(context, engine)
        setPreferredWebsiteAppearance(context, engine)
        setRefererXOriginPolicy(context, engine)
        setAddressAutofillEnabled(context, engine)
        setCardAutofillEnabled(context, engine)
        setPasswordManagerEnabled(context, engine)
        setServaOnboardingCompleted(context, engine)

        // We don't support EME, but, if a user enables it from the about:config,
        // we need to expose the permision UI for it.
        // If we don't, and a user enables it, Gecko will just allow every site unconditionally to use EME...
        val emeGeckoPref = GeckoSettingsDictionary.emeEnabled
        @OptIn(ExperimentalAndroidComponentsApi::class)
        engine.getBrowserPref(
            emeGeckoPref,
            onSuccess = { emePref ->
                ServaPreferences.setEMEEnabled(context, emePref.value as Boolean)
            },
            onError = { throwable ->
                ServaPreferences.setEMEEnabled(context, false)
            }
        )
    }

    fun setServaOnboardingCompleted(context: Context, engine: Engine) {
        val ironFoxOnboardingCompleted = ServaPreferences.isServaOnboardingCompleted(context)
        val ironFoxOnboardingCompletedGeckoPref = GeckoSettingsDictionary.onboardingCompleted
        setUserPref(engine, ironFoxOnboardingCompletedGeckoPref, ironFoxOnboardingCompleted)
        setDefaultPref(engine, ironFoxOnboardingCompletedGeckoPref, ironFoxOnboardingCompleted)
    }

    fun setWebGLDisabled(context: Context, engine: Engine) {
        val webglDisabled = ServaPreferences.isWebGLDisabled(context)
        val webglGeckoPref = GeckoSettingsDictionary.webglDisabled
        setUserPref(engine, webglGeckoPref, webglDisabled)
        setDefaultPref(engine, webglGeckoPref, webglDisabled)
    }

    fun setAccessibilityEnabled(context: Context, engine: Engine) {
        val accessibilityEnabled = ServaPreferences.isAccessibilityEnabled(context)
        val accessibilityGeckoPref = GeckoSettingsDictionary.accessibilityDisabled
        if (accessibilityEnabled) {
            setUserPref(engine, accessibilityGeckoPref, 0)
            setDefaultPref(engine, accessibilityGeckoPref, 0)
        } else {
            setUserPref(engine, accessibilityGeckoPref, 1)
            setDefaultPref(engine, accessibilityGeckoPref, 1)
        }
    }

    fun setJavaScriptEnabled(context: Context, engine: Engine) {
        val javascriptEnabled = ServaPreferences.isJavaScriptEnabled(context)
        val javascriptGeckoPref = GeckoSettingsDictionary.javascriptEnabled
        setUserPref(engine, javascriptGeckoPref, javascriptEnabled)
        setDefaultPref(engine, javascriptGeckoPref, javascriptEnabled)
    }

    fun setFPPOverridesServaWebGLEnabled(context: Context, engine: Engine) {
        val fppOverridesServaWebGLEnabled = ServaPreferences.isFPPOverridesServaWebGLEnabled(context)
        val fppOverridesServaWebGLGeckoPref = GeckoSettingsDictionary.fppOverridesIFWebGL
        setUserPref(engine, fppOverridesServaWebGLGeckoPref, fppOverridesServaWebGLEnabled)
        setDefaultPref(engine, fppOverridesServaWebGLGeckoPref, fppOverridesServaWebGLEnabled)
    }

    fun setAlwaysUsePrivateBrowsing(context: Context, engine: Engine) {
        val alwaysUsePrivateBrowsing = ServaPreferences.isAlwaysUsePrivateBrowsing(context)
        val alwaysUsePrivateBrowsingGeckoPref = GeckoSettingsDictionary.alwaysUsePrivateBrowsing
        setUserPref(engine, alwaysUsePrivateBrowsingGeckoPref, alwaysUsePrivateBrowsing)
        setDefaultPref(engine, alwaysUsePrivateBrowsingGeckoPref, alwaysUsePrivateBrowsing)
    }

    fun setCacheEnabled(context: Context, engine: Engine) {
        val cacheEnabled = ServaPreferences.isCacheEnabled(context)
        val cacheEnabledGeckoPref = GeckoSettingsDictionary.cacheEnabled
        setUserPref(engine, cacheEnabledGeckoPref, cacheEnabled)
        setDefaultPref(engine, cacheEnabledGeckoPref, cacheEnabled)
    }

    fun setFPPOverridesServaEnabled(context: Context, engine: Engine) {
        val fppOverridesServaEnabled = ServaPreferences.isFPPOverridesServaEnabled(context)
        val fppOverridesServaGeckoPref = GeckoSettingsDictionary.fppOverridesIFUnbreak
        setUserPref(engine, fppOverridesServaGeckoPref, fppOverridesServaEnabled)
        setDefaultPref(engine, fppOverridesServaGeckoPref, fppOverridesServaEnabled)
    }

    fun setFPPOverridesMozillaEnabled(context: Context, engine: Engine) {
        val fppOverridesMozillaEnabled = ServaPreferences.isFPPOverridesMozillaEnabled(context)
        val fppOverridesMozillaGeckoPref = GeckoSettingsDictionary.fppOverridesMozUnbreak
        setUserPref(engine, fppOverridesMozillaGeckoPref, fppOverridesMozillaEnabled)
        setDefaultPref(engine, fppOverridesMozillaGeckoPref, fppOverridesMozillaEnabled)
    }

    fun setFPPOverridesServaTimezoneEnabled(context: Context, engine: Engine) {
        val fppOverridesServaTimezoneEnabled = ServaPreferences.isFPPOverridesServaTimezoneEnabled(context)
        val fppOverridesServaTimezoneGeckoPref = GeckoSettingsDictionary.fppOverridesIFTimezone
        setUserPref(engine, fppOverridesServaTimezoneGeckoPref, fppOverridesServaTimezoneEnabled)
        setDefaultPref(engine, fppOverridesServaTimezoneGeckoPref, fppOverridesServaTimezoneEnabled)
    }

    fun setSpoofEnglishEnabled(context: Context, engine: Engine) {
        val spoofEnglish = ServaPreferences.isSpoofEnglishEnabled(context)
        val spoofEnglishGeckoPref = GeckoSettingsDictionary.spoofEnglish
        if (spoofEnglish) {
            setUserPref(engine, spoofEnglishGeckoPref, 2)
            setDefaultPref(engine, spoofEnglishGeckoPref, 2)
        } else {
            setUserPref(engine, spoofEnglishGeckoPref, 1)
            setDefaultPref(engine, spoofEnglishGeckoPref, 1)
        }
    }

    fun setSpoofTimezoneEnabled(context: Context, engine: Engine) {
        val spoofTimezone = ServaPreferences.isSpoofTimezoneEnabled(context)
        val spoofTimezoneGeckoPref = GeckoSettingsDictionary.spoofTimezone
        setUserPref(engine, spoofTimezoneGeckoPref, spoofTimezone)
        setDefaultPref(engine, spoofTimezoneGeckoPref, spoofTimezone)
    }

    fun setXPInstallEnabled(context: Context, engine: Engine) {
        val xpinstallEnabled = ServaPreferences.isXPInstallEnabled(context)
        val xpinstallGeckoPref = GeckoSettingsDictionary.xpinstallEnabled
        setUserPref(engine, xpinstallGeckoPref, xpinstallEnabled)
        setDefaultPref(engine, xpinstallGeckoPref, xpinstallEnabled)
    }

    fun setJITEnabled(context: Context, engine: Engine) {
        val javascriptJitEnabled = ServaPreferences.isJITEnabled(context)
        val jitBaselineGeckoPref = GeckoSettingsDictionary.jitBaseline
        val jitIonGeckoPref = GeckoSettingsDictionary.jitIon
        val jitHintsGeckoPref = GeckoSettingsDictionary.jitHints
        val jitNativeRegexpGeckoPref = GeckoSettingsDictionary.jitNativeRegexp
        val jitWasmIonGeckoPref = GeckoSettingsDictionary.jitWasmIon
        setUserPref(engine, jitBaselineGeckoPref, javascriptJitEnabled)
        setDefaultPref(engine, jitBaselineGeckoPref, javascriptJitEnabled)
        setUserPref(engine, jitIonGeckoPref, javascriptJitEnabled)
        setDefaultPref(engine, jitIonGeckoPref, javascriptJitEnabled)
        setUserPref(engine, jitHintsGeckoPref, javascriptJitEnabled)
        setDefaultPref(engine, jitHintsGeckoPref, javascriptJitEnabled)
        setUserPref(engine, jitNativeRegexpGeckoPref, javascriptJitEnabled)
        setDefaultPref(engine, jitNativeRegexpGeckoPref, javascriptJitEnabled)
        setUserPref(engine, jitWasmIonGeckoPref, javascriptJitEnabled)
        setDefaultPref(engine, jitWasmIonGeckoPref, javascriptJitEnabled)
    }

    fun setJITTrustedPrincipalsEnabled(context: Context, engine: Engine) {
        val javascriptJitTrustedPrincipalsEnabled = ServaPreferences.isJITTrustedPrincipalsEnabled(context)
        val jitTrustedPrincipalsGeckoPref = GeckoSettingsDictionary.jitTrustedPrincipals
        setUserPref(engine, jitTrustedPrincipalsGeckoPref, javascriptJitTrustedPrincipalsEnabled)
        setDefaultPref(engine, jitTrustedPrincipalsGeckoPref, javascriptJitTrustedPrincipalsEnabled)
    }

    fun setPrintEnabled(context: Context, engine: Engine) {
        val printEnabled = ServaPreferences.isPrintEnabled(context)
        val printGeckoPref = GeckoSettingsDictionary.printEnabled
        setUserPref(engine, printGeckoPref, printEnabled)
        setDefaultPref(engine, printGeckoPref, printEnabled)
    }

    fun setSafeBrowsingEnabled(context: Context, engine: Engine) {
        val safeBrowsingEnabled = ServaPreferences.isSafeBrowsingEnabled(context)
        val safeBrowsingMalwareGeckoPref = GeckoSettingsDictionary.sbMalware
        val safeBrowsingPhishingGeckoPref = GeckoSettingsDictionary.sbPhishing
        setUserPref(engine, safeBrowsingMalwareGeckoPref, safeBrowsingEnabled)
        setDefaultPref(engine, safeBrowsingMalwareGeckoPref, safeBrowsingEnabled)
        setUserPref(engine, safeBrowsingPhishingGeckoPref, safeBrowsingEnabled)
        setDefaultPref(engine, safeBrowsingPhishingGeckoPref, safeBrowsingEnabled)
    }

    fun setSVGEnabled(context: Context, engine: Engine) {
        val svgEnabled = ServaPreferences.isSVGEnabled(context)
        val svgGeckoPref = GeckoSettingsDictionary.svgDisabled
        setUserPref(engine, svgGeckoPref, !svgEnabled)
        setDefaultPref(engine, svgGeckoPref, !svgEnabled)
    }

    fun setWASMEnabled(context: Context, engine: Engine) {
        val wasmEnabled = ServaPreferences.isWASMEnabled(context)
        val wasmGeckoPref = GeckoSettingsDictionary.wasm
        setUserPref(engine, wasmGeckoPref, wasmEnabled)
        setDefaultPref(engine, wasmGeckoPref, wasmEnabled)
    }

    fun setWebRTCEnabled(context: Context, engine: Engine) {
        val webrtcEnabled = ServaPreferences.isWebRTCEnabled(context)
        val webrtcGeckoPref = GeckoSettingsDictionary.webrtcEnabled
        setUserPref(engine, webrtcGeckoPref, webrtcEnabled)
        setDefaultPref(engine, webrtcGeckoPref, webrtcEnabled)
    }

    fun setTranslationsEnabled(context: Context, engine: Engine) {
        val translationsEnabled = ServaPreferences.isTranslationsEnabled(context)
        val translationsAiControlGeckoPref = GeckoSettingsDictionary.translationsAiControl
        val translationsEnabledGeckoPref = GeckoSettingsDictionary.translationsEnabled
        val translationsSupportedGeckoPref = GeckoSettingsDictionary.translationsUnsupported
        setUserPref(engine, translationsEnabledGeckoPref, translationsEnabled)
        setDefaultPref(engine, translationsEnabledGeckoPref, translationsEnabled)
        setUserPref(engine, translationsSupportedGeckoPref, !translationsEnabled)
        setDefaultPref(engine, translationsSupportedGeckoPref, !translationsEnabled)

        if (translationsEnabled) {
            setUserPref(engine, translationsAiControlGeckoPref, "enabled")
            setDefaultPref(engine, translationsAiControlGeckoPref, "enabled")
        } else {
            setUserPref(engine, translationsAiControlGeckoPref, "blocked")
            setDefaultPref(engine, translationsAiControlGeckoPref, "blocked")
        }
    }

    fun setIPv6Enabled(context: Context, engine: Engine) {
        val ipv6Enabled = ServaPreferences.isIPv6Enabled(context)
        val ipv6GeckoPref = GeckoSettingsDictionary.ipv6Disabled
        setUserPref(engine, ipv6GeckoPref, !ipv6Enabled)
        setDefaultPref(engine, ipv6GeckoPref, !ipv6Enabled)
    }

    fun setPDFjsDisabled(context: Context, engine: Engine) {
        val pdfjsDisabled = ServaPreferences.isPDFjsDisabled(context)
        val pdfjsGeckoPref = GeckoSettingsDictionary.pdfjsDisabled
        setUserPref(engine, pdfjsGeckoPref, pdfjsDisabled)
        setDefaultPref(engine, pdfjsGeckoPref, pdfjsDisabled)
    }

    fun setAutoplayBlockingPolicy(context: Context, engine: Engine) {
        val autoplayBlockingClickToPlay = ServaPreferences.isAutoplayBlockingClickToPlay(context)
        val autoplayBlockingSticky = ServaPreferences.isAutoplayBlockingSticky(context)
        val autoplayBlockingPolicyGeckoPref = GeckoSettingsDictionary.autoplayPolicy

        if (autoplayBlockingClickToPlay) {
            setUserPref(engine, autoplayBlockingPolicyGeckoPref, 2)
            setDefaultPref(engine, autoplayBlockingPolicyGeckoPref, 2)
        } else if (autoplayBlockingSticky) {
            setUserPref(engine, autoplayBlockingPolicyGeckoPref, 0)
            setDefaultPref(engine, autoplayBlockingPolicyGeckoPref, 0)
        } else {
            setUserPref(engine, autoplayBlockingPolicyGeckoPref, 1)
            setDefaultPref(engine, autoplayBlockingPolicyGeckoPref, 1)
        }
    }

    fun setPreferredWebsiteAppearance(context: Context, engine: Engine) {
        val prefersBrowserColorScheme = ServaPreferences.isPrefersBrowserColorScheme(context)
        val prefersDarkColorScheme = ServaPreferences.isPrefersDarkColorScheme(context)
        val preferredWebsiteAppearanceGeckoPref = GeckoSettingsDictionary.websiteAppearance

        if (prefersBrowserColorScheme) {
            setUserPref(engine, preferredWebsiteAppearanceGeckoPref, 2)
            setDefaultPref(engine, preferredWebsiteAppearanceGeckoPref, 2)
        } else if (prefersDarkColorScheme) {
            setUserPref(engine, preferredWebsiteAppearanceGeckoPref, 0)
            setDefaultPref(engine, preferredWebsiteAppearanceGeckoPref, 0)
        } else {
            setUserPref(engine, preferredWebsiteAppearanceGeckoPref, 1)
            setDefaultPref(engine, preferredWebsiteAppearanceGeckoPref, 1)
        }
    }

    fun setRefererXOriginPolicy(context: Context, engine: Engine) {
        val refererXOriginBaseDomainsMatch = ServaPreferences.isRefererXOriginBaseDomainsMatch(context)
        val refererXOriginHostsMatch = ServaPreferences.isRefererXOriginHostsMatch(context)
        val refererXOriginPolicyGeckoPref = GeckoSettingsDictionary.refererXOriginPolicy

        if (refererXOriginHostsMatch) {
            setUserPref(engine, refererXOriginPolicyGeckoPref, 2)
            setDefaultPref(engine, refererXOriginPolicyGeckoPref, 2)
        } else if (refererXOriginBaseDomainsMatch) {
            setUserPref(engine, refererXOriginPolicyGeckoPref, 1)
            setDefaultPref(engine, refererXOriginPolicyGeckoPref, 1)
        } else {
            setUserPref(engine, refererXOriginPolicyGeckoPref, 0)
            setDefaultPref(engine, refererXOriginPolicyGeckoPref, 0)
        }
    }

    fun setAddressAutofillEnabled(context: Context, engine: Engine) {
        val addressAutofillEnabled = context.settings().shouldAutofillAddressDetails
        val addressAutofillGeckoPref = GeckoSettingsDictionary.addressAutofill
        setUserPref(engine, addressAutofillGeckoPref, addressAutofillEnabled)
        setDefaultPref(engine, addressAutofillGeckoPref, addressAutofillEnabled)
    }

    fun setCardAutofillEnabled(context: Context, engine: Engine) {
        val cardAutofillEnabled = context.settings().shouldAutofillCreditCardDetails
        val cardAutofillGeckoPref = GeckoSettingsDictionary.cardAutofill
        setUserPref(engine, cardAutofillGeckoPref, cardAutofillEnabled)
        setDefaultPref(engine, cardAutofillGeckoPref, cardAutofillEnabled)
    }

    fun setPasswordManagerEnabled(context: Context, engine: Engine) {
        val passwordManagerEnabled = context.settings().shouldPromptToSaveLogins
        val passwordManagerGeckoPref = GeckoSettingsDictionary.passwordManager
        setUserPref(engine, passwordManagerGeckoPref, passwordManagerEnabled)
        setDefaultPref(engine, passwordManagerGeckoPref, passwordManagerEnabled)
    }
}

/**
 * Set a Gecko preference (geckoPreference) to a desired value (geckoPreferenceValue)
 *
 * @param engine Gecko engine
 * @param geckoPreference The desired Gecko preference
 * @param geckoPreferenceValue The desired Gecko preference value
 * @param geckoPreferenceBranch The desired Gecko preference branch (Currently, user or default)
 */
internal fun setPref(
    engine: Engine,
    geckoPreference: String,
    geckoPreferenceValue: Any,
    geckoPreferenceBranch: Branch,
) {
    @OptIn(ExperimentalAndroidComponentsApi::class)
    when (geckoPreferenceValue) {
        is String -> engine.setBrowserPref(
            geckoPreference,
            geckoPreferenceValue,
            geckoPreferenceBranch,
            onSuccess = {},
            onError = {}
        )
        is Boolean -> engine.setBrowserPref(
            geckoPreference,
            geckoPreferenceValue,
            geckoPreferenceBranch,
            onSuccess = {},
            onError = {}
        )
        is Int -> engine.setBrowserPref(
            geckoPreference,
            geckoPreferenceValue,
            geckoPreferenceBranch,
            onSuccess = {},
            onError = {}
        )
        else -> throw IllegalArgumentException("Unsupported preference value type: ${geckoPreferenceValue::class.java}")
    }
}

/**
 * Set a Gecko preference (geckoPreference) to a desired user value (geckoPreferenceValue)
 * Generally, we should use this if the preference is being set as a result of user input
 * 
 * @param engine Gecko engine
 * @param geckoPreference The desired Gecko preference
 * @param geckoPreferenceValue The desired Gecko preference value
 */
internal fun setUserPref(
    engine: Engine,
    geckoPreference: String,
    geckoPreferenceValue: Any,
) {
    setPref(engine, geckoPreference, geckoPreferenceValue, Branch.USER)
}

/**
 * Set a Gecko preference (geckoPreference) to a desired default value (geckoPreferenceValue)
 * This will appear as if it was unedited by the user
 * 
 * @param engine Gecko engine
 * @param geckoPreference The desired Gecko preference
 * @param geckoPreferenceValue The desired Gecko preference value
 */
internal fun setDefaultPref(
    engine: Engine,
    geckoPreference: String,
    geckoPreferenceValue: Any,
) {
    setPref(engine, geckoPreference, geckoPreferenceValue, Branch.DEFAULT)
}

/**
 * Get the value of a Gecko preference (geckoPreference)
 *
 * @param engine Gecko engine
 * @param geckoPreference The desired Gecko preference
 */
internal suspend fun getPref(
    engine: Engine,
    geckoPreference: String,
): Any? = suspendCancellableCoroutine { continuation ->
    @OptIn(ExperimentalAndroidComponentsApi::class)
    engine.getBrowserPref(
        geckoPreference,
        onSuccess = { geckoPref ->
            continuation.resume(geckoPref.value)
        },
        onError = {
            continuation.resume(null)
        }
    )
}

/**
 * Reset the value of a Gecko preference (geckoPreference)
 * This applies to user prefs
 *
 * @param engine Gecko engine
 * @param geckoPreference The desired Gecko preference
 */
internal fun clearPref(
    engine: Engine,
    geckoPreference: String,
) {
    @OptIn(ExperimentalAndroidComponentsApi::class)
    engine.clearBrowserUserPref(
        geckoPreference,
        onSuccess = {},
        onError = {}
    )
}
