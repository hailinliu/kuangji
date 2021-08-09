package it.mbkj.lib.base

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class HttpsUtil {
    companion object{
        fun getSslSocketFactory(): SSLSocketFactory? {
            return try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(
                    null as Array<KeyManager?>?,
                    arrayOf(UnSafeTrustManager()),
                    SecureRandom()
                )
                sslContext.socketFactory
            } catch (var1: NoSuchAlgorithmException) {
                throw AssertionError(var1)
            } catch (var2: KeyManagementException) {
                throw AssertionError(var2)
            }
        }
        class UnSafeTrustManager : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }

        class UnSafeHostnameVerifier : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        }
    }
}