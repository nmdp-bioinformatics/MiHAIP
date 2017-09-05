package nmdp;

public class Configure {
	public static String inputD;
	public static String inputR;
	private static String pairID;
	private static String hla;
    public static String hla_a1;
    public static String hla_b1;
    public static String hla_c1;
    public static String hla_a2;
    public static String hla_b2;
    public static String hla_c2;
	public static String tool = "/home/wwang/Tools/";
	private static String output;
	private static Sex sex = Sex.MM;
    
    public static void setHLA(String hla) throws Exception{
    	String [] data = hla.split(",");
    	if(data.length != 6){
    		throw new Exception("HLA must include HLA-A, HLA-B and HLA-C");
    	}
    	for(String s : data){
    		if(s.charAt(0) == 'a' || s.charAt(0) == 'A' ){
    			if(hla_a1 == null){
    				hla_a1 = "HLA-" + s.toUpperCase();
    			}else{
    				if(!hla_a1.equals("HLA-" + s.toUpperCase())){
    					hla_a2 = "HLA-" + s.toUpperCase();
    				}
    			}
    			
    		}else if(s.charAt(0) == 'b' || s.charAt(0) == 'B'){
    			if(hla_b1 == null){
    				hla_b1 = "HLA-" + s.toUpperCase();
    			}else{
    				if(!hla_b1.equals("HLA-" + s.toUpperCase())){
    					hla_b2 = "HLA-" + s.toUpperCase();
    				}
    			}
    		}else{
    			if(hla_c1 == null){
    				hla_c1 = "HLA-" + s.toUpperCase();
    			}else{
    				if(!hla_c1.equals("HLA-" + s.toUpperCase())){
    					hla_c2 = "HLA-" + s.toUpperCase();
    				}
    			}
    		}
    	}
    }
    
    public static String removeComma(String s){
    	return s.replace(":", "");
    }
    
    public static String getPairID(){
    	return pairID;
    }
    
    public static void  setPairID(String id){
    	pairID = id;
    }
    
    public static void setOutputFolder(String path){
    	output = path;
    }
    
    public static String getOutputFolder(){
    	return output;
    }
    public static Sex getSex(){
    	return sex;
    }
    public static void setSex(String sexString) throws Exception{
    	sexString = sexString.toUpperCase();
    	if(sexString.equals("MM")){
    		sex = Sex.MM;
    	}else if(sexString.equals("MF")){
    		sex = Sex.MF;
    	}else if(sexString.equals("FM")){
    		sex = Sex.FM;
    	}else if(sexString.equals("FF")){
    		sex = Sex.FF;
    	}else{
    		throw new Exception("The sex is unknow");
    	}
    }
    
    public static boolean hasTwoHla_a(){
    	return hla_a2 != null;
    }
    
    public static boolean hasTwoHla_b(){
    	return hla_b2 != null;
    }
    
    public static boolean hasTwoHla_c(){
    	return hla_c2 != null;
    }
}
