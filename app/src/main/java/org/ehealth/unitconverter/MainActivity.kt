package org.ehealth.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ehealth.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = MaterialTheme.colorScheme.background,
                ) { innerPadding ->
                    Surface {
                        UnitConverter(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier) {


    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("0.0") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    val conversionFactor = remember { mutableStateOf(1.0) }
    val oConversionFactor = remember { mutableStateOf(1.0) }


    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0;
        val result = (
                inputValueDouble * conversionFactor.value * 100.0 / oConversionFactor.value
                ).roundToInt() / 100.0;
        outputValue = result.toString();
    }

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        here all the UI element will be stacked below each other

//        header
        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(16.dp))

//        input filled
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it;
                convertUnits();
            },
            label = {
                Text(text = "Enter value");
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

//        selecting the units
        Row {
            val context = LocalContext.current;

            Box {
                Button(onClick = { iExpanded = true }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }

                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
//                            change the
                            inputUnit = "Centimeters";
                            iExpanded = false;
                            conversionFactor.value = 0.01;
                            convertUnits();
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            inputUnit = "Meters";
                            iExpanded = false;
                            conversionFactor.value = 1.0;
                            convertUnits();
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            inputUnit = "Feet";
                            iExpanded = false;
                            conversionFactor.value = 0.3048;
                            convertUnits()
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            inputUnit = "Millimeters";
                            iExpanded = false;
                            conversionFactor.value = 0.001;
                            convertUnits()
                        }
                    );
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpanded = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            outputUnit = "Centimeters";
                            oExpanded = false;
                            oConversionFactor.value = 0.01
                            convertUnits()
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            outputUnit = "Meters";
                            oExpanded = false;
                            oConversionFactor.value = 1.0;
                            convertUnits();
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            outputUnit = "Feet";
                            oExpanded = false;
                            oConversionFactor.value = 0.3048;
                            convertUnits();
                        }
                    );
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            outputUnit = "Millimeters";
                            oExpanded = false;
                            oConversionFactor.value = 0.001;
                            convertUnits();
                        }
                    );
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

         Text(
            text = "Results: $outputValue $outputUnit",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
        );
    }
}


@Preview()
@Composable
fun UnitConverterPreview() {
    UnitConverter(modifier = Modifier)
}