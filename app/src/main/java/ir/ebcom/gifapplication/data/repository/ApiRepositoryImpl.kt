package ir.ebcom.gifapplication.data.repository

import ir.ebcom.gifapplication.data.ResponseState
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.data.localmodel.SearchResults
import ir.ebcom.gifapplication.data.webservice.GiphyWebServices
import ir.ebcom.gifapplication.util.toRandomGifModel
import ir.ebcom.gifapplication.util.toSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val webServices: GiphyWebServices
) : ApiRepository, BaseRepository(){
    override suspend fun getRandomGif()= flow<ResponseState<GifModel>> {
        try {
            emit(ResponseState.Loading)
            val response=webServices.getRandomGif()
            if (response.isSuccessful){
                emit(ResponseState.Success(response.body()?.toRandomGifModel()))
            }else {
                emit(ResponseState.Error(getFailedResponse(response)))
            }
        }catch (e:Exception){
            emit(ResponseState.Error(errorMapper(e)))
        }
    }

    override suspend fun searchForGifs(query: String)= flow<ResponseState<SearchResults>> {
        try {
            emit(ResponseState.Loading)
            val response=webServices.searchGifs(query = query)
            if (response.isSuccessful){
                emit(ResponseState.Success(response.body()?.toSearchResult()))
            }else{
                emit(ResponseState.Error(getFailedResponse(response)))
            }
        }catch (e: Exception){
            emit(ResponseState.Error(errorMapper(e)))
        }
    }
}