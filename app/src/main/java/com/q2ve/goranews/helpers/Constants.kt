/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.helpers

object Constants {
	//Network
	const val serverUrl = "newsapi.org"
	const val serverUrlApi = "https://newsapi.org/v2/"
	//Left API keys here for the clarity. Otherwise, i would put it in the gradle files.
	//const val serverApiKey = "ac0793eb091f4bde9622dae5dfad2202" //MyOwn
	const val serverApiKey = "5b24feab76bd41d4a22d2229c7fe7b0d" //TempMail
	const val enableCertificatePining = false
	const val serverCertFingerprint = "sha256/..."
	const val connectionTimeout: Long = 8
	const val paginationStep: Int = 20
	
	//Database
	const val realmName = "GORA_News_database"
}