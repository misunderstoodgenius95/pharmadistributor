package pharma.Service.Report;

import pharma.config.InputValidation;
import pharma.dao.PurchaseOrderDao;

import java.util.*;

public class BuildFormula {
    private UserFormula userFormula;
    private PurchaseOrderDao purchaseOrderDao;
    private List<FormuleBuildModel> formuleBuildModels;
    private static LinkedList<Double> linkedList=new LinkedList<>();
    public BuildFormula(UserFormula userFormula,PurchaseOrderDao purchaseOrderDao) {
        this.userFormula = userFormula;
        formuleBuildModels = new ArrayList<>();
        this.purchaseOrderDao=purchaseOrderDao;
    }

    /*
    Estrae tutti gli operatori nella formula utilizzando la parentesi aperta come chiave iniziale
    */
    public static List<String> extractOperation(String formula) {
        List<String> risultati = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            if (c == '(') {
                // Trovata una parentesi aperta, salva il nome accumulato
                if (!buffer.isEmpty()) {
                    risultati.add(buffer.toString());
                    buffer = new StringBuilder();
                }
            } else if (Character.isLetterOrDigit(c) || c == '_') {
                // Accumula solo caratteri validi per nomi di operazioni
                buffer.append(c);
            } else {
                // Carattere non valido (virgola, spazio, operatore), resetta
                buffer = new StringBuilder();
            }
        }

