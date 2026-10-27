package com.example.model

import com.example.R

data class Product(
    val id: String,
    val name: String,
    val sku: String,
    val category: String,
    val unitPrice: Double,
    val wholesalePrice: Double,
    val minWholesaleUnits: Int = 6,
    val bulkDiscountPercent: Int = 12,
    val packDescription: String,
    val inStock: Boolean = true,
    val stockUnits: Int = 120,
    val imageRes: Int = R.drawable.brand_logo_official,
    val barcode: String = "7791234567890",
    val description: String = ""
)

data class Catalog(
    val id: String,
    val name: String,
    val company: String,
    val sector: String,
    val description: String,
    val bannerRes: Int,
    val contactWhatsApp: String,
    val currency: String = "$",
    val products: List<Product>
)

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val effectiveUnitPrice: Double
        get() = if (quantity >= product.minWholesaleUnits) {
            if (quantity >= product.minWholesaleUnits * 3) {
                product.wholesalePrice * (1.0 - (product.bulkDiscountPercent / 100.0))
            } else {
                product.wholesalePrice
            }
        } else {
            product.unitPrice
        }

    val totalPrice: Double
        get() = effectiveUnitPrice * quantity

    val isWholesaleApplied: Boolean
        get() = quantity >= product.minWholesaleUnits
}

