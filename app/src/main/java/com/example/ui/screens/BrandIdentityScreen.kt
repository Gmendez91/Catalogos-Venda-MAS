package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewCompact
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.ColorToken
import com.example.model.LogoVariant
import com.example.ui.components.VendeMasCompactLogo
import com.example.ui.components.VendeMasFavicon
import com.example.ui.components.VendeMasHorizontalLogo
import com.example.ui.components.VendeMasMonochromeLogo
import com.example.ui.components.VendeMasSquareIcon
import com.example.ui.components.VendeMasSymbolBadge
import com.example.ui.theme.BorderGray
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanSoft
import com.example.ui.theme.LightGray

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrandIdentityScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    var selectedVariant by remember { mutableStateOf(LogoVariant.HORIZONTAL) }
    var selectedBgColorIndex by remember { mutableIntStateOf(0) } // 0: DeepBlue, 1: LightGray, 2: White, 3: DarkGray

    val previewBgColors = listOf(
        Pair("Azul Profundo", DeepBlue),
        Pair("Gris Claro", LightGray),
        Pair("Blanco Puro", BrandWhite),
        Pair("Gris Oscuro", DarkGray)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Brand Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("brand_hero_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DeepBlue),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Official Monogram Badge
                VendeMasSymbolBadge(
                    size = 64.dp,
                    containerColor = DeepBlue,
                    vColor = BrandWhite,
                    plusColor = ElectricCyan,
                    cColor = BrandWhite,
                    shapeCornerRadius = 14.dp,
                    showBorder = true,
                    borderColor = ElectricCyan
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = BrandTokens.BRAND_NAME_PRIMARY,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = BrandWhite,
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = "CATÁLOGOS COMERCIALES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ElectricCyan,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ElectricCyan.copy(alpha = 0.15f),
                    border = null
                ) {
                    Text(
                        text = "“${BrandTokens.BRAND_SLOGAN}”",
                        color = ElectricCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = BrandTokens.BRAND_VALUE_PROP,
                    fontSize = 14.sp,
                    color = LightGray.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Section: Explorador Interactivo de Variantes de Logo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ViewCompact,
                        contentDescription = null,
                        tint = ElectricCyan,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Variantes del Logotipo V+C",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Selecciona una variante para visualizar su aplicación técnica y comportamiento en distintos fondos:",
                    fontSize = 13.sp,
                    color = DarkGray.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Variant Filter Chips
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LogoVariant.values().forEach { variant ->
                        val isSelected = selectedVariant == variant
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedVariant = variant },
                            label = {
                                Text(
                                    text = variant.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DeepBlue,
                                selectedLabelColor = ElectricCyan,
                                containerColor = LightGray,
                                labelColor = DarkGray
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) ElectricCyan else BorderGray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Canvas Background Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fondo de prueba:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGray
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        previewBgColors.forEachIndexed { index, pair ->
                            val isSelected = selectedBgColorIndex == index
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(pair.second)
                                    .border(
                                        width = if (isSelected) 2.5.dp else 1.dp,
                                        color = if (isSelected) ElectricCyan else BorderGray,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedBgColorIndex = index }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Interactive Logo Display Canvas
                val currentBg = previewBgColors[selectedBgColorIndex].second
                val isDarkBg = currentBg == DeepBlue || currentBg == DarkGray

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(currentBg)
                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (selectedVariant) {
                        LogoVariant.HORIZONTAL -> {
                            VendeMasHorizontalLogo(
                                isDarkTheme = isDarkBg,
                                symbolSize = 54.dp,
                                titleSize = 24.sp,
                                showSubtitle = true
                            )
                        }
                        LogoVariant.COMPACT -> {
                            VendeMasCompactLogo(
                                isDarkTheme = isDarkBg
                            )
                        }
                        LogoVariant.SQUARE_ICON -> {
                            VendeMasSquareIcon(
                                size = 96.dp
                            )
                        }
                        LogoVariant.FAVICON -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    VendeMasFavicon(size = 20.dp)
                                    VendeMasFavicon(size = 32.dp)
                                    VendeMasFavicon(size = 48.dp)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "16dp / 32dp / 48dp (Micro-escala)",
                                    fontSize = 11.sp,
                                    color = if (isDarkBg) LightGray else DarkGray.copy(alpha = 0.7f)
                                )
                            }
                        }
                        LogoVariant.MONOCHROME -> {
                            VendeMasMonochromeLogo(
                                onDark = isDarkBg,
                                size = 48.dp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Variant Details & Guidelines
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = LightGray,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = selectedVariant.subtitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Aplicación recomendada: ${selectedVariant.usage}",
                            fontSize = 12.sp,
                            color = DarkGray,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Section: Paleta Oficial de Colores
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = DeepBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Paleta Oficial de Colores",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Toca cualquier color para copiar su código HEX al portapapeles:",
                    fontSize = 13.sp,
                    color = DarkGray.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                BrandTokens.officialColors.forEach { token ->
                    ColorTokenRow(
                        token = token,
                        onCopy = {
                            clipboardManager.setText(AnnotatedString(token.hex))
                            Toast.makeText(context, "Copiado: ${token.name} (${token.hex})", Toast.LENGTH_SHORT).show()
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // Section: Concepto del Símbolo & Mensaje de Marca
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = ElectricCyan,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Fundamentos de Marca & Concepto V+C",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                ConceptPillar(
                    title = "Letra V (Venta & Visibilidad)",
                    desc = "Representa la aceleración comercial, el volumen de ventas y el despegue de la presencia de cada producto en el mercado."
                )

                Spacer(modifier = Modifier.height(8.dp))

                ConceptPillar(
                    title = "Signo + (Crecimiento & 'Más')",
                    desc = "Punto de anclaje visual en celeste eléctrico (#63D5F2). Simboliza suma de valor, rentabilidad agregada y el 'Más' de Vende Más."
                )

                Spacer(modifier = Modifier.height(8.dp))

                ConceptPillar(
                    title = "Letra C (Catálogos & Comunicación)",
                    desc = "Define la especialidad: catálogos profesionales, organización integral de inventarios y comunicación comercial transparente."
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Brand Strategy Note
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ElectricCyanSoft,
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = DeepBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Posicionamiento Comercial",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = BrandTokens.BRAND_POSITIONING,
                            fontSize = 12.sp,
                            color = DarkGray,
                            lineHeight = 17.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "La marca comunica una herramienta de ventas de alto impacto para negocios, no un simple servicio de diseño.",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepBlue
                        )
                    }
                }
            }
        }

        // Section: Guía de Uso del Nombre (Regla Oficial)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = LightGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Reglas de Escritura y Nomenclatura",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• En títulos principales y elementos visuales: “Vende Más”\n" +
                            "• En textos secundarios, bajadas y contratos: “Vende Más — Catálogos”\n" +
                            "• Símbolo abreviado: V+C (siempre con el '+' en Celeste Eléctrico)\n" +
                            "• Reemplazo completado: Todo rastro anterior de “Gabriel Méndez — Catálogos Digitales” ha sido unificado bajo la marca oficial Vende Más.",
                    fontSize = 12.sp,
                    color = DarkGray,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun ColorTokenRow(
    token: ColorToken,
    onCopy: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCopy() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color Swatch
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(token.color)
                    .border(1.dp, BorderGray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = token.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = BrandWhite,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                    ) {
                        Text(
                            text = token.hex,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkGray,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = token.role,
                    fontSize = 11.sp,
                    color = DarkGray.copy(alpha = 0.85f),
                    lineHeight = 15.sp
                )
            }

            IconButton(onClick = onCopy) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copiar color",
                    tint = DeepBlue.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ConceptPillar(
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(ElectricCyan)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = DarkGray.copy(alpha = 0.85f),
                lineHeight = 16.sp
            )
        }
    }
}
