package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BrandTokens
import com.example.model.OfficialServicePlans
import com.example.model.ServicePlan
import com.example.ui.components.VendeMasHorizontalLogo
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
fun ServicesContactScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var clientCompany by remember { mutableStateOf("") }
    var clientProductsCount by remember { mutableStateOf("50 a 200 productos") }
    var clientSector by remember { mutableStateOf("Distribuidora / Mayorista") }
    var clientNotes by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray)
            .verticalScroll(scrollState)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Services Header
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                VendeMasSymbolBadge(
                    size = 54.dp,
                    containerColor = DeepBlue,
                    vColor = BrandWhite,
                    plusColor = ElectricCyan,
                    cColor = BrandWhite,
                    shapeCornerRadius = 12.dp,
                    showBorder = true,
                    borderColor = ElectricCyan
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Planes & Soluciones Comerciales",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = BrandWhite,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "“${BrandTokens.BRAND_SLOGAN}”",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ElectricCyan
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = BrandTokens.BRAND_VALUE_PROP,
                    fontSize = 13.sp,
                    color = LightGray.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        }

        // Value Proposition Pillars (Why Vende Más?)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Una Herramienta Comercial, No Solo Diseño",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )
                Spacer(modifier = Modifier.height(12.dp))

                ValueBenefitItem(
                    icon = Icons.Default.TrendingUp,
                    title = "Más Ventas y Pedidos Claros",
                    desc = "Tus clientes ven precios, mínimos de compra y bultos cerrados de un solo vistazo, cerrando órdenes sin idas y vueltas."
                )

                Spacer(modifier = Modifier.height(10.dp))

                ValueBenefitItem(
                    icon = Icons.Default.Speed,
                    title = "Acelera el Trabajo de Vendedores",
                    desc = "Tu equipo comercial en calle o mostrador comparte fichas de productos y catálogos en segundos por WhatsApp o email."
                )

                Spacer(modifier = Modifier.height(10.dp))

                ValueBenefitItem(
                    icon = Icons.Default.Shield,
                    title = "Mantenimiento Continuo de Precios",
                    desc = "Actualizamos tus listas quincenal o mensualmente para que nunca vendas con precios desactualizados."
                )
            }
        }

        // Service Plans Cards
        Text(
            text = "Elige la Solución para tu Empresa",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        OfficialServicePlans.forEach { plan ->
            ServicePlanCard(
                plan = plan,
                onSelect = {
                    val message = "Hola Vende Más! Me interesa consultar por el plan:\n" +
                            "*${plan.name}*\n" +
                            "“Tus productos, mejor presentados”. Quisiera recibir presupuesto para mi empresa."
                    val uri = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode(message)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            )
        }

        // Quick Quote Inquiry Form
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("inquiry_form_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.NearMe, contentDescription = null, tint = DeepBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Solicitar Asesoría y Presupuesto",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Cuéntanos sobre tu negocio y te armamos una propuesta a medida sin compromiso:",
                    fontSize = 12.sp,
                    color = DarkGray.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = clientCompany,
                    onValueChange = { clientCompany = it },
                    label = { Text("Nombre de tu Empresa o Distribuidora") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = BorderGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = clientSector,
                    onValueChange = { clientSector = it },
                    label = { Text("Rubro (ej. Alimentos, Ferretería, Cosmética)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = BorderGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = clientProductsCount,
                    onValueChange = { clientProductsCount = it },
                    label = { Text("Cantidad Estimada de Productos") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = BorderGray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = clientNotes,
                    onValueChange = { clientNotes = it },
                    label = { Text("Comentarios o necesidades específicas") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricCyan,
                        unfocusedBorderColor = BorderGray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val companyText = if (clientCompany.isNotBlank()) clientCompany else "mi empresa"
                        val msg = "Hola equipo de *Vende Más — Catálogos*!\n" +
                                "Me contacto desde *$companyText* ($clientSector).\n" +
                                "Tenemos aprox. $clientProductsCount y quisiéramos armar un catálogo comercial profesional.\n" +
                                if (clientNotes.isNotBlank()) "Detalle: $clientNotes\n" else "" +
                                "“Tus productos, mejor presentados”."
                        val uri = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode(msg)}")
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
                        .testTag("inquiry_submit_button")
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Contactar por WhatsApp Oficial", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Official Brand Footer & Transition Notice
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = BrandWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VendeMasHorizontalLogo(
                    isDarkTheme = false,
                    symbolSize = 42.dp,
                    titleSize = 20.sp,
                    showSubtitle = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“${BrandTokens.BRAND_SLOGAN}”",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepBlue
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = BorderGray)
                Spacer(modifier = Modifier.height(10.dp))

                // Discreet mention of service director as instructed in prompt:
                Text(
                    text = "Estrategia Comercial y Dirección de Proyecto: ${BrandTokens.LEAD_DIRECTOR}\n" +
                            "Marca comercial titular: ${BrandTokens.BRAND_NAME_FULL} (V+C)",
                    fontSize = 11.sp,
                    color = DarkGray.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Todos los derechos reservados © 2026 • Argentina & Latinoamérica",
                    fontSize = 10.sp,
                    color = DarkGray.copy(alpha = 0.55f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ValueBenefitItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = ElectricCyanSoft,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DeepBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DeepBlue)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, fontSize = 12.sp, color = DarkGray.copy(alpha = 0.85f), lineHeight = 16.sp)
        }
    }
}

@Composable
private fun ServicePlanCard(
    plan: ServicePlan,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = BrandWhite),
        border = androidx.compose.foundation.BorderStroke(
            width = if (plan.badge == "Más Vendido") 2.dp else 1.dp,
            color = if (plan.badge == "Más Vendido") ElectricCyan else BorderGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (plan.badge == "Más Vendido") 3.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = plan.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = DeepBlue
                )
                plan.badge?.let { badge ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (badge == "Más Vendido") ElectricCyan else LightGray
                    ) {
                        Text(
                            text = badge,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = plan.subtitle,
                fontSize = 12.sp,
                color = DarkGray.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = LightGray
            ) {
                Text(
                    text = plan.priceNote,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepBlue,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            plan.features.forEach { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        fontSize = 12.sp,
                        color = DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onSelect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (plan.badge == "Más Vendido") ElectricCyan else DeepBlue,
                    contentColor = if (plan.badge == "Más Vendido") DeepBlue else BrandWhite
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = plan.buttonText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
