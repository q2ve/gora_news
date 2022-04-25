/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.helpers

object Constants {
	// Left API keys here for the clarity. Otherwise, i would put it in the gradle files.
	// Each key allows only 100 requests per day. 14 starts of the app.
	//const val serverApiKey = "ac0793eb091f4bde9622dae5dfad2202" //My mail.
		//const val serverApiKey = "5b24feab76bd41d4a22d2229c7fe7b0d" //TempMail.
			const val serverApiKey = "244c9da99c794df882d494f9b8fe1007" //TempMail. Again.
				//const val serverApiKey = "8509c8f3f79a4c78830a78b4e4d15702" //TempMail. Again. Again. <---
					//const val serverApiKey = "d1a9009a630f4cb8909b3649a2c8cb71" //TempMail. For release.
	
	//Network
	const val serverUrl = "newsapi.org"
	const val serverUrlApi = "https://newsapi.org/v2/"
	const val enableCertificatePining = false
	const val serverCertFingerprint = "sha256/..."
	const val connectionTimeout: Long = 8
	const val paginationStep: Int = 20
	
	//Database
	const val realmName = "GORA_News_database"
	
	//Functional
	const val useAlternativeFeed = false
}