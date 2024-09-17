package com.example.elevatorcodingproject.views

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
            var textFieldState by remember {
                mutableStateOf("")
            }

            var liftState by remember {
                mutableStateOf(0)
            }

            var progressbarState by remember {
                mutableStateOf(0)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(modifier = Modifier
                        .height(70.dp)
                        .padding(10.dp), onClick = {

                        moveTheElevator(liftState, textFieldState.toInt(), ElevatorState.MOVE_UP) {

                        }
                    }) {
                        Text(text = "Up")
                    }

                    Button(modifier = Modifier
                        .height(70.dp)
                        .padding(10.dp),
                        onClick = {
                            moveTheElevator(liftState, textFieldState.toInt(), ElevatorState.MOVE_DOWN) {

                            }
                        }) {
                        Text(text = "Down")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    IndeterminateCircularIndicator(progressbarState)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(value = textFieldState,
                        label = {
                            Text("Enter your floor between 0-10")
                        }, onValueChange = {
                            try {
                                if(it.isDigitsOnly()) {

                                }
                            } catch (ex: Exception) {
                                print("Something went wrong")
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                    )
                }

                LaunchedEffect(key1 = progressbarState) {
                    lifecycleScope.launch {
                        delay(2000)
                        progressbarState = 0
                    }
                }
                showLiftState(liftFloorNumber = liftState)
            }
        }
    }

    @Composable
    fun showLiftState(liftFloorNumber: Int) {
        if (liftFloorNumber in 0..10) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.3f)
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
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

    private fun moveTheElevator(currentFloor: Int, destinationFloor: Int, direction: ElevatorState , elevatorReached: () -> Unit) {
        lifecycleScope.launch {
            if (currentFloor == destinationFloor) {
                System.out.println("Elevator is at required floor")
            } else if (direction == ElevatorState.MOVE_UP) {
                moveUp(currentFloor, destinationFloor)
            } else {
                moveDown(currentFloor, destinationFloor)
            }

            elevatorReached()
        }
    }

    suspend fun moveUp(currentFloor: Int, destinationFloor: Int) {
        var floorNow = currentFloor
        if (currentFloor < destinationFloor) {
            while (currentFloor < destinationFloor) {
                floorNow += 1
                delay(1000)
                System.out.println("Elevator is moving up to floor: " + destinationFloor);
            }
        } else if (currentFloor > destinationFloor) {
            System.out.println("Invalid floor");
        } else {
            System.out.println("Already at  floor")
        }

        return
    }

    suspend fun moveDown(currentFloor: Int, destinationFloor: Int) {
        var floorNow = currentFloor
        if (currentFloor > destinationFloor) {
            while (currentFloor > destinationFloor) {
                floorNow -= 1
                delay(1000)
                System.out.println("Elevator is moving up to floor: " + destinationFloor);
            }
        } else if(currentFloor < destinationFloor) {
            System.out.println("Invalid Floor")
        } else {
            System.out.println("Already at top floor");
        }
        return
    }

    enum class ElevatorState {
        MOVE_UP, MOVE_DOWN, AT_REST
    }

    private fun print(msg: String) {
        Log.d("MainActiviyt", msg)
    }
}
