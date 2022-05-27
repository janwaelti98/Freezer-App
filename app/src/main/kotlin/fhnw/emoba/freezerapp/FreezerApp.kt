package fhnw.emoba.freezerapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.freezerapp.data.FreezerService
import fhnw.emoba.freezerapp.data.impl.RemoteFreezerService
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.AppUI

object FreezerApp : EmobaApp {
    private lateinit var model: FreezerModel

    override fun initialize(activity: ComponentActivity) {
        val freezerService = RemoteFreezerService()
        model = FreezerModel(freezerService)
        model.loadAllRadioStationsAsync()
    }

    @Composable
    override fun CreateUI() {
        AppUI(model)
    }
}