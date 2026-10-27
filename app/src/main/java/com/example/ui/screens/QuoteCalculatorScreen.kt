package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.CartItem
import com.example.model.DemoCatalogData
import com.example.model.Product
import com.example.ui.components.VendeMasMonochromeLogo
import com.example.ui.components.VendeMasSymbolBadge
import com.example.ui.theme.BorderGray
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanSoft
import com.example.ui.theme.LightGray
import com.example.ui.theme.SuccessGreen

@Composable
fun QuoteCalculatorScreen(
    cartItems: List<CartItem>,
    onUpdateQuantity: (Product, Int) -> Unit,
    onClearCart: () -> Unit,
    onLoadSampleOrder: () -> Unit,
    onNavigateToCatalogs: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isPdfPreviewMode by remember { mutableStateOf(false) }

    val subtotalWithoutDiscount = cartItems.sumOf { it.product.unitPrice * it.quantity }
    val finalTotal = cartItems.sumOf { it.totalPrice }
    val totalSavings = subtotalWithoutDiscount - finalTotal

    if (isPdfPreviewMode) {
        // High-Contrast Monochromatic PDF / Quote Sheet View
        PdfQuoteSheetPreview(
            cartItems = cartItems,
            subtotal = subtotalWithoutDiscount,
            savings = totalSavings,
            total = finalTotal,
            onBack = { isPdfPreviewMode = false },
            onShare = {
                val shareText = composeOrderWhatsAppMessage(cartItems, subtotalWithoutDiscount, totalSavings, finalTotal)
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Enviar Ficha de Pedido PDF"))
            }
        )
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DeepBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VendeMasSymbolBadge(
                        size = 46.dp,
                        containerColor = DeepBlue,
                        vColor = BrandWhite,
                        plusColor = ElectricCyan,
                        cColor = BrandWhite,
                        shapeCornerRadius = 10.dp,
                        showBorder = true,
                        borderColor = ElectricCyan
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Cotizador Comercial B2B",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandWhite
                        )
                        Text(
                            text = "Descuentos automáticos por escala y bulto cerrado",
                            fontSize = 12.sp,
                            color = LightGray.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        if (cartItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = DarkGray.copy(alpha = 0.4f),
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No hay productos en la cotización",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Explora los catálogos mayoristas y suma artículos para ver la aplicación de escalas de precio en vivo.",
                            fontSize = 13.sp,
                            color = DarkGray.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedButton(
                                onClick = onLoadSampleOrder,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Cargar Pedido Ejemplo", fontSize = 12.sp)
                            }
                            Button(
                                onClick = onNavigateToCatalogs,
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DeepBlue,
                                    contentColor = BrandWhite
                                )
                            ) {
                                Text("Ver Catálogos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        } else {
            // Header with action bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Artículos Seleccionados (${cartItems.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                    OutlinedButton(
                        onClick = onClearCart,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Vaciar", fontSize = 11.sp)
                    }
                }
            }

            // Cart Items
            items(cartItems) { item ->
                QuoteItemCard(
                    cartItem = item,
                    onIncrement = { onUpdateQuantity(item.product, item.quantity + 1) },
                    onDecrement = { onUpdateQuantity(item.product, item.quantity - 1) },
                    onRemove = { onUpdateQuantity(item.product, 0) }
                )
            }

            // Summary Calculation Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quote_summary_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resumen de la Orden",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Subtotal (Minorista):", fontSize = 13.sp, color = DarkGray)
                            Text(
                                text = "$ ${String.format("%.2f", subtotalWithoutDiscount)}",
                                fontSize = 13.sp,
                                color = DarkGray
                            )
                        }

                        if (totalSavings > 0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalOffer,
                                        contentDescription = null,
                                        tint = SuccessGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ahorro Escala Mayorista:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }
                                Text(
                                    text = "- $ ${String.format("%.2f", totalSavings)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = BorderGray)
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Cotizado:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = DeepBlue
                            )
                            Text(
                                text = "$ ${String.format("%.2f", finalTotal)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = DeepBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action Buttons: Send WhatsApp & View Monochromatic PDF
                        Button(
                            onClick = {
                                val message = composeOrderWhatsAppMessage(cartItems, subtotalWithoutDiscount, totalSavings, finalTotal)
                                val uri = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode(message)}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepBlue,
                                contentColor = BrandWhite
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("quote_whatsapp_button")
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Enviar Pedido por WhatsApp",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = { isPdfPreviewMode = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("quote_pdf_preview_button")
                        ) {
                            Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = DeepBlue, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ficha de Pedido PDF (Monocromo)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepBlue
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun QuoteItemCard(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BrandWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = cartItem.product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                    Text(
                        text = "SKU: ${cartItem.product.sku} • ${cartItem.product.packDescription}",
                        fontSize = 11.sp,
                        color = DarkGray.copy(alpha = 0.7f)
                    )
                }
                IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Quitar",
                        tint = DarkGray.copy(alpha = 0.5f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quantity Selector
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = LightGray,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) {
                            Icon(imageVector = Icons.Default.Remove, contentDescription = "Menos", modifier = Modifier.size(16.dp))
                        }
                        Text(
                            text = "${cartItem.quantity}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Más", modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Price display with wholesale discount indicator
                Column(horizontalAlignment = Alignment.End) {
                    if (cartItem.isWholesaleApplied) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SuccessGreen.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "Tarifa Mayorista Aplicada",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "$ ${String.format("%.2f", cartItem.totalPrice)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = DeepBlue
                    )
                    Text(
                        text = "($ ${String.format("%.2f", cartItem.effectiveUnitPrice)} c/u)",
                        fontSize = 10.sp,
                        color = DarkGray.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PdfQuoteSheetPreview(
    cartItems: List<CartItem>,
    subtotal: Double,
    savings: Double,
    total: Double,
    onBack: () -> Unit,
    onShare: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGray,
                    contentColor = DeepBlue
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Volver", fontSize = 12.sp)
            }

            Button(
                onClick = onShare,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricCyan,
                    contentColor = DeepBlue
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Compartir Ficha", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Printable Sheet Surface (White sheet with clean black ink)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(8.dp),
            color = BrandWhite,
            shadowElevation = 6.dp
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    // Monochromatic Header
                    VendeMasMonochromeLogo(
                        onDark = false,
                        size = 36.dp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "ORDEN DE COTIZACIÓN COMERCIAL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = DarkGray
                    )
                    Text(
                        text = "Emitido: Octubre 2026 • Documento Oficial Vende Más — Catálogos",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = DarkGray.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(thickness = 1.5.dp, color = DarkGray)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(cartItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${item.quantity}x ${item.product.name}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkGray
                            )
                            Text(
                                text = "SKU: ${item.product.sku} | Unit: $ ${String.format("%.2f", item.effectiveUnitPrice)}",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = DarkGray.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = "$ ${String.format("%.2f", item.totalPrice)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = DarkGray
                        )
                    }
                    HorizontalDivider(thickness = 0.5.dp, color = BorderGray)
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "SUBTOTAL:", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        Text(text = "$ ${String.format("%.2f", subtotal)}", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }
                    if (savings > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "DESCUENTO MAYORISTA:", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            Text(text = "- $ ${String.format("%.2f", savings)}", fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(thickness = 1.5.dp, color = DarkGray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TOTAL ESTIMADO:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "$ ${String.format("%.2f", total)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Condiciones: Precios sujetos a confirmación de stock. Validez: 7 días hábiles.\n" +
                                "Presentación comercial estandarizada bajo norma Vende Más — Catálogos (V+C).\n" +
                                "“Tus productos, mejor presentados”.",
                        fontSize = 9.sp,
                        color = DarkGray.copy(alpha = 0.7f),
                        lineHeight = 13.sp
                    )
                }
            }
        }
    }
}

private fun composeOrderWhatsAppMessage(
    items: List<CartItem>,
    subtotal: Double,
    savings: Double,
    total: Double
): String {
    val builder = StringBuilder()
    builder.append("📋 *PEDIDO COMERCIAL — VENDE MÁS (V+C)*\n")
    builder.append("“Tus productos, mejor presentados”\n\n")
    builder.append("Detalle de productos cotizados:\n")

    items.forEach { item ->
        builder.append("• ${item.quantity}x ${item.product.name} (SKU: ${item.product.sku})\n")
        builder.append("  Precio unit.: $ ${String.format("%.2f", item.effectiveUnitPrice)} | Subtotal: $ ${String.format("%.2f", item.totalPrice)}\n")
    }

    builder.append("\n━━━━━━━━━━━━━━━━━━━━\n")
    builder.append("Subtotal: $ ${String.format("%.2f", subtotal)}\n")
    if (savings > 0) {
        builder.append("Ahorro Mayorista: - $ ${String.format("%.2f", savings)}\n")
    }
    builder.append("*TOTAL: $ ${String.format("%.2f", total)}*\n")
    builder.append("━━━━━━━━━━━━━━━━━━━━\n\n")
    builder.append("Generado con la plataforma oficial de *Vende Más — Catálogos*.")
    return builder.toString()
}
