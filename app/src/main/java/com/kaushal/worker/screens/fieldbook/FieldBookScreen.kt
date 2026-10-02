package com.kaushal.worker.screens.fieldbook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun FieldBookScreen(onBack: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp)
    ) {
        ScreenTopBar(stringResource(R.string.fieldbook_title), onBack)
        Text(stringResource(R.string.fieldbook_subtitle), style = MaterialTheme.typography.headlineMedium)
        Text(
            stringResource(R.string.fieldbook_desc),
            color = KaushalMuted,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(Modifier.height(20.dp))
        Card {
            Column(Modifier.padding(18.dp)) {
                Text(stringResource(R.string.fieldbook_l1_title), style = MaterialTheme.typography.titleMedium, color = KaushalOrange)
                Text(stringResource(R.string.fieldbook_l1_desc), modifier = Modifier.padding(top = 6.dp))
            }
        }
        Spacer(Modifier.height(12.dp))
        Card {
            Column(Modifier.padding(18.dp)) {
                Text(stringResource(R.string.fieldbook_l2_title), style = MaterialTheme.typography.titleMedium, color = KaushalOrange)
                Text(stringResource(R.string.fieldbook_l2_desc), modifier = Modifier.padding(top = 6.dp))
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(stringResource(R.string.fieldbook_note), color = KaushalMuted)
    }
}
