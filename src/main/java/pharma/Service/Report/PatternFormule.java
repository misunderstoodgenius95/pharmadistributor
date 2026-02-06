package pharma.Service.Report;

import java.util.List;

public class PatternFormule {



    public static final String single_pattern_sum="^somma\\([\\w]*\\)";
    public static final String double_pattern_sum="^somma\\([\\w]*,[\\w]*\\)";
    public  static final String moltiply="^moltiplicazione\\([\\w]+\\,[\\d]+\\)$";
    public static final String divisone="^divisione\\([\\w]+\\,[\\d]+\\)$";
    public static final String max_pattern="^max\\([\\w]+\\)$";
    public static final String min_pattern="^min\\([\\w]+\\)$";
    public static final String deviazione_standard="^deviazione_standard\\([\\w]+\\)$";
    public static final String media="^media\\([\\w]+\\)$;";
    public static final String varianza="^varianza\\([\\w]+\\)$";
    public static final String percentili="^percentili\\([\\w]+\\,[\\d]+\\)$";
    public  static  final  List<String> patterns=List.of(single_pattern_sum,double_pattern_sum,moltiply,divisone,max_pattern,min_pattern,deviazione_standard,media,varianza,percentili);

}
