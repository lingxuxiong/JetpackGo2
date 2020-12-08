package vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GreetingViewModel: ViewModel() {

    private val _greeting = MutableLiveData("Hello data binding")
    val greeting: LiveData<String> = _greeting

    fun changeGreeting(greetingMessage: String) {
        _greeting.value = greetingMessage
    }
}