        return risultati;


    }

    public static List<String> extractIntoParentesis(String formula) {
        List<String> risultati = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int parentesiAperte = 0;

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (c == '(') {
                parentesiAperte++;
                if (!buffer.isEmpty()) {
                    risultati.add(buffer.toString());
                    buffer = new StringBuilder();
                }
            } else if (c == ')') {
                parentesiAperte--;
                if (parentesiAperte == 0 && !buffer.isEmpty()) {
                    risultati.add(buffer.toString());
                    buffer = new StringBuilder();
                }
            } else if (c == ',' && parentesiAperte > 0) {
                if (!buffer.isEmpty()) {
                    risultati.add(buffer.toString());
                    buffer = new StringBuilder();
                }

            } else if (parentesiAperte > 0 && Character.isLetterOrDigit(c) || c == '_') {
                buffer.append(c);
            }
        }

        return risultati;

    }


    public static List<String> extractIntoParentesis(String formula, String functionName) {
        List<String> risultati = new ArrayList<>();

        // Trova l'indice dove inizia la funzione
        int startIndex = formula.indexOf(functionName + "(");
        if (startIndex == -1) {
            return risultati; // Funzione non trovata
        }

        // Inizia dopo "functionName("
        int i = startIndex + functionName.length() + 1;

        StringBuilder buffer = new StringBuilder();
        int parentesiAperte = 1; // Già dentro la prima parentesi

        while (i < formula.length() && parentesiAperte > 0) {
            char c = formula.charAt(i);

            if (c == '(') {
                parentesiAperte++;
                buffer.append(c);
            } else if (c == ')') {
                parentesiAperte--;
                if (parentesiAperte > 0) {
                    buffer.append(c);
                } else if (!buffer.isEmpty()) {
                    // Chiusa l'ultima parentesi della funzione
                    risultati.add(buffer.toString().trim());
                }
            } else if (c == ',' && parentesiAperte == 1) {
                // Virgola al primo livello: separa gli argomenti
                if (!buffer.isEmpty()) {
                    risultati.add(buffer.toString().trim());
                    buffer = new StringBuilder();
                }
            } else {
                buffer.append(c);
            }

            i++;
        }

        return risultati;
    }

    public static boolean checkOperazioni(List<String> operands) {

        for (String operand : operands) {
            if (!FormuleEngine.operazioni.contains(operand)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkAttribute(List<String> attributes) {
        int index = 0;
        for (String attribute : attributes) {
            if (FormuleEngine.dati_map.containsKey(attribute)) {
                index++;
            }

        }
        return index > 0;
    }

    public static boolean checkAttributeandOperator(List<String> attributes) {
        for (String attribute : attributes) {

            boolean isValid = FormuleEngine.dati_map.containsKey(attribute)
                    || FormuleEngine.operazioni.contains(attribute)
                    || InputValidation.validate_digit(attribute);

            if (!isValid) {
                return false;
            }
        }
        return true;
    }


    public double buildFormula() {

        if (!checkParentesi(userFormula.getFormula())) {
            return -1.1;
        }
        // Ottengo la stringa degli operatori
        List<String> ops = extractOperation(userFormula.getFormula());
        if (ops.isEmpty()) {
            return -1.1;
        }
        // Controllare se gli operatori sono corretti
        if (!checkOperazioni(ops)) {
            return -1.1;
        }

        List<String> attributesAndOperators = extractIntoParentesis(userFormula.getFormula());
        if (!checkAttribute(attributesAndOperators)) {
            return -1.1;
        }
        // Step 5: Verifica che tutti gli attributi e operatori siano validi
        if (!checkAttributeandOperator(attributesAndOperators)) {
            System.err.println("Errore: Uno o più attributi non sono validi");
            return -1.1;
        }

         return buildQuery(ops, userFormula.getFormula());

    }

    public Double buildQuery(List<String> operators, String formula) {
        operators.reversed().forEach(op -> {
            List<String> extractes = extractIntoParentesis(formula, op);
            boolean containAnOperator = extractes.stream()
                    .anyMatch(str -> FormuleEngine.operazioni.stream()
                            .anyMatch(str::contains));
            List<Double> numericAttribute=new ArrayList<>();
            // se contengono un operatore
            if (containAnOperator) {
                if (!linkedList.isEmpty()) {
                    double resultedValue = linkedList.getLast();
                    numericAttribute.add(resultedValue);

                }
            }
               List<String> onlyAttributeandNumeric = removeOperator(extractes);
                    //conversione di attributi
                   onlyAttributeandNumeric.forEach(v->{
                     if(FormuleEngine.dati_map.containsKey(v)){
                         DatiModel datiModel=FormuleEngine.dati_map.get(v);
                         numericAttribute.add(getValueFromAttribute(datiModel));
                     }else if(isNumeric(v)){
                         double numeric=Double.parseDouble(v);
                         numericAttribute.add(numeric);
                     }
                   });
                   double result=executeOperationWithAttribute(op,numericAttribute);
                System.out.println(result);
                   linkedList.add(result);




        });
        System.out.println(linkedList);
        // operation
        return linkedList.getLast();
    }


    public  double getValueFromAttribute(DatiModel datiModel){
        System.out.println("execute");
        if(datiModel.getNome_tabella().equals("purchase_order")){
             return  purchaseOrderDao.findBySumAggregate(datiModel.getNome_attributo());

        }
         return -1.1;

    }
    public  static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }
    public static List<String> removeOperator(List<String> attributes){
         return attributes.stream().filter(value->!value.contains("(")).toList();

    }

    public static double executeOperationWithAttribute(String operation, List<Double> doubleList) {
        System.out.println(operation);
        switch (operation) {
            case "somma" -> {

                return doubleList.stream().mapToDouble(Double::doubleValue).sum();

            }
            case "moltiplicazione"->{
                return doubleList.stream().reduce(1.0, (a, b) -> a * b);
            }


            default -> {
                return -1.1;
            }
        }






    }






























      /*  List<String> list=extractOperation();
        list.forEach(op->{
                BuildFormulaModel buildFormulaModel=new BuildFormulaModel();
                buildFormulaModel.setOperation(op);
            String attributes=estraiContenutoOperazione(op);
            if(attributes.contains(",")){
                String[]array_ops=attributes.split("'");
                Set<String> setAttribute=new HashSet<>();
                Arrays.stream(array_ops).forEach(attribute->{
                    if(FormuleEngine.dati_map.containsKey(attribute)){

                    }
                });
            }else{


            }

        });*/


    public  static boolean checkParentesi(String formula) {



        // Inizia dalla parentesi aperta dopo il nome dell'operazione
        int parentesiAperte =0;
        int num_parentesi=0;
        for (int i =0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                parentesiAperte++;
                num_parentesi++;
            } else if (formula.charAt(i) == ')') {
                parentesiAperte--;
                num_parentesi++;
                if (parentesiAperte < 0) {
                    return  false;
                }
            }
        }
        return parentesiAperte == 0 && num_parentesi>0;
    }





    public String estraiContenutoOperazione( String operazione) {
        String formula=userFormula.getFormula();
        int index = userFormula.getFormula().indexOf(operazione + "(");
        if (index == -1) {
            return null; // Operazione non trovata
        }

        // Inizia dalla parentesi aperta dopo il nome dell'operazione
        int start = index+operazione.length()+1;
        int parentesiAperte = 1;
        int endIndex = start;

        for (int i = start; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                parentesiAperte++;
            } else if (formula.charAt(i) == ')') {
                parentesiAperte--;
                if (parentesiAperte == 0) {
                    endIndex = i;
                    break;
                }
            }
        }

        return formula.substring(start, endIndex);
    }








/*    public List<FormuleBuildModel> estraiValori() {
        return operazionis.stream().map(value -> {
            FormuleBuildModel formuleBuildModel=new FormuleBuildModel();
            if(FormuleEngine.dati_map.containsKey(value.getDati())) {
                formuleBuildModel.setFormuleModel(FormuleEngine.dati_map.get(value.getDati()));
            }
            if(FormuleEngine.operazioni_map.containsKey(value.getOperazione())){

               formuleBuildModel.setAggregate(FormuleEngine.operazioni_map.get(value.getOperazione()));
            }
            return formuleBuildModel;

        }).toList();


    }*/












}






