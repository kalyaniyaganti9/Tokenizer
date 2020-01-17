

import java.io.File;
import java.util.*;

/**
 * @author kalyani created on 2019-09-05
 * This assignment uses 'space' as delimiter in the input file
 */
public class LexicalAnalyzer {


    // Declaring Unmodifiable Token Lists for Java Program

    public static final List<String> seperatorTokenList =
            Collections.unmodifiableList(Arrays.asList("(", ")","{","}","[","]",",",";"));
    public static final List<String> operatorTokenList =
            Collections.unmodifiableList(Arrays.asList("-","+","*","/","%", "<",">","<=","!=","&&","||","!","=","++","--","?:",">>","<<","^"));
    public static final List<String> keywordTokenList =
            Collections.unmodifiableList(Arrays.asList("void","public","static","main","import","int","if","else","boolean","final","case","throws",
                                                        "private","for","class","new","finally","catch","package","return"));
    public static final List<String> specialCharacterTokenList= Collections.unmodifiableList(Arrays.asList("`","~","@","#","$","_"));

    // Declaring 3 HashMaps to store the count data after parsing input file
    // HashMap internally uses HashTable and the retrieval is O(1) most times

    public static HashMap<String, Integer> tokenCountMap = new LinkedHashMap<>();
    public static HashMap<String, String> tokenClassMap = new LinkedHashMap<>();
    public static HashMap<String, Integer> tokenClassCount = new LinkedHashMap<>();

    public static void main(String[] args){

        if(args.length != 1){
            throw new IllegalArgumentException("Improper number of arguments! Need an argument for input file with extension");
        }

        getParsedTokenData(args[0]);

        System.out.println("List of Tokens appeared in the input file with the count of tokens");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-10s | %10s\n","TOKEN","TOKEN COUNT");
        tokenCountMap.forEach(
                (key, value) -> System.out.printf("%-10s | %10s\n",key,value)
        );


        System.out.println("List of Tokens appeared in the input file along with the Token Category");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-10s | %10s\n","TOKEN","TOKEN CATEGORY");
        tokenClassMap.forEach(
                (key, value) -> System.out.printf("%-10s | %10s\n",key,value)
        );


        System.out.println("List of Token Categories appeared in the input file with the count of tokens categories");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-18s | %10s\n","TOKEN CATEGORY","TOKEN CATEGORY COUNT");
        tokenClassCount.forEach(
                (key, value) -> System.out.printf("%-18s | %10s\n",key,value)
        );

    }

    public static void getParsedTokenData(String filePath){

        // Scanner use default delimiter of whitespace
        Scanner scanner = null;

        try{

            File inputFile = new File(filePath);

            if(!inputFile.exists() || inputFile.isDirectory()){
                throw new IllegalAccessException("Cannot Access file in the location : "+filePath);
            }

            System.out.println("Reading input file : "+filePath);
            scanner = new Scanner(inputFile);
            while (scanner.hasNext()){

                String token = scanner.next();
                String tokenClass = getTokenClass(token);
                if(tokenClass != null){

                    if(tokenCountMap.containsKey(token)){
                        tokenCountMap.put(token, tokenCountMap.get(token) + 1);
                    }else{
                        tokenCountMap.put(token, 1);
                    }

                    tokenClassMap.put(token, tokenClass);
                    if(tokenClassCount.containsKey(tokenClass)){
                        tokenClassCount.put(tokenClass, tokenClassCount.get(tokenClass) + 1);
                    }else{
                        tokenClassCount.put(tokenClass, 1);
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            if(scanner != null) { scanner.close(); }
        }

    }

    public static String getTokenClass(String token){

        if(token == null || token.length() == 0) { return null; }
        if(seperatorTokenList.contains(token)){ return "Seperator"; }
        if(operatorTokenList.contains(token)) {  return "Operator"; }
        if(keywordTokenList.contains(token)) { return "Keyword"; }
        if(specialCharacterTokenList.contains(token)) { return "SpecialCharacter"; }

        for(char ch : token.toCharArray()){
            if(!Character.isDigit(ch)) { return null; }
        }

        return "Numeric";


    }
}
