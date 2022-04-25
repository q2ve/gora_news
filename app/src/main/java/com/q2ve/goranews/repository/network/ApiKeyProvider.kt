/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network

object ApiKeyProvider: ApiKeyProviderInterface {
	//Left API keys here because each key allows only 50 requests per 12 hours. 7 starts of the app.
	//const val serverApiKey = "ac0793eb091f4bde9622dae5dfad2202" //My mail.
	//const val serverApiKey = "5b24feab76bd41d4a22d2229c7fe7b0d" //TempMail.
	//const val serverApiKey = "244c9da99c794df882d494f9b8fe1007" //TempMail. Again.
	//const val serverApiKey = "8509c8f3f79a4c78830a78b4e4d15702" //TempMail. Again. Again. <---
	
	private val keys = listOf( // Unused release test keys
		"d1a9009a630f4cb8909b3649a2c8cb71",
		"ecae96fff6324b58a2be49f0c0e1a381",
		"96fa9d7c00bc41008ae5d12a2dbed92d",
		"bbbfa3fc15b0410c8f7f84de69f46ce4",
		"fe9a8aaff5c049819001dcfe409b07b1"
	)
	private var iterator = 0
	
	override fun getApiKey(): String {
		val key = keys[iterator]
		if (iterator == keys.size - 1) iterator = 0 else iterator ++
		return key
	}
}