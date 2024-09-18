package com.example.elevatorcodingproject.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var elevatorDestinationFloorState by remember {
                mutableStateOf("")
            }

            var elevatorCurrentFloorState by remember {
                mutableIntStateOf(0)
            }

            var progressbarState by remember {
                mutableIntStateOf(0)
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                showLiftState(liftFloorNumber = elevatorCurrentFloorState)

                Row(verticalAlignment = Alignment.Top) {
                    Button(modifier = Modifier
                        .height(70.dp)
                        .padding(10.dp), onClick = {
                        var destinationFloor = 0
                        try {
                            destinationFloor = elevatorDestinationFloorState.toInt()
                        } catch (_: Exception) {
                        }

                        if (elevatorCurrentFloorState > destinationFloor) {
                            lifecycleScope.launch {
                                progressbarState = 1
                                moveDown(elevatorCurrentFloorState, destinationFloor, {
                                    elevatorCurrentFloorState = it
                                }, {
                                    progressbarState = 0
                                    elevatorDestinationFloorState = ""
                                })
                            }
                        } else {
                            lifecycleScope.launch {
                                progressbarState = 1
                                moveUp(elevatorCurrentFloorState, destinationFloor, {
                                    elevatorCurrentFloorState = it
                                }, {
                                    progressbarState = 0
                                    elevatorDestinationFloorState = ""
                                })
                            }
                        }
                    }) {
                        Text(text = "Up")
                    }

                    Button(modifier = Modifier
                        .height(70.dp)
                        .padding(10.dp),
                        onClick = {

                            var destinationFloor = 0
                            try {
                                destinationFloor = elevatorDestinationFloorState.toInt()
                            } catch (ex: Exception) {
                                print(ex.message)
                            }
                            if (elevatorCurrentFloorState < destinationFloor) {
                                lifecycleScope.launch {
                                    progressbarState = 1
                                    moveUp(elevatorCurrentFloorState, destinationFloor, {
                                        elevatorCurrentFloorState = it
                                    }, {
                                        progressbarState = 0
                                        elevatorDestinationFloorState = ""
                                    })
                                }
                            } else {
                                lifecycleScope.launch {
                                    progressbarState = 1
                                    moveDown(elevatorCurrentFloorState, destinationFloor, {
                                        elevatorCurrentFloorState = it
                                    }, {
                                        progressbarState = 0
                                        elevatorDestinationFloorState = ""
                                    })
                                }
                            }
                        }) {
                        Text(text = "Down")
                    }

                    IndeterminateCircularIndicator(progressbarState)
                }

                val keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
                TextField(value = elevatorDestinationFloorState,
                    label = {
                        Text("Enter your floor between 0-10")
                    }, onValueChange = {
                        try {
                            if (it.isDigitsOnly()) {
                                if(it.toInt() in 0..10) {
                                    elevatorDestinationFloorState = it
                                } else {
                                    elevatorDestinationFloorState = ""
                                }
                            } else {
                                print("Please enter digits")
                                elevatorDestinationFloorState = ""
                            }
                        } catch (ex: Exception) {
                            print("Something went wrong")
                            elevatorDestinationFloorState = ""
                        }
                    },
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

    private suspend fun moveUp(
        elevatorCurrentFloorState: Int,
        destinationFloor: Int,
        currentFloor: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        lifecycleScope.launch {
            var currentFloor = elevatorCurrentFloorState
            while (currentFloor < destinationFloor) {
                delay(1000)
                currentFloor += 1
                print("Elevator is moving Down to floor: $currentFloor")
                currentFloor(currentFloor)
            }
            onComplete()
        }
    }

    private suspend fun moveDown(
        elevatorCurrentFloorState: Int,
        destinationFloor: Int,
        currentFloor: (Int) -> Unit,
        onComplete: () -> Unit
    ) {
        var currentFloor = elevatorCurrentFloorState
        while (currentFloor > destinationFloor) {
            delay(1000)
            currentFloor -= 1
            print("Elevator is moving Down to floor: $currentFloor")
            currentFloor(currentFloor)
        }
        onComplete()
    }

    @Composable
    fun showLiftState(liftFloorNumber: Int) {
        if (liftFloorNumber in 0..10) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Green)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                style = TextStyle.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Lift is at $liftFloorNumber"
            )
        }
    }

    @Composable
    fun IndeterminateCircularIndicator(progressbarState: Int) {
        if (progressbarState <= 0 || progressbarState >= 10) return

        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .padding(10.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
