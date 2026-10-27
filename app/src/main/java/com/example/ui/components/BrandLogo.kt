package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.LogoVariant
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LightGray

/**
 * The geometric V+C Monogram badge:
 * Combines modern letterforms V and C with an electric cyan '+' in the center.
 */
@Composable
fun VendeMasSymbolBadge(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    containerColor: Color = DeepBlue,
    vColor: Color = BrandWhite,
    plusColor: Color = ElectricCyan,
    cColor: Color = BrandWhite,
    shapeCornerRadius: Dp = 10.dp,
    elevation: Dp = 0.dp,
    showBorder: Boolean = false,
    borderColor: Color = ElectricCyan.copy(alpha = 0.4f)
) {
    Surface(
        modifier = modifier
            .size(size)
            .testTag("vc_symbol_badge"),
        shape = RoundedCornerShape(shapeCornerRadius),
        color = containerColor,
        shadowElevation = elevation
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (showBorder) Modifier.border(1.dp, borderColor, RoundedCornerShape(shapeCornerRadius))
                    else Modifier
                )
                .padding(horizontal = size * 0.08f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // V letterform
                Text(
                    text = "V",
                    color = vColor,
                    fontSize = (size.value * 0.48f).sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = (-0.5).sp
                )
                // + Plus symbol (growth, addition, 'Más')
                Text(
                    text = "+",
                    color = plusColor,
                    fontSize = (size.value * 0.52f).sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(horizontal = size * 0.02f)
                )
                // C letterform (Catálogos, commerce)
                Text(
                    text = "C",
                    color = cColor,
                    fontSize = (size.value * 0.48f).sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.SansSerif,
                    letterSpacing = (-0.5).sp
                )
            }
        }
    }
}

/**
 * 1. Horizontal Logo:
 * Full brand lockup: Symbol [V+C] + Title "Vende Más" + Subtitle "Catálogos".
 */
@Composable
fun VendeMasHorizontalLogo(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    symbolSize: Dp = 46.dp,
    titleSize: TextUnit = 22.sp,
    showSubtitle: Boolean = true
) {
    val titleColor = if (isDarkTheme) BrandWhite else DeepBlue
    val subtitleColor = if (isDarkTheme) ElectricCyan else DarkGray.copy(alpha = 0.75f)

    Row(
        modifier = modifier.testTag("logo_horizontal"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VendeMasSymbolBadge(
            size = symbolSize,
            containerColor = DeepBlue,
            vColor = BrandWhite,
            plusColor = ElectricCyan,
            cColor = BrandWhite,
            shapeCornerRadius = 10.dp,
            showBorder = true,
            borderColor = ElectricCyan.copy(alpha = 0.35f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = BrandTokens.BRAND_NAME_PRIMARY,
                    color = titleColor,
                    fontSize = titleSize,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.4).sp
                )
                // Highlight dot
                Box(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 2.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(ElectricCyan)
                )
            }

            if (showSubtitle) {
                Text(
                    text = "Catálogos",
                    color = subtitleColor,
                    fontSize = (titleSize.value * 0.56f).sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp
                )
            }
        }
    }
}

/**
 * 2. Compact Logo:
 * Streamlined lockup ideal for mobile AppBars and sticky headers.
 */
@Composable
fun VendeMasCompactLogo(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false
) {
    val titleColor = if (isDarkTheme) BrandWhite else DeepBlue

    Row(
        modifier = modifier.testTag("logo_compact"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VendeMasSymbolBadge(
            size = 34.dp,
            containerColor = DeepBlue,
            vColor = BrandWhite,
            plusColor = ElectricCyan,
            cColor = BrandWhite,
            shapeCornerRadius = 7.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Vende Más",
            color = titleColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * 3. Square Icon (App Icon & Social Media avatar):
 * High-visibility icon with rounded squircle and polished contrast.
 */
@Composable
fun VendeMasSquareIcon(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .testTag("logo_square_icon")
            .clip(RoundedCornerShape(size * 0.22f))
            .background(DeepBlue)
            .border(
                width = 1.5.dp,
                color = ElectricCyan.copy(alpha = 0.5f),
                shape = RoundedCornerShape(size * 0.22f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "V",
                color = BrandWhite,
                fontSize = (size.value * 0.44f).sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
            Text(
                text = "+",
                color = ElectricCyan,
                fontSize = (size.value * 0.48f).sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = size * 0.02f)
            )
            Text(
                text = "C",
                color = BrandWhite,
                fontSize = (size.value * 0.44f).sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
        }
    }
}

/**
 * 4. Favicon / Micro-Scale variant (16-32dp):
 * Extra bold strokes for high legibility at extreme small sizes.
 */
@Composable
fun VendeMasFavicon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .testTag("logo_favicon")
            .clip(RoundedCornerShape(size * 0.25f))
            .background(DeepBlue),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "V",
                color = BrandWhite,
                fontSize = (size.value * 0.50f).sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "+",
                color = ElectricCyan,
                fontSize = (size.value * 0.54f).sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "C",
                color = BrandWhite,
                fontSize = (size.value * 0.50f).sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

/**
 * 5. Monochromatic Version:
 * High contrast 1-color application for official PDF documents, invoices and receipts.
 */
@Composable
fun VendeMasMonochromeLogo(
    modifier: Modifier = Modifier,
    onDark: Boolean = false,
    size: Dp = 40.dp
) {
    val inkColor = if (onDark) BrandWhite else DarkGray
    val bgColor = if (onDark) DarkGray else LightGray

    Row(
        modifier = modifier.testTag("logo_monochrome"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(6.dp))
                .background(bgColor)
                .border(1.2.dp, inkColor, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "V",
                    color = inkColor,
                    fontSize = (size.value * 0.46f).sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "+",
                    color = inkColor,
                    fontSize = (size.value * 0.48f).sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 1.dp)
                )
                Text(
                    text = "C",
                    color = inkColor,
                    fontSize = (size.value * 0.46f).sp,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = BrandTokens.BRAND_NAME_PRIMARY,
                color = inkColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.2).sp
            )
            Text(
                text = "CATÁLOGOS COMERCIALES",
                color = inkColor.copy(alpha = 0.7f),
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )
        }
    }
}
