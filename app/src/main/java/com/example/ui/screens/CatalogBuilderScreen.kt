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
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.Product
import com.example.ui.components.VendeMasCompactLogo
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
fun CatalogBuilderScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var businessName by remember { mutableStateOf("Distribuidora Centro") }
    var businessType by remember { mutableStateOf("Mayorista & Distribución") }
    var whatsappNumber by remember { mutableStateOf("+5491122334455") }
    var catalogSlogan by remember { mutableStateOf("Lista Oficial de Precios Mayoristas") }
    var currencySymbol by remember { mutableStateOf("$") }

    var customProducts by remember {
        mutableStateOf(
            listOf(
                Product(
                    id = "cp1",
                    name = "Yerba Mate Premium Orgánica 500g",
                    sku = "YER-ORG500",
                    category = "Infusiones",
                    unitPrice = 4.80,
                    wholesalePrice = 3.60,
                    minWholesaleUnits = 10,
                    bulkDiscountPercent = 10,
                    packDescription = "Fardo x 10 unidades selladas",
                    description = "Estacionamiento natural de 24 meses, secado barbacuá artesanal."
                ),
                Product(
                    id = "cp2",
                    name = "Café Tostado en Granos Espresso 1kg",
                    sku = "CAF-ESP1KG",
                    category = "Cafetería",
                    unitPrice = 18.00,
                    wholesalePrice = 14.20,
                    minWholesaleUnits = 6,
                    bulkDiscountPercent = 12,
                    packDescription = "Caja x 6 bolsas valvuladas",
                    description = "Blend 100% Arábica con notas a chocolate y avellana tostada."
                )
            )
        )
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var isPreviewTab by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Tab Header: Configurar vs Vista Previa en Vivo
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BrandWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
            ) {
                TabRow(
                    selectedTabIndex = if (isPreviewTab) 1 else 0,
                    containerColor = BrandWhite
                ) {
                    Tab(
                        selected = !isPreviewTab,
                        onClick = { isPreviewTab = false },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Personalizar Datos", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                    Tab(
                        selected = isPreviewTab,
                        onClick = { isPreviewTab = true },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Vista Previa en Vivo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }
        }

        if (!isPreviewTab) {
            // Configuration Mode
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Business, contentDescription = null, tint = DeepBlue)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Datos de tu Empresa",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = businessName,
                            onValueChange = { businessName = it },
                            label = { Text("Nombre de la Distribuidora / Comercio") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = BorderGray
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = businessType,
                            onValueChange = { businessType = it },
                            label = { Text("Rubro o Tipo de Empresa") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = BorderGray
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = whatsappNumber,
                                onValueChange = { whatsappNumber = it },
                                label = { Text("WhatsApp Comercial") },
                                singleLine = true,
                                modifier = Modifier.weight(1.5f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ElectricCyan,
                                    unfocusedBorderColor = BorderGray
                                )
                            )

                            OutlinedTextField(
                                value = currencySymbol,
                                onValueChange = { currencySymbol = it },
                                label = { Text("Moneda") },
                                singleLine = true,
                                modifier = Modifier.weight(0.8f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ElectricCyan,
                                    unfocusedBorderColor = BorderGray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = catalogSlogan,
                            onValueChange = { catalogSlogan = it },
                            label = { Text("Subtítulo de Portada") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = BorderGray
                            )
                        )
                    }
                }
            }

            // Products Management Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Artículos del Catálogo (${customProducts.size})",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DeepBlue
                                )
                                Text(
                                    text = "Organizados con estándar Vende Más",
                                    fontSize = 11.sp,
                                    color = DarkGray.copy(alpha = 0.7f)
                                )
                            }

                            Button(
                                onClick = { showAddDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DeepBlue,
                                    contentColor = BrandWhite
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Agregar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        customProducts.forEach { prod ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = LightGray,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = prod.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DeepBlue)
                                        Text(text = "SKU: ${prod.sku} | $currencySymbol ${prod.wholesalePrice} (mín. ${prod.minWholesaleUnits}u)", fontSize = 11.sp, color = DarkGray)
                                    }
                                    IconButton(
                                        onClick = { customProducts = customProducts.filter { it.id != prod.id } },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = DarkGray.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { isPreviewTab = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricCyan,
                        contentColor = DeepBlue
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generar y Ver Catálogo en Vivo", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // Live Preview Mode
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepBlue),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            VendeMasCompactLogo(isDarkTheme = true)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ElectricCyanSoft
                            ) {
                                Text(
                                    text = "EN VIVO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = DeepBlue,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = businessName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = BrandWhite
                        )
                        Text(
                            text = "$businessType • $catalogSlogan",
                            fontSize = 12.sp,
                            color = ElectricCyan,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = LightGray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Pedidos: $whatsappNumber",
                                fontSize = 11.sp,
                                color = LightGray
                            )
                        }
                    }
                }
            }

            items(customProducts) { prod ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "SKU: ${prod.sku}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkGray.copy(alpha = 0.7f))
                            Text(text = "Mínimo: ${prod.minWholesaleUnits}u", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepBlue)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = prod.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DeepBlue)
                        Text(text = prod.packDescription, fontSize = 11.sp, color = DarkGray.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$currencySymbol ${String.format("%.2f", prod.wholesalePrice)} mayorista",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = DeepBlue
                            )
                            Button(
                                onClick = {
                                    val msg = "Hola $businessName! Quisiera consultar por el producto:\n${prod.name} (SKU: ${prod.sku})\nCatálogo creado con Vende Más — Catálogos."
                                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=${whatsappNumber.replace("+", "")}&text=${Uri.encode(msg)}")
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = DeepBlue, contentColor = BrandWhite)
                            ) {
                                Text("Pedir", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            item {
                // Official Catalog Footer Stamp
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VendeMasSymbolBadge(size = 40.dp, containerColor = DeepBlue, shapeCornerRadius = 8.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Vende Más — Catálogos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue
                        )
                        Text(
                            text = "“${BrandTokens.BRAND_SLOGAN}”",
                            fontSize = 11.sp,
                            color = DarkGray.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                val shareText = "Mira nuestro catálogo comercial online de *$businessName* presentado con Vende Más (V+C):\n" +
                                        "“Tus productos, mejor presentados”. Contacto pedidos: $whatsappNumber"
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Compartir Catálogo"))
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Compartir Enlace del Catálogo", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Dialog: Add Product
    if (showAddDialog) {
        var newName by remember { mutableStateOf("") }
        var newSku by remember { mutableStateOf("") }
        var newCategory by remember { mutableStateOf("General") }
        var newWholesalePrice by remember { mutableStateOf("") }
        var newMinUnits by remember { mutableStateOf("6") }
        var newPack by remember { mutableStateOf("Caja x 6 unidades") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(text = "Nuevo Producto Comercial", fontWeight = FontWeight.Bold, color = DeepBlue)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Nombre del Producto") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newSku,
                        onValueChange = { newSku = it },
                        label = { Text("Código SKU") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newWholesalePrice,
                        onValueChange = { newWholesalePrice = it },
                        label = { Text("Precio Mayorista ($)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newMinUnits,
                        onValueChange = { newMinUnits = it },
                        label = { Text("Mínimo de Compra (unidades)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newPack,
                        onValueChange = { newPack = it },
                        label = { Text("Presentación de Embalaje") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsedPrice = newWholesalePrice.toDoubleOrNull() ?: 10.0
                        val parsedMin = newMinUnits.toIntOrNull() ?: 6
                        val created = Product(
                            id = "cp_${System.currentTimeMillis()}",
                            name = if (newName.isBlank()) "Producto Nuevo" else newName,
                            sku = if (newSku.isBlank()) "SKU-${System.currentTimeMillis() % 1000}" else newSku,
                            category = newCategory,
                            unitPrice = parsedPrice * 1.3,
                            wholesalePrice = parsedPrice,
                            minWholesaleUnits = parsedMin,
                            packDescription = newPack
                        )
                        customProducts = customProducts + created
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DeepBlue, contentColor = BrandWhite)
                ) {
                    Text("Guardar Producto")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
