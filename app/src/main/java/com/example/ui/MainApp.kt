package com.example.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.CartItem
import com.example.model.DemoCatalogData
import com.example.model.Product
import com.example.ui.components.VendeMasCompactLogo
import com.example.ui.components.VendeMasSymbolBadge
import com.example.ui.screens.BrandIdentityScreen
import com.example.ui.screens.CatalogBuilderScreen
import com.example.ui.screens.DemoCatalogsScreen
import com.example.ui.screens.QuoteCalculatorScreen
import com.example.ui.screens.ServicesContactScreen
import com.example.ui.theme.BorderGray
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanSoft
import com.example.ui.theme.LightGray

enum class NavSection(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    IDENTITY("Identidad", Icons.Filled.Palette, Icons.Outlined.Palette),
    CATALOGS("Catálogos", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    BUILDER("Creador", Icons.Filled.EditNote, Icons.Outlined.EditNote),
    QUOTE("Cotizador", Icons.Filled.ReceiptLong, Icons.Outlined.ReceiptLong),
    SERVICES("Planes", Icons.Filled.BusinessCenter, Icons.Outlined.BusinessCenter)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val context = LocalContext.current
    var selectedSectionIndex by remember { mutableIntStateOf(0) }
    val cartItems = remember { mutableStateListOf<CartItem>() }

    // Intercept hardware Back button when not on Home/Identity tab
    if (selectedSectionIndex != 0) {
        BackHandler {
            selectedSectionIndex = 0
        }
    }

    val totalItemsCount = cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VendeMasCompactLogo(isDarkTheme = true)
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ElectricCyan.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Catálogos",
                                color = ElectricCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val uri = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode("Hola Vende Más! Quisiera consultar por catálogos comerciales. “Tus productos, mejor presentados”.")}")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        },
                        modifier = Modifier.testTag("top_bar_whatsapp_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "WhatsApp Comercial",
                            tint = ElectricCyan
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue,
                    titleContentColor = BrandWhite,
                    actionIconContentColor = ElectricCyan
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = BrandWhite,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("main_navigation_bar")
            ) {
                NavSection.values().forEachIndexed { index, section ->
                    val isSelected = selectedSectionIndex == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedSectionIndex = index },
                        icon = {
                            if (section == NavSection.QUOTE && totalItemsCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = ElectricCyan,
                                            contentColor = DeepBlue
                                        ) {
                                            Text(text = "$totalItemsCount", fontWeight = FontWeight.Bold)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) section.selectedIcon else section.unselectedIcon,
                                        contentDescription = section.label
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) section.selectedIcon else section.unselectedIcon,
                                    contentDescription = section.label
                                )
                            }
                        },
                        label = {
                            Text(
                                text = section.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepBlue,
                            selectedTextColor = DeepBlue,
                            indicatorColor = ElectricCyan,
                            unselectedIconColor = DarkGray.copy(alpha = 0.6f),
                            unselectedTextColor = DarkGray.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightGray)
        ) {
            when (selectedSectionIndex) {
                0 -> BrandIdentityScreen()
                1 -> DemoCatalogsScreen(
                    onAddToCart = { product ->
                        val existingIndex = cartItems.indexOfFirst { it.product.id == product.id }
                        if (existingIndex >= 0) {
                            val existing = cartItems[existingIndex]
                            cartItems[existingIndex] = existing.copy(quantity = existing.quantity + 1)
                        } else {
                            cartItems.add(CartItem(product = product, quantity = product.minWholesaleUnits))
                        }
                    },
                    onNavigateToQuote = { selectedSectionIndex = 3 },
                    cartItemCount = totalItemsCount
                )
                2 -> CatalogBuilderScreen()
                3 -> QuoteCalculatorScreen(
                    cartItems = cartItems,
                    onUpdateQuantity = { product, newQty ->
                        val existingIndex = cartItems.indexOfFirst { it.product.id == product.id }
                        if (newQty <= 0) {
                            if (existingIndex >= 0) cartItems.removeAt(existingIndex)
                        } else {
                            if (existingIndex >= 0) {
                                cartItems[existingIndex] = cartItems[existingIndex].copy(quantity = newQty)
                            } else {
                                cartItems.add(CartItem(product = product, quantity = newQty))
                            }
                        }
                    },
                    onClearCart = { cartItems.clear() },
                    onLoadSampleOrder = {
                        cartItems.clear()
                        val gourmet = DemoCatalogData.catalogs[0].products
                        if (gourmet.size >= 3) {
                            cartItems.add(CartItem(product = gourmet[0], quantity = 12))
                            cartItems.add(CartItem(product = gourmet[1], quantity = 6))
                            cartItems.add(CartItem(product = gourmet[2], quantity = 24))
                        }
                    },
                    onNavigateToCatalogs = { selectedSectionIndex = 1 }
                )
                4 -> ServicesContactScreen()
            }
        }
    }
}
