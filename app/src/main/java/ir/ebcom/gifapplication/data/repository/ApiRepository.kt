package ir.ebcom.gifapplication.data.repository

import ir.ebcom.gifapplication.data.ResponseState
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.data.localmodel.SearchResults
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun getRandomGif(): Flow<ResponseState<GifModel>>
    suspend fun searchForGifs(query: String): Flow<ResponseState<SearchResults>>
}