object DemoCatalogData {
    val catalogs: List<Catalog> = listOf(
        Catalog(
            id = "gourmet",
            name = "Catálogo Mayorista Gourmet 2026",
            company = "Distribuidora Del Plata Gourmet",
            sector = "Alimentos & Delicatessen",
            description = "Línea selecta de aceites de oliva virgen extra, aceto balsámico reserva y conservas finas para restaurantes y tiendas gourmet.",
            bannerRes = R.drawable.banner_gourmet,
            contactWhatsApp = "+5491199887766",
            currency = "$",
            products = listOf(
                Product(
                    id = "g1",
                    name = "Aceite de Oliva Extra Virgen 500ml",
                    sku = "OLV-EV500",
                    category = "Aceites y Vinagres",
                    unitPrice = 14.50,
                    wholesalePrice = 11.20,
                    minWholesaleUnits = 6,
                    bulkDiscountPercent = 10,
                    packDescription = "Caja x 12 unidades (Vidrio Oscuro)",
                    description = "Primera prensada en frío, acidez menor a 0.2%. Varietal Arauco y Arbequina con aromas frutados intensos.",
                    barcode = "7798123001"
                ),
                Product(
                    id = "g2",
                    name = "Aceto Balsámico Tradicional Módena 250ml",
                    sku = "ACT-MOD250",
                    category = "Aceites y Vinagres",
                    unitPrice = 18.00,
                    wholesalePrice = 13.90,
                    minWholesaleUnits = 6,
                    bulkDiscountPercent = 12,
                    packDescription = "Pack exhibidor x 6 botellas",
                    description = "Añejado en barricas de roble durante 5 años. Textura densa, notas agridulces perfectas para quesos y ensaladas.",
                    barcode = "7798123002"
                ),
                Product(
                    id = "g3",
                    name = "Pasta de Aceitunas Negras & Nueces 200g",
                    sku = "TAP-NEG200",
                    category = "Conservas & Untables",
                    unitPrice = 9.80,
                    wholesalePrice = 7.40,
                    minWholesaleUnits = 12,
                    bulkDiscountPercent = 15,
                    packDescription = "Bandeja termoformada x 12 frascos",
                    description = "Elaborado con aceitunas negras maduras de San Juan y nueces partidas con hierbas mediterráneas.",
                    barcode = "7798123003"
                ),
                Product(
                    id = "g4",
                    name = "Arroz Carnaroli Risotto con Trufa Negra",
                    sku = "RIS-TRUF500",
                    category = "Granos & Pastas",
                    unitPrice = 16.20,
                    wholesalePrice = 12.50,
                    minWholesaleUnits = 10,
                    bulkDiscountPercent = 12,
                    packDescription = "Caja máster x 10 estuches",
                    description = "Mezcla de arroz italiano Carnaroli super fino aromatizado con láminas deshidratadas de trufa negra.",
                    barcode = "7798123004"
                ),
                Product(
                    id = "g5",
                    name = "Queso Parmigiano Reggiano Cuña 300g",
                    sku = "QSO-PAR300",
                    category = "Lácteos & Quesos",
                    unitPrice = 22.00,
                    wholesalePrice = 17.50,
                    minWholesaleUnits = 8,
                    bulkDiscountPercent = 8,
                    packDescription = "Al vacío en caja x 8 unidades",
                    description = "Maduración mínima 24 meses. Cristalización pronunciada, sabor profundo con notas a frutos secos.",
                    barcode = "7798123005"
                )
            )
        ),
        Catalog(
            id = "industrial",
            name = "Suministros Ferreteros & Máquinas Pro",
            company = "Ferretería & Herramientas Delta S.A.",
            sector = "Ferretería & Construcción",
            description = "Equipamiento eléctrico profesional, consumibles y herramientas manuales para talleres, contratistas e industrias.",
            bannerRes = R.drawable.banner_industrial,
            contactWhatsApp = "+5491144332211",
            currency = "USD",
            products = listOf(
                Product(
                    id = "i1",
                    name = "Taladro Percutor Inalámbrico Brushless 20V",
                    sku = "TLD-BR20V",
                    category = "Herramientas Eléctricas",
                    unitPrice = 189.00,
                    wholesalePrice = 145.00,
                    minWholesaleUnits = 4,
                    bulkDiscountPercent = 10,
                    packDescription = "Maletín rígido + 2 Baterías 4Ah + Cargador",
                    description = "Motor sin escobillas de alto rendimiento con torque máximo de 65Nm y portabrocas metálico de 13mm.",
                    barcode = "7799001001"
                ),
                Product(
                    id = "i2",
                    name = "Juego de Llaves Combinadas Cromo Vanadio (12 pcs)",
                    sku = "LLV-CV12P",
                    category = "Herramientas Manuales",
                    unitPrice = 64.00,
                    wholesalePrice = 48.00,
                    minWholesaleUnits = 6,
                    bulkDiscountPercent = 12,
                    packDescription = "Caja expositora x 6 sets",
                    description = "Fabricadas en acero Cr-V forjado, acabado mate satinado antideslizante con medidas de 6mm a 22mm.",
                    barcode = "7799001002"
                ),
                Product(
                    id = "i3",
                    name = "Amoladora Angular 115mm 900W Industrial",
                    sku = "AML-900W115",
                    category = "Herramientas Eléctricas",
                    unitPrice = 85.00,
                    wholesalePrice = 66.00,
                    minWholesaleUnits = 5,
                    bulkDiscountPercent = 14,
                    packDescription = "Caja máster x 5 unidades",
                    description = "Sistema de ventilación antipolvo, empuñadura antivibración y arranque suave con protección de rearranque.",
                    barcode = "7799001003"
                ),
                Product(
                    id = "i4",
                    name = "Caja de Tornillos Autoperforantes 1000u",
                    sku = "TRN-AUT1000",
                    category = "Fijaciones & Consumibles",
                    unitPrice = 28.50,
                    wholesalePrice = 21.00,
                    minWholesaleUnits = 10,
                    bulkDiscountPercent = 15,
                    packDescription = "Bulto x 10 cajas selladas",
                    description = "Punta broca con arandela vulcanizada EPDM para chapas y perfiles metálicos estructurales.",
                    barcode = "7799001004"
                ),
                Product(
                    id = "i5",
                    name = "Gafas de Seguridad Policarbonato UV400",
                    sku = "SEG-GAFUV",
                    category = "Seguridad Industrial",
                    unitPrice = 8.50,
                    wholesalePrice = 5.20,
                    minWholesaleUnits = 20,
                    bulkDiscountPercent = 20,
                    packDescription = "Display mostrador x 20 unidades",
                    description = "Lentes envolventes antiempañamiento con patillas regulables y certificación ANSI Z87.1.",
                    barcode = "7799001005"
                )
            )
        )
    )
}
