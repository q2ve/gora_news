/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

import com.q2ve.goranews.helpers.Constants
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitRequestBuilder {
	/**
	 * Builds retrofit request with possible certificate pinning.
	 * You can change timeout, enable/disable pinning, change server's URL and fingerprint
	 * in the Constants file.
	 */
	fun buildRequest(): RetrofitApiRequests {
		val httpBuilder = OkHttpClient.Builder()
		
		val timeout = Constants.connectionTimeout
		httpBuilder.callTimeout(timeout, TimeUnit.SECONDS)
		httpBuilder.connectTimeout(timeout, TimeUnit.SECONDS)
		httpBuilder.readTimeout(timeout, TimeUnit.SECONDS)
		httpBuilder.writeTimeout(timeout, TimeUnit.SECONDS)
		
		val client: OkHttpClient =
			if (Constants.enableCertificatePining) {
				val certificatePinner = CertificatePinner.Builder()
					.add(Constants.serverUrl, Constants.serverCertFingerprint)
					.build()
				httpBuilder.certificatePinner(certificatePinner).build()
			} else {
				httpBuilder.build()
			}
		
		return Retrofit.Builder()
			.client(client)
			.baseUrl(Constants.serverUrlApi)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(RetrofitApiRequests::class.java)
	}
}