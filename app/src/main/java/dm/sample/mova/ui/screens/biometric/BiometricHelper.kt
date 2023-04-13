package dm.sample.mova.ui.screens.biometric

import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.fragment.app.FragmentActivity
import dm.sample.mova.R

enum class BiometricState {
    ENABLED, UNAVAILABLE, DISABLED
}

object BiometryHelper {
    private const val authenticators =
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL or
                BiometricManager.Authenticators.BIOMETRIC_WEAK

    fun getBiometryState(context: Context): BiometricState {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.P -> {
                    if (hasEnrolledFingerprints(context)) {
                        BiometricState.ENABLED
                    } else {
                        BiometricState.DISABLED
                    }
                }
                else -> BiometricState.ENABLED
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricState.DISABLED
            else -> BiometricState.UNAVAILABLE
        }
    }

    private fun hasEnrolledFingerprints(context: Context): Boolean {
        return FingerprintManagerCompat.from(context).hasEnrolledFingerprints()
    }

    fun getIntentForInitBiometry(): Intent {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        authenticators
                    )
                }
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                Intent(Settings.ACTION_FINGERPRINT_ENROLL)
            }
            else -> {
                Intent(Settings.ACTION_SETTINGS)
            }
        }
    }

    fun showBiometricPrompt(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback,
    ): BiometricPrompt {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.resources.getString(R.string.app_name_text))
            .setNegativeButtonText(activity.resources.getString(R.string.close_text))
            .build()
        val executor = ContextCompat.getMainExecutor(activity)
        return BiometricPrompt(activity, executor, callback).apply {
            authenticate(promptInfo)
        }
    }
}