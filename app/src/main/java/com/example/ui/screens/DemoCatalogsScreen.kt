package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.Catalog
import com.example.model.DemoCatalogData
import com.example.model.Product
import com.example.ui.components.VendeMasCompactLogo
import com.example.ui.components.VendeMasSymbolBadge
import com.example.ui.theme.BorderGray
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.DeepBlueSurface
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanSoft
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LightGray
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DemoCatalogsScreen(
    onAddToCart: (Product) -> Unit,
    onNavigateToQuote: () -> Unit,
    cartItemCount: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCatalogIndex by remember { mutableStateOf(0) }
    val currentCatalog = DemoCatalogData.catalogs[selectedCatalogIndex]

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }
    var isGridView by remember { mutableStateOf(true) }
    var selectedProductForDetail by remember { mutableStateOf<Product?>(null) }

    val categories = remember(currentCatalog) {
        listOf("Todas") + currentCatalog.products.map { it.category }.distinct()
    }

    val filteredProducts = remember(currentCatalog, searchQuery, selectedCategory) {
        currentCatalog.products.filter { p ->
            val matchesCategory = selectedCategory == "Todas" || p.category == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                    p.name.contains(searchQuery, ignoreCase = true) ||
                    p.sku.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))

            // Catalog Switcher Tabs
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BrandWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
            ) {
                TabRow(
                    selectedTabIndex = selectedCatalogIndex,
                    containerColor = BrandWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedCatalogIndex]),
                            color = ElectricCyan,
                            height = 3.dp
                        )
                    }
                ) {
                    DemoCatalogData.catalogs.forEachIndexed { index, catalog ->
                        val isSelected = selectedCatalogIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = {
                                selectedCatalogIndex = index
                                selectedCategory = "Todas"
                                searchQuery = ""
                            },
                            text = {
                                Text(
                                    text = if (index == 0) "Distribuidora Gourmet" else "Ferretería Industrial",
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) DeepBlue else DarkGray
                                )
                            }
                        )
                    }
                }
            }
        }

        // Hero Banner for Selected Catalog
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("catalog_banner_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DeepBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    ) {
                        Image(
                            painter = painterResource(id = currentCatalog.bannerRes),
                            contentDescription = currentCatalog.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Subtle gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DeepBlue.copy(alpha = 0.45f))
                        )

                        // Brand Badge overlay top right
                        Surface(
                            shape = RoundedCornerShape(bottomStart = 10.dp),
                            color = DeepBlue.copy(alpha = 0.92f),
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Formato Oficial",
                                    fontSize = 10.sp,
                                    color = BrandWhite,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "V+C",
                                    fontSize = 11.sp,
                                    color = ElectricCyan,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currentCatalog.company,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricCyan
                                )
                                Text(
                                    text = currentCatalog.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = BrandWhite
                                )
                            }

                            if (cartItemCount > 0) {
                                Button(
                                    onClick = onNavigateToQuote,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ElectricCyan,
                                        contentColor = DeepBlue
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("catalog_to_quote_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ver Cotización ($cartItemCount)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = currentCatalog.description,
                            fontSize = 12.sp,
                            color = LightGray.copy(alpha = 0.9f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Search & View Controls
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BrandWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Buscar producto o SKU...", fontSize = 13.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = DarkGray.copy(alpha = 0.6f)
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = "Limpiar")
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricCyan,
                                unfocusedBorderColor = BorderGray,
                                focusedContainerColor = LightGray,
                                unfocusedContainerColor = LightGray
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Grid vs List Toggle
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = LightGray,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
                        ) {
                            IconButton(onClick = { isGridView = !isGridView }) {
                                Icon(
                                    imageVector = if (isGridView) Icons.Default.ViewList else Icons.Default.GridView,
                                    contentDescription = "Cambiar vista",
                                    tint = DeepBlue
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Category Filter Chips
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categories.forEach { category ->
                            val isSelected = selectedCategory == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = category },
                                label = {
                                    Text(
                                        text = category,
                                        fontSize = 11.sp,
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
                }
            }
        }

        // Product Catalog Items List
        items(filteredProducts) { product ->
            ProductCatalogCard(
                product = product,
                currency = currentCatalog.currency,
                onSelectDetail = { selectedProductForDetail = product },
                onAdd = {
                    onAddToCart(product)
                    Toast.makeText(context, "${product.name} añadido a la cotización", Toast.LENGTH_SHORT).show()
                },
                onQuickWhatsApp = {
                    val message = "Hola! Quisiera consultar stock y cotización mayorista para:\n" +
                            "• Producto: ${product.name}\n" +
                            "• SKU: ${product.sku}\n" +
                            "• Presentación: ${product.packDescription}\n" +
                            "• Catálogo: ${currentCatalog.name}\n" +
                            "Presentado con Vende Más — Catálogos (V+C)"
                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=${currentCatalog.contactWhatsApp.replace("+", "")}&text=${Uri.encode(message)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            )
        }

        // Footer Brand Assurance
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BrandWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VendeMasSymbolBadge(
                        size = 38.dp,
                        containerColor = DeepBlue,
                        shapeCornerRadius = 8.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Catálogo Diseñado por Vende Más",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue
                        )
                        Text(
                            text = "“${BrandTokens.BRAND_SLOGAN}” • Listas mayoristas con actualización continua",
                            fontSize = 11.sp,
                            color = DarkGray.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Modal Bottom Sheet: Ficha Técnica de Producto
    selectedProductForDetail?.let { product ->
        ModalBottomSheet(
            onDismissRequest = { selectedProductForDetail = null },
            sheetState = rememberModalBottomSheetState(),
            containerColor = BrandWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = ElectricCyanSoft
                        ) {
                            Text(
                                text = "SKU: ${product.sku} • ${product.category}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = product.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = DeepBlue
                        )
                    }
                    IconButton(onClick = { selectedProductForDetail = null }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    color = DarkGray,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Price & Tier Breakdown
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = LightGray,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Precio Unitario Sugerido:", fontSize = 12.sp, color = DarkGray)
                            Text(
                                text = "${currentCatalog.currency} ${String.format("%.2f", product.unitPrice)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkGray
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Precio Mayorista (Mín. ${product.minWholesaleUnits}u):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue
                            )
                            Text(
                                text = "${currentCatalog.currency} ${String.format("%.2f", product.wholesalePrice)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = DeepBlue
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Bulto Cerrado / Distribuidor:",
                                fontSize = 12.sp,
                                color = SuccessGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "-${product.bulkDiscountPercent}% adicional",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Presentación de embalaje: ${product.packDescription}",
                            fontSize = 11.sp,
                            color = DarkGray.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            val shareText = "Ficha Comercial Vende Más:\n" +
                                    "Producto: ${product.name}\n" +
                                    "SKU: ${product.sku}\n" +
                                    "Precio Mayorista: ${currentCatalog.currency} ${product.wholesalePrice}\n" +
                                    "Mínimo: ${product.minWholesaleUnits} unidades\n" +
                                    "Presentación: ${product.packDescription}\n" +
                                    "Generado con Vende Más — Catálogos (V+C)"
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Compartir Ficha"))
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Compartir", fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            onAddToCart(product)
                            selectedProductForDetail = null
                            Toast.makeText(context, "${product.name} sumado a la cotización", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepBlue,
                            contentColor = BrandWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1.3f)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Sumar al Pedido", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProductCatalogCard(
    product: Product,
    currency: String,
    onSelectDetail: () -> Unit,
    onAdd: () -> Unit,
    onQuickWhatsApp: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("product_card_${product.id}")
            .clickable { onSelectDetail() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BrandWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header with SKU and Stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = LightGray
                ) {
                    Text(
                        text = product.sku,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkGray,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SuccessGreen.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "En Stock (${product.stockUnits}u)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SuccessGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.packDescription,
                fontSize = 11.sp,
                color = DarkGray.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing Area
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LightGray.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Precio Minorista: $currency ${String.format("%.2f", product.unitPrice)}",
                            fontSize = 10.sp,
                            color = DarkGray.copy(alpha = 0.6f),
                            textDecoration = TextDecoration.LineThrough
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Mayorista: ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepBlue
                            )
                            Text(
                                text = "$currency ${String.format("%.2f", product.wholesalePrice)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = DeepBlue
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ElectricCyanSoft,
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = "Mín. ${product.minWholesaleUnits}u",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onQuickWhatsApp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DeepBlue)
                }

                Button(
                    onClick = onAdd,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DeepBlue,
                        contentColor = BrandWhite
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Cotizar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
