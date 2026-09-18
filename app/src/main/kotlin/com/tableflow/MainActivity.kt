package com.tableflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tableflow.navigation.TableFlowApp
import com.tableflow.ui.theme.TableFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TableFlowTheme {
                TableFlowApp()
            }
        }
    }
}
