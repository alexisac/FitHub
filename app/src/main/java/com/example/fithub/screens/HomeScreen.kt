package com.example.fithub.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fithub.common.Constants
import com.example.fithub.common.messages.ScreenMessages
import com.example.fithub.models.WeightChartPoint
import com.example.fithub.screens.reusableComponents.ThemeToggle
import com.example.fithub.ui.theme.AppColors
import com.example.fithub.viewModels.WeightViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.Zoom
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen (
    viewModel: WeightViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    goToAddWeightMenu: () -> Unit,
    goToManageWorkoutSplit: () -> Unit,
    goToManageExercises: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val colors = AppColors.colors(isDarkTheme)
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        IconButton(
            onClick = {
                showMenu = true
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 40.dp,
                    end = 20.dp
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = ScreenMessages.MENU_DESCRIPTION,
                tint = colors.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 100.dp
                )
        ) {
            WeightChartCard(
                points = uiState.weightChart,
                primaryTextColor = colors.primaryText,
                secondaryTextColor = colors.secondaryText,
                borderColor = colors.border,
                containerColor = colors.card,
                chartColor = colors.primary
            )
        }

        RightSideMenu(
            visible = showMenu,
            isDarkTheme = isDarkTheme,
            onDismiss = {
                showMenu = false
            },
            goToManageWorkoutSplit = {
                showMenu = false
                goToManageWorkoutSplit()
            },
            goToAddWeightMenu = {
                showMenu = false
                goToAddWeightMenu()
            },
            goToManageExercises = {
                showMenu = false
                goToManageExercises()
            },
            onThemeChange = onThemeChange
        )
    }
}

@Composable
private fun RightSideMenu(
    visible: Boolean,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
    goToManageWorkoutSplit: () -> Unit,
    goToAddWeightMenu: () -> Unit,
    goToManageExercises: () -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    val colors = AppColors.colors(isDarkTheme)

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) {
                    onDismiss()
                }
        ) {
            AnimatedVisibility(
                visible = visible,
                modifier = Modifier.align(Alignment.CenterEnd),
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth ->
                        fullWidth
                    }
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { fullWidth ->
                        fullWidth
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.65f)
                        .background(colors.card)
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ){}
                        .padding(
                            horizontal = 20.dp,
                            vertical = 48.dp
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = ScreenMessages.MENU_TITLE,
                            color = colors.primaryText,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = ScreenMessages.CLOSE_DESCRIPTION,
                                tint = colors.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    MenuButton(
                        text = ScreenMessages.MANAGE_WORKOUT_BUTTON,
                        icon = Icons.Outlined.FitnessCenter,
                        primaryTextColor = colors.primaryText,
                        containerColor = colors.background,
                        iconColor = colors.primary,
                        onClick = goToManageWorkoutSplit
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MenuButton(
                        text = ScreenMessages.ADD_WEIGHT_BUTTON,
                        icon = Icons.Outlined.MonitorWeight,
                        primaryTextColor = colors.primaryText,
                        containerColor = colors.background,
                        iconColor = colors.primary,
                        onClick = goToAddWeightMenu
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MenuButton(
                        text = ScreenMessages.MANAGE_EXERCISES_BUTTON,
                        icon = Icons.Outlined.SportsGymnastics,
                        primaryTextColor = colors.primaryText,
                        containerColor = colors.background,
                        iconColor = colors.primary,
                        onClick = goToManageExercises
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    ThemeToggle(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = onThemeChange
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuButton(
    text: String,
    icon: ImageVector,
    primaryTextColor: Color,
    containerColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(18.dp),
        border = null,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = primaryTextColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = primaryTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun WeightChartCard(
    points: List<WeightChartPoint>,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    borderColor: Color,
    containerColor: Color,
    chartColor: Color
) {
    val availablePoints = points.filter { it.weight != null }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = ScreenMessages.WEIGHT_PROGRESS_TITLE,
                color = primaryTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = ScreenMessages.weightProgressSubtitle(Constants.WEIGHT_CHART_DAYS.toString()),
                color = secondaryTextColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (availablePoints.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ScreenMessages.NO_WEIGHT_RECORDS_AVAILABLE,
                        color = secondaryTextColor,
                        fontSize = 15.sp
                    )
                }
            } else {
                WeightLineChart(
                    points = points,
                    chartColor = chartColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
private fun WeightLineChart(
    points: List<WeightChartPoint>,
    chartColor: Color,
    modifier: Modifier = Modifier
) {
    val availablePoints = remember(points) {
        points.filter { point ->
            point.weight != null
        }
    }

    if (availablePoints.isEmpty()) {
        return
    }

    val weights = availablePoints.mapNotNull { point ->
        point.weight
    }

    val minimumWeight = weights.minOrNull() ?: return
    val maximumWeight = weights.maxOrNull() ?: return

    val minimumAxisValue = kotlin.math.floor(minimumWeight - 1.0)
    val maximumAxisValue = kotlin.math.ceil(maximumWeight + 1.0)

    val modelProducer = remember {
        CartesianChartModelProducer()
    }

    LaunchedEffect(availablePoints) {
        modelProducer.runTransaction {
            lineSeries {
                series(
                    x = availablePoints.map { point ->
                        point.date.toEpochDay()
                    },
                    y = weights
                )
            }
        }
    }

    CartesianChartHost(
        modifier = modifier,
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(
                            Fill(chartColor)
                        ),
                        pointProvider = LineCartesianLayer.PointProvider.single(
                            LineCartesianLayer.Point(
                                component = rememberShapeComponent(
                                    fill = Fill(chartColor),
                                    shape = CircleShape
                                )
                            )
                        )
                    )
                ),
                rangeProvider = CartesianLayerRangeProvider.fixed(
                    minY = minimumAxisValue,
                    maxY = maximumAxisValue
                )
            ),
            startAxis = VerticalAxis.rememberStart(
                valueFormatter = CartesianValueFormatter.decimal(
                    decimalCount = 1
                ),
                itemPlacer = remember {
                    VerticalAxis.ItemPlacer.step(
                        step = { Constants.WEIGHT_CHART_STEP }
                    )
                }
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = { _, value, _ ->
                    LocalDate
                        .ofEpochDay(value.toLong())
                        .format(
                            DateTimeFormatter.ofPattern(Constants.DATE_CHART_FORMATTER)
                        )
                }
            )
        ),
        modelProducer = modelProducer,
        zoomState = rememberVicoZoomState(
            zoomEnabled = false,
            initialZoom = Zoom.Content
        ),
        scrollState = rememberVicoScrollState(
            scrollEnabled = false
        )
    )
}