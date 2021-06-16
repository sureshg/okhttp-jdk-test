import okhttp3.*
import okio.*
import java.security.*
import javax.net.ssl.*


fun main() {
    val client = OkHttpClient.Builder().apply {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(null as KeyStore?)
        val trustManagers = tmf.trustManagers.filterIsInstance<X509TrustManager>()

        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, trustManagers.toTypedArray(), SecureRandom())
        val socketFactory = sslContext.socketFactory

        connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
        sslSocketFactory(socketFactory, trustManagers[0])

    }.build()


    println("Sending GET request to https://publicobject.com...")
    val request = Request.Builder()
        .url("https://publicobject.com/helloworld.txt")
        .build()

    client.newCall(request)
        .execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            println(response.body!!.string())
        }
}