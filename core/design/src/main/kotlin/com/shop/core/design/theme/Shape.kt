package com.shop.core.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(10.dp),
    large = RoundedCornerShape(12.dp),
)

val Shapes.oval: RoundedCornerShape
    get() = RoundedCornerShape(Int.MAX_VALUE.dp)
