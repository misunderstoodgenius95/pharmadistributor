package pharma.Service.Report;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FormuleEngine {
    public static final Set< String> operazioni = Set.of(
            "media",
            "somma",
            "max",
            "min",
            "deviazione",
            "varianza",
            "moltiplicazione",
            "divisione",
            "sottrazione"
            );
    public static final Map<String, DatiModel> dati_map =Map.of(
            "iva_ordini", new DatiModel("purchase_order","iva"),
            "totale_ordini",new DatiModel("purchase_order","totale"),
            "subtotale_ordini",new DatiModel("purchase_order","subtotale"),
            "prezzo_prodotti",new DatiModel("purchase_order_detail","price"),
            "quantità_acquistata",new DatiModel("purchase_order_detail","quantity"));
}
