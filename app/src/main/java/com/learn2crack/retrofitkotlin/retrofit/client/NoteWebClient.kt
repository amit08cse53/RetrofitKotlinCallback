package br.com.alexf.ceep.retrofit.client


import com.learn2crack.retrofitkotlin.retrofit.RetrofitInitializer
import com.learn2crack.retrofitkotlin.retrofit.callback
import com.learn2crack.retrofitkotlin.model.Android

class NoteWebClient {

    fun list(success: (notes: String) -> Unit, failure: (throwable: Throwable) -> Unit) {

        val call = RetrofitInitializer().noteService().list()

        call.enqueue(callback({ response ->
            response?.body()?.let { success(it.string()) }
        },
                { throwable -> throwable?.let { failure(it) } }
        ))
    }

//    fun insert(note: Android, success: (note: Android) -> Unit,
//               failure: (throwable: Throwable) -> Unit) {
//        val call = RetrofitInitializer().noteService().insert(note)
//        call.enqueue(callback({ response ->
//            response?.body()?.let {
//                success(it)
//            }
//        }, { throwable ->
//            throwable?.let {
//                failure(it)
//            }
//        }))
//    }

}