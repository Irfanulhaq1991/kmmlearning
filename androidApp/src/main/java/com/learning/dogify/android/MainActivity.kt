package com.learning.dogify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.learning.dogify.model.Breed
import com.learning.dogify.usecase.FetchBreedsUseCase
import com.learning.dogify.usecase.GetBreedsUseCase
import com.learning.dogify.usecase.ToggleFavouriteStateUseCase
import kotlinx.coroutines.launch

suspend fun greet()= "${FetchBreedsUseCase().invoke()}\n" +
        "${GetBreedsUseCase().invoke()}\n" +
        "${ToggleFavouriteStateUseCase().invoke(Breed
            ("toggle favourite state test", ""))}\n"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        lifecycleScope.launch {
            tv.text = greet()
        }

    }
}
