package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.BrandWhite
import com.example.ui.theme.DarkGray
import com.example.ui.theme.DeepBlue
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.LightGray

enum class LogoVariant(val title: String, val subtitle: String, val usage: String) {
    HORIZONTAL(
        title = "Logo Horizontal",
        subtitle = "Símbolo V+C + Nombre Vende Más",
        usage = "Cabeceras de sitios web, portadas de catálogos y presentaciones corporativas."
    ),
    COMPACT(
        title = "Logo Compacto",
        subtitle = "Encabezados y barras de navegación",
        usage = "Headers móviles, barras de herramientas y menús de catálogo reducidos."
    ),
    SQUARE_ICON(
        title = "Icono Cuadrado",
        subtitle = "App Icon, redes sociales y avatares",
        usage = "Icono para celular, WhatsApp Business, Instagram, LinkedIn y perfiles."
    ),
    FAVICON(
        title = "Favicon / Micro-Escala",
        subtitle = "Pestañas de navegador y firmas",
        usage = "Legibilidad garantizada en tamaños mínimos (16x16px a 32x32px)."
    ),
    MONOCHROME(
        title = "Versión Monocromática",
        subtitle = "Documentos, PDFs y Facturas",
        usage = "Impresiones en blanco y negro, sellos, tickets y fojas de pedido en PDF."
    )
}

data class ColorToken(
    val name: String,
    val hex: String,
    val rgb: String,
    val color: Color,
    val role: String,
    val textColor: Color
)

object BrandTokens {
    val officialColors = listOf(
        ColorToken(
            name = "Azul Profundo",
            hex = "#10253A",
            rgb = "16, 37, 58",
            color = DeepBlue,
            role = "Transmite confianza, solidez y profesionalismo corporativo. Color principal de la marca y fondos nobles.",
            textColor = BrandWhite
        ),
        ColorToken(
            name = "Celeste Eléctrico",
            hex = "#63D5F2",
            rgb = "99, 213, 242",
            color = ElectricCyan,
            role = "Destaca el símbolo V+C, botones primarios (CTAs) y detalles de interacción dinámica.",
            textColor = DeepBlue
        ),
        ColorToken(
            name = "Blanco",
            hex = "#FFFFFF",
            rgb = "255, 255, 255",
            color = BrandWhite,
            role = "Claridad absoluta, fondos de lectura, contraste tipográfico y nitidez en fichas de producto.",
            textColor = DarkGray
        ),
        ColorToken(
            name = "Gris Claro",
            hex = "#F2F5F8",
            rgb = "242, 245, 248",
            color = LightGray,
            role = "Estructura fondos limpios, contenedores secundarios, bordes sutiles y áreas de descanso visual.",
            textColor = DarkGray
        ),
        ColorToken(
            name = "Gris Oscuro",
            hex = "#263442",
            rgb = "38, 52, 66",
            color = DarkGray,
            role = "Cuerpo tipográfico legible, textos descriptivos, iconos secundarios y estados neutrales.",
            textColor = BrandWhite
        )
    )

    const val BRAND_NAME_PRIMARY = "Vende Más"
    const val BRAND_NAME_FULL = "Vende Más — Catálogos"
    const val LOGO_SYMBOL = "V+C"
    const val BRAND_SLOGAN = "Tus productos, mejor presentados"
    const val BRAND_VALUE_PROP = "Diseñamos y actualizamos catálogos comerciales para que tus productos se presenten de manera clara, profesional y listos para compartir."
    const val BRAND_POSITIONING = "Herramienta comercial estratégica para distribuidoras, mayoristas, proveedores y comercios."
    const val LEAD_DIRECTOR = "Gabriel Méndez"
    const val LEAD_ROLE = "Director de Estrategia Comercial y Diseño"
}

data class ServicePlan(
    val id: String,
    val name: String,
    val subtitle: String,
    val badge: String?,
    val priceNote: String,
    val features: List<String>,
    val target: String,
    val buttonText: String
)

val OfficialServicePlans = listOf(
    ServicePlan(
        id = "plan_express",
        name = "Catálogo Express WhatsApp",
        subtitle = "Lanzamiento ágil en formato digital interactivo",
        badge = "Más Rápido",
        priceNote = "Entrega en 48-72 hs hábiles",
        features = listOf(
            "Hasta 50 productos organizados por familias",
            "Botones directos de pedido a WhatsApp",
            "Diseño adaptado a pantallas de celular",
            "Enlace web interactivo + PDF liviano",
            "Portada personalizada con tu marca"
        ),
        target = "Comercios minoristas, emprendedores y catálogos estacionales.",
        buttonText = "Cotizar Plan Express"
    ),
    ServicePlan(
        id = "plan_mayorista",
        name = "Catálogo Mayorista Pro",
        subtitle = "La herramienta definitiva para distribuidoras",
        badge = "Más Vendido",
        priceNote = "Solución integral para ventas B2B",
        features = listOf(
            "Productos ilimitados con escalas de precios y SKUs",
            "Fichas técnicas con bultos mínimos y descuentos por volumen",
            "Buscador rápido y filtros por categoría o marca",
            "Optimizado para vendedores en calle y compras mayoristas",
            "Exportación a PDF de alta resolución con marca de agua",
            "Capacitación para tu equipo de ventas"
        ),
        target = "Distribuidoras, mayoristas, importadoras y fabricantes.",
        buttonText = "Solicitar Propuesta Pro"
    ),
    ServicePlan(
        id = "plan_mantenimiento",
        name = "Mantenimiento & Actualización",
        subtitle = "Tus precios y stock siempre al día",
        badge = "Suscripción",
        priceNote = "Mensual o por paquete de actualizaciones",
        features = listOf(
            "Actualización mensual o quincenal de listas de precios",
            "Altas y bajas inmediatas de productos agotados",
            "Incorporación de promociones y combos especiales",
            "Soporte prioritario por WhatsApp",
            "Historial de versiones y métricas de visitas"
        ),
        target = "Empresas con listas de precios dinámicas o inflación frecuente.",
        buttonText = "Consultar Mantenimiento"
    )
)
